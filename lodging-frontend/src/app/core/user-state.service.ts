import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { UserDto } from '../../core/openapi';

@Injectable({
    providedIn: 'root'
})
export class UserStateService {

    public user: Observable<UserDto | undefined>
    private userSubject = new ReplaySubject<UserDto | undefined>(1)

    constructor() {
        this.user = this.userSubject.asObservable();
    }

    setUser(user: UserDto | undefined) {
        this.userSubject.next(user);
    }

}
