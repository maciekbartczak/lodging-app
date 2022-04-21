import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HotelRoutingModule } from './hotel-routing.module';
import { HotelListComponent } from './hotel-list/hotel-list.component';
import { HotelTableComponent } from './hotel-list/table/hotel-table.component';
import { PaginatorModule } from 'primeng/paginator';
import { CardModule } from 'primeng/card';
import { ImageModule } from 'primeng/image';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonModule } from 'primeng/button';
import { AddHotelComponent } from './add-hotel/add-hotel.component';
import { HotelComponent } from './hotel.component';
import { CoreModule } from '../core/core.module';
import { InputTextModule } from 'primeng/inputtext';
import { ReactiveFormsModule } from '@angular/forms';
import { FileUploadModule } from 'primeng/fileupload';
import { SkeletonModule } from 'primeng/skeleton';
import { HotelCardComponent } from './hotel-list/table/card/hotel-card.component';
import { HotelSearchComponent } from './hotel-list/hotel-search/hotel-search.component';
import { CalendarModule } from 'primeng/calendar';
import { AddBookingComponent } from './add-booking/add-booking.component';
import { HotelResolver } from '../core/hotel.resolver';


@NgModule({
    declarations: [
        HotelComponent,
        HotelListComponent,
        HotelTableComponent,
        AddHotelComponent,
        HotelCardComponent,
        HotelSearchComponent,
        AddBookingComponent,
    ],
    imports: [
        CommonModule,
        HotelRoutingModule,
        PaginatorModule,
        CardModule,
        ImageModule,
        TranslateModule,
        ButtonModule,
        CoreModule,
        InputTextModule,
        ReactiveFormsModule,
        FileUploadModule,
        SkeletonModule,
        CalendarModule
    ],
    providers: [HotelResolver]
})
export class HotelModule {
}
