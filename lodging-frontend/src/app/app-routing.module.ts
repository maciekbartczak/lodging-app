import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouteNotFoundComponent } from './core/route-not-found/route-not-found.component';
import { MainComponent } from './main/main.component';
import { UserResolver } from './core/user.resolver';

const routes: Routes = [
    {
        path: '',
        redirectTo: '/hotel/list',
        pathMatch: 'full'
    },
    {
        path: '',
        component: MainComponent,
        runGuardsAndResolvers: "always",
        resolve: {
            user: UserResolver
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
