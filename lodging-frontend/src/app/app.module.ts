import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ApiModule } from '../core/openapi';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { ButtonModule } from 'primeng/button';
import { TopBarComponent } from './layout/top-bar/top-bar.component';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { AuthInterceptor } from './core/auth.interceptor';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}
@NgModule({
    declarations: [
        AppComponent,
        TopBarComponent
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
        ToastModule
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        MessageService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
