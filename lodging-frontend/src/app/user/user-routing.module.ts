import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuard } from '../common/user.guard';
import { MyHotelsListComponent } from './my-hotels/my-hotels-list.component';
import { UserDashboardComponent } from './dashboard/user-dashboard.component';
import { EditHotelComponent } from './my-hotels/edit-hotel/edit-hotel.component';
import { HotelResolver } from '../common/hotel.resolver';

const routes: Routes = [
    { path: '', canActivate: [ UserGuard ], component: UserDashboardComponent },
    { path: 'hotels', canActivate: [ UserGuard ], component: MyHotelsListComponent },
    { path: 'hotels/:id', canActivate: [ UserGuard ], component: EditHotelComponent, resolve: { hotel: HotelResolver } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
