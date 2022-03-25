import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { firstValueFrom } from 'rxjs';

export default function appInitializerFactory(translateService: TranslateService, cookieService: CookieService) {
    return () => {
        translateService.setDefaultLang('en');
        let language = cookieService.get('app-lang');
        language = language ? language : 'en';
        return firstValueFrom(translateService.use(language));
    }
}
