import { Component } from '@angular/core';
import { SidebarComponent } from '../shared/sidebar/sidebar.component';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from '../shared/header/header.component';

@Component({
  standalone: true,
  selector: 'app-dashboard-admin',
  imports: [SidebarComponent,HeaderComponent, RouterModule],
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.scss'],

})
export class DashboardAdminComponent {}
