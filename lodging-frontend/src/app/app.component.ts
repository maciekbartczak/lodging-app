import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { UserStateService } from './core/user-state.service';
import { AuthService, UserDto } from '../core/openapi';

export type Language = 'en' | 'pl';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    title = 'lodging-frontend';

    user?: UserDto;
    loading = false;
    currentLanguage: Language = 'en';

    constructor(private translate: TranslateService,
                private userState: UserStateService,
                private authService: AuthService) {

        translate.setDefaultLang('en');
        translate.use(this.currentLanguage);
    }

    ngOnInit(): void {
        this.loading = true;
        this.userState.user.subscribe(
            user => {
                this.user = user;
                this.loading = false;
            }
        );
    }

    logout(): void {
        this.authService.logout().subscribe(
            _ => this.userState.removeCurrentUser()
        );
    }

    changeLanguage(language: Language): void {
        this.translate.use(language);
    }
}
