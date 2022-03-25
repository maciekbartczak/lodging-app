import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { catchError, map, Observable, of } from 'rxjs';
import { UserResponse, UserService } from '../../core/openapi';
import { UserStateService } from './user-state.service';

@Injectable({
    providedIn: 'root'
})
export class UserResolver implements Resolve<UserResponse> {

    constructor(private userService: UserService,
                private userState: UserStateService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserResponse> {
        return this.userService.getCurrentUser().pipe(
                catchError(err => {
                    this.userState.setUser(undefined);
                    return of(err);
                }),
                map((response: UserResponse) => {
                    this.userState.setUser(response.user);
                    return response;
                })
            );
    }
}
