from flask import Flask, request, jsonify
from recommend_logic import load_all_data, train_model, recommend_members, load_model

app = Flask(__name__)

# Load data and model at startup
members, tasks, conges, holidays, assignments = load_all_data()
model, all_skills = train_model(members, tasks, conges, holidays, assignments)

@app.route('/recommend', methods=['POST'])
def recommend():
    task = request.json
    recs = recommend_members(task, members, conges, holidays, assignments, tasks, model, all_skills)
    return jsonify([{'membre_id': mid, 'name': name, 'score': round(score,4)} for mid,name,score in recs])

@app.route('/train', methods=['POST'])
def retrain():
    global model, all_skills
    members, tasks, conges, holidays, assignments = load_all_data()
    model, all_skills = train_model(members, tasks, conges, holidays, assignments)
    return jsonify({'status':'retrained'})

if __name__ == '__main__':
    app.run(debug=True)
