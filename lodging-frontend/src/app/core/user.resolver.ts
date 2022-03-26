import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { catchError, map, Observable, of } from 'rxjs';
import { UserResponse, UserService } from '../../core/openapi';
import { AppStateService } from './app-state.service';

@Injectable({
    providedIn: 'root'
})
export class UserResolver implements Resolve<UserResponse> {

    constructor(private userService: UserService,
                private appState: AppStateService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserResponse> {
        return this.userService.getCurrentUser().pipe(
                catchError(err => {
                    this.appState.setUser(undefined);
                    return of(err);
                }),
                map((response: UserResponse) => {
                    this.appState.setUser(response.user);
                    return response;
                })
            );
    }
}
