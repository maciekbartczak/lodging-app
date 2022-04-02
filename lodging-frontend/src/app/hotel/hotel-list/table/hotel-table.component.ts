import { Component, Input } from '@angular/core';
import { HotelDto } from '../../../../core/openapi';

@Component({
    selector: 'app-hotel-table',
    templateUrl: './hotel-table.component.html',
    styleUrls: ['./hotel-table.component.scss']
})
export class HotelTableComponent {

    @Input()
    hotels: HotelDto[] = [];
    @Input()
    itemCount = 0;
    @Input()
    isLastPage = true;
}
