import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Equipe } from '../../models/equipe.model';
import { EquipeAssignmentRequest } from '../../models/equipe-assignment.model';

@Injectable({
  providedIn: 'root'
})
export class EquipeService {
  private apiUrl = 'http://localhost:8081/api/equipes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Equipe[]> {
    return this.http.get<Equipe[]>(`${this.apiUrl}/allEquipe`);
  }

  getById(id: number): Observable<Equipe> {
    return this.http.get<Equipe>(`${this.apiUrl}/${id}`);
  }

  create(equipe: Equipe): Observable<Equipe> {
    return this.http.post<Equipe>(`${this.apiUrl}/addEquipe`, equipe);
  }

  update(id: number, equipe: Equipe): Observable<Equipe> {
    return this.http.put<Equipe>(`${this.apiUrl}/updateEquipe/${id}`, equipe);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/deleteEquipe/${id}`);
  }

  assignChefAndMembers(id: number, payload: EquipeAssignmentRequest): Observable<Equipe> {
    return this.http.put<Equipe>(`${this.apiUrl}/${id}/assign`, payload);
  }

  removeChef(id: number): Observable<Equipe> {
    return this.http.put<Equipe>(`${this.apiUrl}/${id}/remove-chef`, {});
  }

  removeMembre(equipeId: number, membreId: number): Observable<Equipe> {
    return this.http.put<Equipe>(`${this.apiUrl}/${equipeId}/remove-membre/${membreId}`, {});
  }
}
