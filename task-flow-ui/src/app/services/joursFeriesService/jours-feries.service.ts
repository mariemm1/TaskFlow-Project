import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JoursFeries } from '../../models/jour-feries.model';

@Injectable({
  providedIn: 'root'
})
export class JoursFeriesService {
  private apiUrl = 'http://localhost:8081/api/jours-feries';

  constructor(private http: HttpClient) {}

  getAll(): Observable<JoursFeries[]> {
    return this.http.get<JoursFeries[]>(`${this.apiUrl}/allJoursFeries`);
  }

  getById(id: number): Observable<JoursFeries> {
    return this.http.get<JoursFeries>(`${this.apiUrl}/${id}`);
  }

  create(jf: JoursFeries): Observable<JoursFeries> {
    return this.http.post<JoursFeries>(`${this.apiUrl}/addJoursFeries`, jf);
  }

  update(id: number, jf: JoursFeries): Observable<JoursFeries> {
    return this.http.put<JoursFeries>(`${this.apiUrl}/updateJoursFeries/${id}`, jf);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/deleteJoursFeries/${id}`);
  }
}
