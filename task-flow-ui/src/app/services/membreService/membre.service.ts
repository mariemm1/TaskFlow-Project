import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Membre } from '../../models/membre.model';

@Injectable({
  providedIn: 'root'
})
export class MembreService {
  private baseUrl = 'http://localhost:8081/api/membres';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Membre[]> {
    return this.http.get<Membre[]>(`${this.baseUrl}/all`);
  }

  getById(id: number): Observable<Membre> {
    return this.http.get<Membre>(`${this.baseUrl}/get/${id}`);
  }

  create(membre: Membre): Observable<Membre> {
    return this.http.post<Membre>(`${this.baseUrl}/add`, membre);
  }

  update(id: number, membre: Membre): Observable<Membre> {
    return this.http.put<Membre>(`${this.baseUrl}/update/${id}`, membre);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }
}
