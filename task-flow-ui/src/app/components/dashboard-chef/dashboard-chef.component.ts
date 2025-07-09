import { Component } from '@angular/core';
import { SidebarComponent } from '../shared/sidebar/sidebar.component';
import {RouterModule} from '@angular/router';
import {HeaderComponent} from '../shared/header/header.component';

@Component({
  standalone: true,
  selector: 'app-dashboard-chef',
  imports: [SidebarComponent,HeaderComponent, RouterModule],
  templateUrl: './dashboard-chef.component.html',
  styleUrl: './dashboard-chef.component.scss'
})
export class DashboardChefComponent {

}
