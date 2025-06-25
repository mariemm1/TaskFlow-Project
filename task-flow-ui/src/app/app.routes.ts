import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { AdminComponent } from './component/admin/admin.component';
import { MembreComponent } from './component/membre/membre.component';
import { ChefComponent } from './component/chef/chef.component';
import { AuthGuard } from './auth/guard/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'membre',
    component: MembreComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'chef',
    component: ChefComponent,
    canActivate: [AuthGuard]
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
