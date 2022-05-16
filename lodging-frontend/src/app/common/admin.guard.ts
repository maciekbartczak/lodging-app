import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { AppStateService } from './app-state.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

    constructor(private appState: AppStateService,
                private messageService: MessageService,
                private translate: TranslateService,
                private router: Router) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>  {
        return this.appState.user.pipe(
            take(1),
            map(user => {
                if (user?.roles.includes('ADMIN')) {
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
