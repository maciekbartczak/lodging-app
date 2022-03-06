import { Component } from '@angular/core';
import { AuthService, LoginRequest } from '../../../core/openapi';
import { TokenService } from '../../core/token.service';
import { Router } from '@angular/router';

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
                private tokenService: TokenService,
                private router: Router) {
    }

    login() {
        if (this.loginData.username && this.loginData.password) {
            this.loading = true;

            this.authService.login(this.loginData).subscribe(
                (response) => {
                    this.tokenService.saveToken(response.token);
                    console.log(this.tokenService.getUserId());
                    this.router.navigate(['/']);
                },
                (err) => {
                    console.log(err);
                    this.error = true;
                    this.loading = false;
                },
                () => {
                    this.loading = false;
                }
            )
        }
    }

}
