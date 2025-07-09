import { Component } from '@angular/core';
import { SidebarComponent } from '../shared/sidebar/sidebar.component';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from '../shared/header/header.component';

@Component({
  standalone: true,
  selector: 'app-dashboard-membre',
  imports: [SidebarComponent, HeaderComponent,RouterModule],
  templateUrl: './dashboard-membre.component.html',
  styleUrl: './dashboard-membre.component.scss'
})
export class DashboardMembreComponent {

}
