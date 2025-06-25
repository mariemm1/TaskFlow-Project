import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { AuthService } from '../../services/authService/auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  // canActivate(
  //   route: ActivatedRouteSnapshot,
  //   state: RouterStateSnapshot
  // ): boolean | UrlTree {
  //   this.authService.loadToken(); //  make sure we load it from localStorage
  //   const token = this.authService.getToken();
  //
  //   if (token && !this.authService.isTokenExpired()) {
  //     return true;
  //   }
  //   console.log('AuthGuard called. Token valid:', token && !this.authService.isTokenExpired());
  //
  //
  //   //  Redirect if not authenticated
  //   return this.router.createUrlTree(['/login']);
  // }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    this.authService.loadToken();
    const token = this.authService.getToken();
    const expired = this.authService.isTokenExpired();

    console.log('AuthGuard running');
    console.log('Token:', token);
    console.log('Token expired:', expired);

    if (token && !expired) {
      return true;
    }

    console.log('Not authenticated - redirecting to login');
    return this.router.createUrlTree(['/login']);
  }

}
