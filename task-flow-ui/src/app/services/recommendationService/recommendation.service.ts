import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// 🧠 Interface for the task sent to the ML backend
export interface TaskInput {
  task_id: number;
  start_date: string; // ISO date string (e.g. "2025-08-01")
  end_date: string;   // ISO date string
  skills: string;     // Comma-separated (e.g. "Angular, CSS")
  task_name: string;
  description: string;
  statut: string;
  projet: { id: number };
}

// 🧠 Interface for recommendation response
export interface RecommendationResult {
  membre_id: number;
  name: string;
  score: number; // between 0 and 1
}

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
  private apiUrl = 'http://localhost:5000'; // Flask server

  constructor(private http: HttpClient) {}

  // 🔍 Get recommended members for a given task
  recommend(task: TaskInput): Observable<RecommendationResult[]> {
    return this.http.post<RecommendationResult[]>(`${this.apiUrl}/recommend`, task);
  }

  // 🔁 Force model retraining
  retrainModel(): Observable<{ status: string }> {
    return this.http.post<{ status: string }>(`${this.apiUrl}/train`, {});
  }
}
