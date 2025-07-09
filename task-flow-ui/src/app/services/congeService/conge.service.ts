import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Conge } from '../../models/conge.model';

@Injectable({
  providedIn: 'root'
})
export class CongeService {
  private apiUrl = 'http://localhost:8081/api/conges';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Conge[]> {
    return this.http.get<Conge[]>(`${this.apiUrl}/allConge`);
  }

  getById(id: number): Observable<Conge> {
    return this.http.get<Conge>(`${this.apiUrl}/${id}`);
  }

  create(conge: Conge): Observable<Conge> {
    return this.http.post<Conge>(`${this.apiUrl}/addConge`, conge);
  }

  update(id: number, conge: Conge): Observable<Conge> {
    return this.http.put<Conge>(`${this.apiUrl}/updateConge/${id}`, conge);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/deleteConge/${id}`);
  }
}
