import {RouterModule, Routes} from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { AuthGuard } from './auth/guard/auth.guard';
import {DashboardAdminComponent} from './components/dashboard-admin/dashboard-admin.component';
import {DashboardChefComponent} from './components/dashboard-chef/dashboard-chef.component';
import {DashboardMembreComponent} from './components/dashboard-membre/dashboard-membre.component';
import {NgModule} from '@angular/core';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: 'admin',
    component: DashboardAdminComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'membre',
    component: DashboardMembreComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'chef',
    component: DashboardChefComponent,
    canActivate: [AuthGuard]
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
