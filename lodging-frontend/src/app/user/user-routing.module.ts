import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserGuard } from '../common/user.guard';
import { MyHotelsListComponent } from './my-hotels/my-hotels-list.component';
import { UserDashboardComponent } from './dashboard/user-dashboard.component';
import { EditHotelComponent } from './my-hotels/edit-hotel/edit-hotel.component';
import { HotelResolver } from '../common/hotel.resolver';
import { MyBookingsListComponent } from './my-bookings/my-bookings-list.component';

const routes: Routes = [
    { path: '', canActivate: [ UserGuard ], component: UserDashboardComponent },
    { path: 'hotels', canActivate: [ UserGuard ], component: MyHotelsListComponent },
    { path: 'hotels/:id', canActivate: [ UserGuard ], component: EditHotelComponent, resolve: { hotel: HotelResolver } },
    { path: 'bookings', canActivate: [ UserGuard ], component: MyBookingsListComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
