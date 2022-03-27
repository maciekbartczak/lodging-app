import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HotelListComponent } from './hotel-list/hotel-list.component';
import { AddHotelComponent } from './add-hotel/add-hotel.component';
import { UserGuard } from '../core/user.guard';

const routes: Routes = [
    { path: 'list', component: HotelListComponent },
    { path: 'add', canActivate: [ UserGuard ], component: AddHotelComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HotelRoutingModule { }
