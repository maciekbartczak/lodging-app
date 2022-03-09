import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Observable, ReplaySubject } from 'rxjs';
import { UserDto, UserService } from '../../core/openapi';

@Injectable({
    providedIn: 'root'
})
export class UserStateService {

    public user: Observable<UserDto>
    private userSubject = new ReplaySubject<UserDto>(1)

    constructor(private tokenService: TokenService,
                private userService: UserService) {
        this.user = this.userSubject.asObservable();

        const token = tokenService.getToken();
        if (token) {
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
