import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AppStateService } from './app-state.service';
import { TranslateService } from '@ngx-translate/core';
import { map, Observable, take } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserGuard implements CanActivate {

    constructor(private appState: AppStateService,
                private messageService: MessageService,
                private translate: TranslateService,
                private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>  {
        return this.appState.user.pipe(
            take(1),
            map(user => {
                if (user) {
                    return true;
                } else {
                    this.messageService.add({
                        severity: 'warn',
                        summary: this.translate.instant('error.not-logged-in'),
                        detail: this.translate.instant('error.not-logged-in-details')
                    });
                    this.router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
                    return false;
                }
            }));
    }
}
