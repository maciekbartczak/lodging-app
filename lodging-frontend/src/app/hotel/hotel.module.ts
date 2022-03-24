import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HotelRoutingModule } from './hotel-routing.module';
import { HotelListComponent } from './hotel-list/hotel-list.component';


@NgModule({
  declarations: [
    HotelListComponent
  ],
  imports: [
    CommonModule,
    HotelRoutingModule
  ]
})
export class HotelModule { }
