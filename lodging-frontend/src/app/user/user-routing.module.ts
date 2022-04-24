import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuard } from '../core/user.guard';
import { MyHotelsListComponent } from './my-hotels/my-hotels-list.component';

const routes: Routes = [
    { path: 'hotels', canActivate: [ UserGuard ], component: MyHotelsListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
