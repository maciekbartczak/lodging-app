import { Component, EventEmitter, Output } from '@angular/core';
import { HotelSearchQuery } from '../../../core/model/hotelSearchData.model';

@Component({
    selector: 'app-hotel-search',
    templateUrl: './hotel-search.component.html',
    styleUrls: ['./hotel-search.component.scss']
})
export class HotelSearchComponent {

    @Output()
    searchAction: EventEmitter<HotelSearchQuery> = new EventEmitter<HotelSearchQuery>();

    today = new Date();
    dateRange: Date[] = [];
    cityQuery = '';


    search() {
        if (!this.cityQuery || this.dateRange.length !== 2) {
            return ;
        }
        const searchQuery: HotelSearchQuery = {
            city: this.cityQuery,
            endDate: this.dateRange[0],
            startDate: this.dateRange[1]
        }

        this.searchAction.emit(searchQuery);
    }
}
