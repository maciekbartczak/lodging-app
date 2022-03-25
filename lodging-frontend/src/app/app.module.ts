import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ApiModule, Configuration } from '../core/openapi';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { ButtonModule } from 'primeng/button';
import { TopBarComponent } from './layout/top-bar/top-bar.component';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { AvatarModule } from 'primeng/avatar';
import { MenuModule } from 'primeng/menu';
import { RouteNotFoundComponent } from './core/route-not-found/route-not-found.component';
import { SlideMenuModule } from 'primeng/slidemenu';
import { MainComponent } from './main/main.component';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { firstValueFrom } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}
@NgModule({
    declarations: [
        AppComponent,
        TopBarComponent,
        RouteNotFoundComponent,
        MainComponent
    ],
    imports: [
        BrowserModule,
        CommonModule,
        AppRoutingModule,
        ApiModule,
        HttpClientModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: HttpLoaderFactory,
                deps: [HttpClient]
            },
            defaultLanguage: 'en'
        }),
        BrowserAnimationsModule,
        ButtonModule,
        MenubarModule,
        InputTextModule,
        ToastModule,
        AvatarModule,
        MenuModule,
        SlideMenuModule,
        ProgressSpinnerModule
    ],
    providers: [
        MessageService,
        {
            provide: Configuration,
            useValue: new Configuration({withCredentials: true})
        },
        {
            provide: APP_INITIALIZER,
            useFactory: appInitializerFactory,
            deps: [TranslateService, CookieService],
            multi: true
        },
        [
            CookieService,
        ]
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

export function appInitializerFactory(translateService: TranslateService, cookieService: CookieService) {
    return () => {
        translateService.setDefaultLang('en');
        let language = cookieService.get('app-lang');
        language = language ? language : 'en';
        return firstValueFrom(translateService.use(language));
    }
}
