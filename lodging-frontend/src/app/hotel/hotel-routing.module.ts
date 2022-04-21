import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HotelListComponent } from './hotel-list/hotel-list.component';
import { AddHotelComponent } from './add-hotel/add-hotel.component';
import { UserGuard } from '../core/user.guard';
import { AddBookingComponent } from './add-booking/add-booking.component';
import { HotelResolver } from '../core/hotel.resolver';

const routes: Routes = [
    { path: 'list', component: HotelListComponent },
    { path: 'add', canActivate: [ UserGuard ], component: AddHotelComponent },
    { path: ':id/booking', canActivate: [ UserGuard ], component: AddBookingComponent,
        resolve: { hotel: HotelResolver } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HotelRoutingModule { }
