import { Component } from '@angular/core';
import { AuthService, LoginRequest, UserDto } from '../../../core/openapi';
import { TokenService } from '../../core/token.service';
import { Router } from '@angular/router';
import { UserStateService } from '../../core/user-state.service';

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
                private tokenService: TokenService,
                private userStateService: UserStateService,
                private router: Router) {
    }

    login() {
        if (this.loginData.username && this.loginData.password) {
            this.authService.login(this.loginData).subscribe(
                (response) => {
                    this.tokenService.saveToken(response.token);
                    this.userStateService.getCurrentUser();

                    this.userStateService.user.subscribe(
                        user => {
                            this.user = user;
                            console.log(this.user);
                        }
                    );
                    // this.router.navigate(['/']);
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

            this.loading = true;
        }
    }

}
