import { Component, OnInit } from '@angular/core';
import { AuthService, UserDto } from '../../core/openapi';
import { TranslateService } from '@ngx-translate/core';
import { AppStateService } from '../common/app-state.service';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';

export type Language = 'en' | 'pl';

@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
    title = 'lodging-frontend';

    user?: UserDto;

    constructor(private translate: TranslateService,
                private appState: AppStateService,
                private authService: AuthService,
                private cookieService: CookieService,
                private router: Router,
                private config: PrimeNGConfig) {
    }

    ngOnInit(): void {
        this.appState.user.subscribe(
            user => {
                this.user = user;
            });
    }

    logout(): void {
        this.authService.logout().subscribe(
            _ => {
                this.appState.setUser(undefined);
                this.router.navigate(['/']);
            });
    }

    changeLanguage(language: Language): void {
        if (this.cookieService.get('app-lang') != language) {
            this.cookieService.set('app-lang', language, new Date(9999, 11));
            setTimeout(() => window.location.reload());
        }
    }

}
