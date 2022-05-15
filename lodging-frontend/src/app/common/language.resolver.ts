import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LanguageResolver implements Resolve<boolean> {

    constructor(private translateService: TranslateService,
                private cookieService: CookieService) {
}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
        this.translateService.setDefaultLang('en');
        let language = this.cookieService.get('app-lang');
        language = language ? language : 'en';
        return firstValueFrom(this.translateService.use(language));
    }
}
