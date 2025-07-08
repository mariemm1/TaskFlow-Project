import os
import pandas as pd
import joblib
import pymysql
from datetime import timedelta
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import MultiLabelBinarizer

# Load DB config from environment variables
DB_HOST = os.getenv('DB_HOST', 'localhost')
DB_USER = os.getenv('DB_USER', 'root')
DB_PASSWORD = os.getenv('DB_PASSWORD', '')
DB_NAME = os.getenv('DB_NAME', 'Task-Flow')

MODEL_PATH = os.path.join(os.path.dirname(__file__), 'model', 'task_model.joblib')


# ──────────────────────────── Utility Functions ────────────────────────────────

def clean_skills(s):
    if pd.isna(s):
        return []
    return [tok.strip().lower() for tok in s.split(',') if tok.strip()]


def preprocess_skills(df, col):
    df = df.copy()
    df['skills_list'] = df[col].apply(clean_skills)
    return df


def one_hot(df, col):
    mlb = MultiLabelBinarizer()
    mat = mlb.fit_transform(df[col])
    return pd.concat([df.reset_index(drop=True), pd.DataFrame(mat, columns=mlb.classes_)], axis=1), mlb.classes_


def calculate_duration(start, end, holidays):
    s = pd.to_datetime(start)
    e = pd.to_datetime(end)
    h = set(pd.to_datetime(holidays['holiday_date']).dt.date)
    days = sum(1 for d in pd.date_range(s, e) if d.weekday() < 5 and d.date() not in h)
    return days * 8


def is_available(mid, start, end, conges, holidays, assignments):
    s, e = pd.to_datetime(start).date(), pd.to_datetime(end).date()

    for _, row in conges[conges['user_id'] == mid].iterrows():
        if not (e < row.start_date.date() or s > row.end_date.date()):
            return False

    for _, row in assignments[assignments['allocated_membre'] == mid].iterrows():
        ts, te = row.start_date.date(), row.end_date.date()
        if not (e < ts or s > te):
            return False

    return True


# ─────────────────────────────── DB Loader ──────────────────────────────────────

def load_all_data():
    conn = pymysql.connect(host=DB_HOST, user=DB_USER, password=DB_PASSWORD, database=DB_NAME)
    cursor = conn.cursor()

    # Load members with competences
    cursor.execute("""
        SELECT m.id AS membre_id, u.nom, u.prenom, mc.competences
        FROM membre m
        JOIN membre_competences mc ON m.id = mc.membre_id
        JOIN user u ON m.id = u.id;
    """)
    members = pd.DataFrame(cursor.fetchall(), columns=['membre_id', 'nom', 'prenom', 'competences'])

    # Load tasks
    cursor.execute("""
        SELECT date_debut AS start_date,
               date_fin AS end_date,
               id AS task_id,
               membre_id AS allocated_membre,
               projet_id,
               description,
               nom AS task_name,
               skills,
               statut
        FROM tache;
    """)
    tasks = pd.DataFrame(cursor.fetchall(), columns=[
        'start_date', 'end_date', 'task_id', 'allocated_membre',
        'projet_id', 'description', 'task_name', 'skills', 'statut'
    ])

    # Load conges
    cursor.execute("SELECT user_id, start_date, end_date FROM conge;")
    conges = pd.DataFrame(cursor.fetchall(), columns=['user_id', 'start_date', 'end_date'])

    # Load holidays
    cursor.execute("SELECT date FROM jours_feries;")
    holidays = pd.DataFrame(cursor.fetchall(), columns=['holiday_date'])

    conn.close()

    assignments = tasks[['task_id', 'allocated_membre', 'start_date', 'end_date', 'statut']].copy()
    return members, tasks, conges, holidays, assignments


# ──────────────────────────── Model Training ────────────────────────────────────

def train_model(members, tasks, conges, holidays, assignments):
    tasks = preprocess_skills(tasks, 'skills')

    members = members.groupby(['membre_id', 'prenom'])['competences'] \
        .apply(lambda x: list(set(x.dropna()))) \
        .reset_index()
    members['competences'] = members['competences'].apply(lambda arr: ', '.join(arr))
    members = preprocess_skills(members, 'competences')

    t_enc, t_skills = one_hot(tasks, 'skills_list')
    m_enc, m_skills = one_hot(members, 'skills_list')
    m_enc = m_enc.rename(columns={sk: f'membre_{sk}' for sk in m_skills})

    all_skills = sorted(set(t_skills).union(set(m_skills)))

    for sk in all_skills:
        if sk not in t_enc:
            t_enc[sk] = 0
        if f'membre_{sk}' not in m_enc:
            m_enc[f'membre_{sk}'] = 0

    rows = []
    for _, task in tasks.iterrows():
        tid, start, end = task.task_id, task.start_date, task.end_date
        dur = calculate_duration(start, end, holidays)
        trow = t_enc[t_enc['task_id'] == tid][all_skills].iloc[0]

        for _, mem in members.iterrows():
            mid = mem.membre_id
            mrow = m_enc[m_enc['membre_id'] == mid][[f'membre_{sk}' for sk in all_skills]].iloc[0]
            score = sum(trow[sk] and mrow[f'membre_{sk}'] for sk in all_skills)
            label = int(mid in assignments[assignments['task_id'] == tid]['allocated_membre'].values)
            rows.append({
                'skill_match': score,
                'duration': dur,
                'available_hours': dur,
                'label': label
            })

    df = pd.DataFrame(rows)
    X = df[['skill_match', 'duration', 'available_hours']]
    y = df['label']

    clf = RandomForestClassifier(n_estimators=200, max_depth=4, random_state=42)
    clf.fit(X, y)

    os.makedirs(os.path.dirname(MODEL_PATH), exist_ok=True)
    joblib.dump((clf, all_skills), MODEL_PATH)

    return clf, all_skills


# ──────────────────────── Recommendation Function ───────────────────────────────

def recommend_members(task, members, conges, holidays, assignments, tasks, model, all_skills, top_n=3):
    task_skills = clean_skills(task['skills'])
    start, end = task['start_date'], task['end_date']
    dur = calculate_duration(start, end, holidays)

    recs = []
    for _, mem in members.iterrows():
        mid = mem.membre_id
        comp = clean_skills(mem['competences'])
        score = sum(k in task_skills and k in comp for k in all_skills)
        if score == 0 or not is_available(mid, start, end, conges, holidays, assignments):
            continue
        Xp = [[score, dur, dur]]
        prob = model.predict_proba(Xp)[0][1]
        recs.append((mid, mem.prenom, prob))

    return sorted(recs, key=lambda x: -x[2])[:top_n]


# ─────────────────────────── Load Model ─────────────────────────────────────────

def load_model():
    return joblib.load(MODEL_PATH)
