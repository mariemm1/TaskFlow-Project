import {Component, OnInit} from '@angular/core';
import { AuthService } from '../../../services/authService/auth.service';
import {CommonModule} from '@angular/common';
import {Router, RouterModule} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-sidebar',
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})


export class SidebarComponent implements OnInit {
  role: string | undefined;
  openMenus: { [key: string]: boolean } = {};

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.loadToken();
    this.role = this.authService.roles;
  }

  toggleMenu(menu: string): void {
    this.openMenus[menu] = !this.openMenus[menu];
  }


}
