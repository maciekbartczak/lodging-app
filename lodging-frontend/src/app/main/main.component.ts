import { Component, OnInit } from '@angular/core';
import { AuthService, UserDto } from '../../core/openapi';
import { TranslateService } from '@ngx-translate/core';
import { AppStateService } from '../core/app-state.service';
import { CookieService } from 'ngx-cookie-service';

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
                private cookieService: CookieService) {
    }

    ngOnInit(): void {
        this.appState.user.subscribe(
            user => {
                this.user = user;
            }
        );
    }

    logout(): void {
        this.authService.logout().subscribe(
            _ => this.appState.setUser(undefined)
        );
    }

    async changeLanguage(language: Language): Promise<void> {
        if (this.cookieService.get('app-lang') != language) {
            this.cookieService.set('app-lang', language, new Date(9999, 11));
            window.location.reload();
        }
    }

}
