# 🧠 Task Flow Management System

A full-stack **task assignment and project management system** for teams, built with **Spring Boot**, **MySQL**, **Flask (Python)** and **Angular 19**, featuring **AI-based member recommendation** using machine learning.

---

## 📐 UML Diagrams

### ✅ Use Case Diagram  
![Use Case Diagram](/images/img)

### ✅ Class Diagram  
![Class Diagram](/images/img.png)
---

## 📦 Tech Stack

| Layer         | Technology                     |
|---------------|--------------------------------|
| Backend API   | Java + Spring Boot             |
| Frontend      | Angular 19                     |
| Database      | MySQL                          |
| Authentication| JWT (JSON Web Tokens)          |
| AI Engine     | Python + Flask                 |
| ML Model      | RandomForestClassifier (scikit-learn) |

---

## 📂 Project Structure

```
Task-Flow/
├── IARecommendationML/               # 🧠 AI Recommendation Engine
│   ├── api.py                        # Flask API
│   ├── recommend_logic.py            # Core ML logic
│   └── model/                        # Trained .joblib ML model
├── src/                              # 🔧 Spring Boot Backend
│   ├── DTO/                          # Data Transfer Objects
│   ├── JWT/                          # JWT config & filters
│   ├── Models/                       # JPA entities (User, Projet, etc.)
│   ├── Repositories/                 # Spring Data Repos
│   ├── RestControllers/              # Exposed REST APIs
│   └── Services/                     # Business Logic
└── frontend/                         # 🎨 Angular 19 Frontend (in progress)
```

---

## 📌 Features

### ✅ Core Functionalities
- 🧑‍💼 Manage roles: **Admin**, **ChefEquipe**, **Membre**
- 📅 Manage:
  - Tâches (Tasks)
  - Projets
  - Congés (Leaves)
  - Équipes (Teams)
  - Jours Fériés (Public Holidays)
- 🔐 JWT-Based Authentication
- 🎯 Role-Based Access Control

### 🧠 AI Recommendation System
- Suggests best-matching members for a given task
- Based on:
  - Skill Matching
  - Availability (no ongoing task or leave)
  - History of previous assignments
- Trained using `RandomForestClassifier`
- Flask-based standalone prediction API (`/recommend`)

---

## 🧭 API Overview

### 🔧 Spring Boot REST Endpoints (Port `8081`)

| Operation                  | Method | Endpoint                                             |
|---------------------------|--------|------------------------------------------------------|
| Get all tâches            | GET    | `/api/taches/allTache`                               |
| Create a new projet       | POST   | `/api/projets/addProjet`                             |
| Assign équipe to projet   | PUT    | `/api/projets/{projetId}/assignEquipe/{equipeId}`    |
| Assign membre to tâche    | PUT    | `/api/taches/{tacheId}/assign/{membreId}`            |
| Delete équipe (if empty)  | DELETE | `/api/equipes/deleteEquipe/{id}`                     |

### 🌐 Flask AI Endpoints (Port `5000`)

| Operation                  | Method | Endpoint                      |
|---------------------------|--------|-------------------------------|
| Recommend members         | POST   | `http://localhost:5000/recommend` |
| Retrain ML model          | POST   | `/train`                      |

---

## 🧪 How to Test Locally

### ✅ Spring Boot API (Port `8081`)
```bash
# From project root
./mvnw spring-boot:run
```
📍 Access Swagger/Postman: `http://localhost:8081/api`

---

### ✅ Flask AI API (Port `5000`)
```bash
cd IARecommendationML/
python -m venv venv
venv\Scripts\activate  # or source venv/bin/activate on macOS/Linux
pip install -r requirements.txt
python api.py
```

✔️ Runs on `http://localhost:5000`

---

## 🔁 Sample AI Request

```json
POST http://localhost:5000/recommend
{
  "task_id": 1,
  "start_date": "2025-08-01",
  "end_date": "2025-08-10",
  "skills": "Angular, CSS",
  "task_name": "Fix UI Bug",
  "description": "Resolve header alignment",
  "statut": "EN_ATTENTE",
  "projet": { "id": 1 }
}
```

✔️ Response:
```json
[
  {
    "membre_id": 5,
    "name": "Ahmed",
    "score": 0.9231
  },
  {
    "membre_id": 2,
    "name": "Fatma",
    "score": 0.8145
  }
]
```

---

## ✨ Role-Based Behavior

| Role        | Access Capabilities                                |
|-------------|----------------------------------------------------|
| Admin       | Full CRUD + Assignments + Holidays + Congés        |
| ChefEquipe  | Assign members to projects + équipe management     |
| Membre      | Accept/Refuse Tâches + View Projects               |


