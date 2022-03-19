import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { UserDto, UserService } from '../../core/openapi';

@Injectable({
    providedIn: 'root'
})
export class UserStateService {

    public user: Observable<UserDto | undefined>
    private userSubject = new ReplaySubject<UserDto | undefined>(1)

    constructor(private userService: UserService) {
        this.user = this.userSubject.asObservable();

       this.getCurrentUser();
    }

    getCurrentUser() {
        this.userService.getCurrentUser().subscribe({
            next: res => this.userSubject.next(res.user),
            error: _ => this.userSubject.next(undefined)
        });
    }

    removeCurrentUser() {
        this.userSubject.next(undefined);
    }

}
