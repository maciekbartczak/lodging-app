import { Component } from '@angular/core';
import { AuthService, LoginRequest, UserDto } from '../../../core/openapi';
import { Router } from '@angular/router';
import { AppStateService } from '../../core/app-state.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    error = false;
    loading = false;
    user: UserDto | null = null;

    loginData: LoginRequest = {
        username: '',
        password: ''
    }

    constructor(private authService: AuthService,
                private appState: AppStateService,
                private router: Router) {
    }

    login() {
        if (!this.loginData.username || !this.loginData.password) {
            return;
        }
        this.loading = true;

        this.authService.login(this.loginData).subscribe({
            next: _ => {
                this.loading = false;
                this.router.navigate(["/"]);
            },
            error: err => {
                this.loading = false;
                console.log(err);
                if (err.error.status === 401) {
                    this.error = true;
                }
            }
        });
    }

}
