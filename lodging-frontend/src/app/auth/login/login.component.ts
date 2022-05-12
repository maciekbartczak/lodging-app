import { Component } from '@angular/core';
import { AuthService, LoginRequest, UserService } from '../../../core/openapi';
import { ActivatedRoute, Router } from '@angular/router';
import { AppStateService } from '../../common/app-state.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    error = false;
    loading = false;

    loginData: LoginRequest = {
        username: '',
        password: ''
    }

    constructor(private authService: AuthService,
                private appState: AppStateService,
                private router: Router,
                private route: ActivatedRoute,
                private userService: UserService) {
    }

    login() {
        if (!this.loginData.username || !this.loginData.password) {
            return;
        }
        this.loading = true;

        this.authService.login(this.loginData).subscribe({
            next: _ => {
                this.loading = false;
                this.onLoginSuccess();
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

    private onLoginSuccess(): void {
        const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
        this.userService.getCurrentUser().subscribe(
            response => {
                this.appState.setUser(response.user);
                this.router.navigate([returnUrl]);
            });
    }

}
