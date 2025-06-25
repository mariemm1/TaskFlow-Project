import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChefEquipe } from '../../models/chef.model';

@Injectable({
  providedIn: 'root'
})
export class ChefService {
  private baseUrl = 'http://localhost:8081/api/chefs';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ChefEquipe[]> {
    return this.http.get<ChefEquipe[]>(`${this.baseUrl}/all`);
  }

  getById(id: number): Observable<ChefEquipe> {
    return this.http.get<ChefEquipe>(`${this.baseUrl}/get/${id}`);
  }

  create(chef: ChefEquipe): Observable<ChefEquipe> {
    return this.http.post<ChefEquipe>(`${this.baseUrl}/add`, chef);
  }

  update(id: number, chef: ChefEquipe): Observable<ChefEquipe> {
    return this.http.put<ChefEquipe>(`${this.baseUrl}/update/${id}`, chef);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }
}
