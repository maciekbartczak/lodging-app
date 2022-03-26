import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HotelRoutingModule } from './hotel-routing.module';
import { HotelListComponent } from './hotel-list/hotel-list.component';
import { HotelTableComponent } from './hotel-list/table/hotel-table.component';
import { PaginatorModule } from 'primeng/paginator';
import { CardModule } from 'primeng/card';
import { ImageModule } from 'primeng/image';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [
    HotelListComponent,
    HotelTableComponent
  ],
    imports: [
        CommonModule,
        HotelRoutingModule,
        PaginatorModule,
        CardModule,
        ImageModule,
        TranslateModule
    ]
})
export class HotelModule { }
