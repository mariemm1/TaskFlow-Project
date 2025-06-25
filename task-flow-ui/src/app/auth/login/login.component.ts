import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule} from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/authService/auth.service'; // adjust path if needed
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';
  loading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  login(): void {
    this.errorMessage = '';
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    const credentials = {
      email: this.loginForm.value.email,
      password: this.loginForm.value.password
    };

    this.authService.login(credentials).subscribe({
      next: (res) => {
        const token = res.token;
        console.log('Raw token:', token);

        this.authService.saveToken(token);

        const helper = new JwtHelperService();
        const decoded: any = helper.decodeToken(token);
        console.log('Decoded Token:', decoded);
        const role = decoded?.role || decoded?.authorities?.[0]?.authority?.replace('ROLE_', '');
        console.log('Decoded role:', role); // DEBUG

        console.log('🔁 Redirecting based on role:', role); //  DEBUG

        // Redirect based on role
        switch (role) {
          case 'ADMIN':
            this.router.navigate(['/admin']);
            break;
          case 'CHEF_EQUIPE':
            this.router.navigate(['/chef']);
            break;
          case 'MEMBRE':
            this.router.navigate(['/membre']);
            break;
          default:
            this.router.navigate(['/login']);
            break;
        }
      },
      error: () => {
        this.errorMessage = 'Email ou mot de passe incorrect.';
        this.loading = false;
      }
    });
  }
}
