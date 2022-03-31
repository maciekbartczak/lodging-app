import { Component, Input } from '@angular/core';
import { Hotel } from '../../../core/model/hotel.model';

@Component({
    selector: 'app-hotel-table',
    templateUrl: './hotel-table.component.html',
    styleUrls: ['./hotel-table.component.scss']
})
export class HotelTableComponent {

    @Input()
    hotels: Hotel[] = [];

}
