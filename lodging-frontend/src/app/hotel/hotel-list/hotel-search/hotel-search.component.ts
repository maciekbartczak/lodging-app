import { Component, EventEmitter, Output } from '@angular/core';
import { HotelSearchQuery } from '../../../common/model/hotelSearchData.model';

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
        const searchQuery: HotelSearchQuery = {
            city: this.cityQuery,
            startDate: this.dateRange?.length == 2 ? this.dateRange[0] : undefined,
            endDate: this.dateRange?.length == 2 ? this.dateRange[1] : undefined
        }

        this.searchAction.emit(searchQuery);
    }
}
