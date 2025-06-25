import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './services/authService/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `<router-outlet></router-outlet>`,
})
export class AppComponent {
  constructor(private authService: AuthService) {}


  ngOnInit() {
    this.authService.loadToken();  // Charger le token dès le démarrage
  }
}
