import { Injectable } from '@angular/core';
import { Observable, ReplaySubject } from 'rxjs';
import { UserDto } from '../../core/openapi';

@Injectable({
    providedIn: 'root'
})
export class AppStateService {

    public user: Observable<UserDto | undefined>;
    public loading: Observable<boolean>;

    private userSubject = new ReplaySubject<UserDto | undefined>(1)
    private loadingSubject = new ReplaySubject<boolean>(1);

    constructor() {
        this.user = this.userSubject.asObservable();
        this.loading = this.loadingSubject.asObservable();
    }

    setUser(user: UserDto | undefined) {
        this.userSubject.next(user);
    }

    setLoading(loading: boolean) {
        this.loadingSubject.next(loading);
    }

}
