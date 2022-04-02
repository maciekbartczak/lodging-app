import { Component, OnInit } from '@angular/core';
import { AppStateService } from 'src/app/core/app-state.service';
import { HotelDto, HotelSearchRequest, HotelService } from '../../../core/openapi';
import { HotelSearchQuery } from '../../core/model/hotelSearchData.model';

@Component({
    selector: 'app-hotel-list',
    templateUrl: './hotel-list.component.html',
    styleUrls: ['./hotel-list.component.scss']
})
export class HotelListComponent implements OnInit {

    hotels: HotelDto[] = [];
    pageNumber = 0;
    pageSize = 5;
    totalItems = 0;
    isSearch = false;

    constructor(private hotelService: HotelService,
                private appState: AppStateService) {
    }

    ngOnInit() {
        this.fetchPage();
    }

    pageChanged(event: any) {
        this.pageNumber = event.page;
        this.pageSize = event.rows;
        this.fetchPage();
    }

    private fetchPage() {
        this.hotels = [];
        this.isSearch = false;
        this.appState.setLoading(true);

        this.hotelService.getPage({pageSize: this.pageSize, pageNumber: this.pageNumber}).subscribe(
            response => {
                this.hotels = response.hotels;
                this.totalItems = response.totalItems;
                this.appState.setLoading(false);
            });
    }

    search(event: HotelSearchQuery) {
        this.hotels = [];
        this.isSearch = true;
        this.appState.setLoading(true);

        const searchRequest: HotelSearchRequest = {
            city: event.city,
            bookingDetails: {
                startDate: event.startDate.toISOString().substring(0, 10),
                endDate: event.endDate.toISOString().substring(0, 10)
            }
        }

        this.hotelService.search(searchRequest).subscribe(response => {
            this.hotels = response.hotels;
            this.appState.setLoading(false);
        });
    }
}
