import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouteNotFoundComponent } from './common/components/route-not-found/route-not-found.component';
import { MainComponent } from './main/main.component';
import { LanguageResolver } from './common/language.resolver';

const routes: Routes = [
    {
        path: '',
        redirectTo: '/hotel/list',
        pathMatch: 'full'
    },
    {
        path: '',
        component: MainComponent,
        resolve: {
            language: LanguageResolver,
        },
        children: [
            {
                path: 'auth',
                loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
            },
            {
                path: 'hotel',
                loadChildren: () => import('./hotel/hotel.module').then(m => m.HotelModule)
            },
            {
                path: 'user',
                loadChildren: () => import('./user/user.module').then(m => m.UserModule)
            },
            { path: '404', component: RouteNotFoundComponent },
        ]
    },
    { path: '**', redirectTo: '/404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
