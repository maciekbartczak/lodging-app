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
import { RouteNotFoundComponent } from './core/route-not-found/route-not-found.component';

export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}
@NgModule({
    declarations: [
        AppComponent,
        TopBarComponent,
        RouteNotFoundComponent
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
        MenuModule
    ],
    providers: [
        MessageService,
        {
            provide: Configuration,
            useValue: new Configuration({withCredentials: true})
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
