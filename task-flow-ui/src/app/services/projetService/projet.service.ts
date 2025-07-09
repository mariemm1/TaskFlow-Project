import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Projet } from '../../models/projet.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjetService {
  private apiUrl = 'http://localhost:8081/api/projets';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Projet[]> {
    return this.http.get<Projet[]>(`${this.apiUrl}/allProjet`);
  }

  getById(id: number): Observable<Projet> {
    return this.http.get<Projet>(`${this.apiUrl}/${id}`);
  }

  create(projet: Projet): Observable<Projet> {
    return this.http.post<Projet>(`${this.apiUrl}/addProjet`, projet);
  }

  update(id: number, projet: Projet): Observable<Projet> {
    return this.http.put<Projet>(`${this.apiUrl}/updateProjet/${id}`, projet);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/deleteProjet/${id}`);
  }

  assignEquipe(projetId: number, equipeId: number): Observable<Projet> {
    return this.http.put<Projet>(`${this.apiUrl}/${projetId}/assignEquipe/${equipeId}`, {});
  }
}
