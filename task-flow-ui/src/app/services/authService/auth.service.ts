import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/auth';
  private token: string = '';
  private helper = new JwtHelperService();

  public isLoggedIn = false;
  public roles: string | undefined;
  public loggedUserEmail: string | undefined;

  constructor(private http: HttpClient, private router: Router) {}

  // 🧠 Checks if we’re running in a browser (not SSR)
  private isBrowser(): boolean {
    return typeof window !== 'undefined';
  }

  // 🔐 LOGIN: Sends credentials and receives JWT
  login(data: { email: string; password: string }): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, data);
  }

  // 💾 STORE TOKEN + ID + ROLE INTO LOCALSTORAGE
  saveToken(token: string): void {
    this.token = token;
    this.isLoggedIn = true;

    if (this.isBrowser()) {
      localStorage.setItem('jwt', token);
      localStorage.setItem('isLoggedIn', 'true');
    }

    this.decodeJWT(); // decode token and set role + email

    if (this.isBrowser()) {
      const userId = this.getUserIdFromToken();
      localStorage.setItem('userId', userId);
      if (this.roles) {
        localStorage.setItem('role', this.roles);
      }
    }
  }

  // 📦 LOAD TOKEN FROM STORAGE (on page refresh)
  loadToken(): void {
    if (this.isBrowser()) {
      const storedToken = localStorage.getItem('jwt');
      if (storedToken) {
        this.token = storedToken;
        this.decodeJWT();
      }
    }
  }

  // 🔓 DECODE JWT to extract email, role, and userId
  private decodeJWT(): void {
    if (!this.token) return;

    const decoded = this.helper.decodeToken(this.token);
    this.roles = decoded?.role || decoded?.authorities?.[0]?.authority?.replace('ROLE_', '');
    this.saveUserEmail(decoded?.sub || '');
  }

  // 📧 Save user email
  saveUserEmail(email: string): void {
    this.loggedUserEmail = email;
  }

  // 🟢 Check if logged in
  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token && !this.helper.isTokenExpired(token);
  }

  // ❌ Check token expiration
  isTokenExpired(): boolean {
    return this.helper.isTokenExpired(this.getToken());
  }

  // 📤 Get token from service or localStorage
  getToken(): string {
    return this.token || (this.isBrowser() ? localStorage.getItem('jwt') || '' : '');
  }

  // 🆔 Extract user ID from token
  getUserIdFromToken(): string {
    const token = this.getToken();
    const decoded = this.helper.decodeToken(token);
    return decoded?.id?.toString() || '';
  }

  // 🔍 Get userId from storage
  getUserId(): number | null {
    if (this.isBrowser()) {
      const id = localStorage.getItem('userId');
      return id ? +id : null;
    }
    return null;
  }

  // 🚪 LOGOUT
  logout(): void {
    this.token = '';
    this.roles = undefined;
    this.loggedUserEmail = undefined;
    this.isLoggedIn = false;

    if (this.isBrowser()) {
      localStorage.removeItem('jwt');
      localStorage.removeItem('isLoggedIn');
      localStorage.removeItem('userId');
      localStorage.removeItem('role');
    }

    this.router.navigate(['/login']);
  }

  // 🛡️ Role check helpers
  isAdmin(): boolean {
    return this.roles === 'ADMIN';
  }

  isMembre(): boolean {
    return this.roles === 'MEMBRE';
  }

  isChef(): boolean {
    return this.roles === 'CHEF_EQUIPE';
  }

  // ⚙️ Auth header for protected APIs
  getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.getToken()}`
    });
  }


  // 🔍 API call to fetch user by email
  getUserByEmail(email: string): Observable<any> {
    const headers = this.getHeaders(); // includes Bearer token
    return this.http.get(`http://localhost:8081/api/users/email/${email}`, { headers });
  }



}
