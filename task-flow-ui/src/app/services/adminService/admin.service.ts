import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Admin } from '../../models/admin.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:8081/api/admins';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Admin[]> {
    return this.http.get<Admin[]>(`${this.baseUrl}/all`);
  }

  getById(id: number): Observable<Admin> {
    return this.http.get<Admin>(`${this.baseUrl}/get/${id}`);
  }

  create(admin: Admin): Observable<Admin> {
    return this.http.post<Admin>(`${this.baseUrl}/add`, admin);
  }

  update(id: number, admin: Admin): Observable<Admin> {
    return this.http.put<Admin>(`${this.baseUrl}/update/${id}`, admin);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }
}
