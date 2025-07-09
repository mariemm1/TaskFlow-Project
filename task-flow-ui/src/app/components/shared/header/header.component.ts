import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/authService/auth.service';
import {CommonModule, NgIf} from '@angular/common';
import {Router, RouterModule} from '@angular/router';


@Component({
  standalone: true,
  selector: 'app-header',
  imports: [CommonModule, RouterModule, NgIf],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  userName: string = 'User';
  role: string = '';
  dropdownOpen = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.loadToken();
    this.role = localStorage.getItem('role') || '';

    const email = this.authService.loggedUserEmail;
    if (email) {
      this.authService.getUserByEmail(email).subscribe({
        next: (user) => {
          this.userName = user.nom || 'User';
        },
        error: () => {
          this.userName = 'User';
        }
      });
    }
  }

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
    console.log('Dropdown toggled:', this.dropdownOpen);
  }


  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
