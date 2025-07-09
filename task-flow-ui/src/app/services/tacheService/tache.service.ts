import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Tache } from '../../models/tache.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TacheService {
  private apiUrl = 'http://localhost:8081/api/taches';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Tache[]> {
    return this.http.get<Tache[]>(`${this.apiUrl}/allTaches`);
  }

  getById(id: number): Observable<Tache> {
    return this.http.get<Tache>(`${this.apiUrl}/${id}`);
  }

  create(tache: Tache): Observable<Tache> {
    return this.http.post<Tache>(`${this.apiUrl}/addTache`, tache);
  }

  update(id: number, tache: Tache): Observable<Tache> {
    return this.http.put<Tache>(`${this.apiUrl}/updateTache/${id}`, tache);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/deleteTache/${id}`);
  }

  assignMembre(tacheId: number, membreId: number): Observable<Tache> {
    return this.http.put<Tache>(`${this.apiUrl}/${tacheId}/assign/${membreId}`, {});
  }

  markAsTerminee(id: number): Observable<Tache> {
    return this.http.put<Tache>(`${this.apiUrl}/${id}/terminee`, {});
  }
}
