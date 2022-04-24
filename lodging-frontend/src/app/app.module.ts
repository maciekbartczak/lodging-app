import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ApiModule, Configuration } from '../core/openapi';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
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
import { SlideMenuModule } from 'primeng/slidemenu';
import { MainComponent } from './main/main.component';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { CookieService } from 'ngx-cookie-service';
import { NgHttpCachingConfig, NgHttpCachingModule, NgHttpCachingStrategy } from 'ng-http-caching';

const cacheConfig: NgHttpCachingConfig = {
    lifetime: 1000 * 60 * 5,
    allowedMethod: ['GET', 'HEAD'],
    cacheStrategy: NgHttpCachingStrategy.ALLOW_ALL,
    isCacheable: req => {
        if (req.urlWithParams.indexOf('/hotel') !== -1) {
            return false;
        }
        return undefined;
    }
}

export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}
@NgModule({
    declarations: [
        AppComponent,
        TopBarComponent,
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
        NgHttpCachingModule.forRoot(cacheConfig),
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
        [
            CookieService,
        ]
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}


