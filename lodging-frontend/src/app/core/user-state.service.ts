import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Observable, ReplaySubject } from 'rxjs';
import { UserDto, UserService } from '../../core/openapi';
import { JwtHelperService } from '@auth0/angular-jwt';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
    providedIn: 'root'
})
export class UserStateService {

    public user: Observable<UserDto>
    private userSubject = new ReplaySubject<UserDto>(1)

    constructor(private tokenService: TokenService,
                private userService: UserService,
                private jwtHelper: JwtHelperService,
                private messageService: MessageService,
                private translateService: TranslateService) {
        this.user = this.userSubject.asObservable();

        const token = tokenService.getToken();

        if (!token) {
            return;
        }

        if (this.jwtHelper.isTokenExpired(token)) {
            setTimeout(() => {
                this.messageService.add({
                    severity: 'warn',
                    summary: translateService.instant('auth.sessionExpired.header'),
                    detail: translateService.instant('auth.sessionExpired.content')
                });
            });
            this.tokenService.removeToken();
        } else {
            this.getCurrentUser();
        }
    }

    getCurrentUser() {
        this.userService.getCurrentUser().subscribe(
            (res) => this.userSubject.next(res.user)
        )
    }

    removeCurrentUser() {
        this.userSubject.next({});
    }

}
