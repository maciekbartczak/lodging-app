import { NgModule } from '@angular/core';
import { RouteNotFoundComponent } from './components/route-not-found/route-not-found.component';
import { RouterBackComponent } from './components/router-back/router-back.component';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonModule } from 'primeng/button';

@NgModule({
    declarations: [
        RouteNotFoundComponent,
        RouterBackComponent
    ],
    exports: [
        RouterBackComponent
    ],
    imports: [
        RouterModule,
        TranslateModule,
        ButtonModule
    ]
})
export class CoreModule {
}
