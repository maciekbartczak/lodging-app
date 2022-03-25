import { Component, OnInit } from '@angular/core';
import { AuthService, UserDto } from '../../core/openapi';
import { TranslateService } from '@ngx-translate/core';
import { UserStateService } from '../core/user-state.service';
import { firstValueFrom } from 'rxjs';
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
    currentLanguage: Language = 'en';
    loading = false;

    constructor(private translate: TranslateService,
                private userState: UserStateService,
                private authService: AuthService,
                private cookieService: CookieService) {
    }

    ngOnInit(): void {
        this.userState.user.subscribe(
            user => {
                this.user = user;
            }
        );
    }

    logout(): void {
        this.authService.logout().subscribe(
            _ => this.userState.setUser(undefined)
        );
    }

    async changeLanguage(language: Language): Promise<void> {
        this.cookieService.set('app-lang', language, new Date(9999, 11));
        this.loading = true;
        await firstValueFrom(this.translate.use(language));
        this.loading = false;
    }

}
