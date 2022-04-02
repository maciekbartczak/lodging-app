import { Component, OnInit } from '@angular/core';
import { AppStateService } from 'src/app/core/app-state.service';
import { HotelDto, HotelService } from '../../../core/openapi';

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
    isLastPage = false;

    constructor(private hotelService: HotelService,
                private appState: AppStateService) {
    }

    ngOnInit() {
        this.fetchPage();
    }

    pageChanged(event: any) {
        this.pageNumber = event.page;
        this.pageSize = event.rows;
        this.hotels = [];
        this.isLastPage = event.page === event.pageCount - 1;
        this.fetchPage();
    }

    private fetchPage() {
        this.appState.setLoading(true);
        this.hotelService.getPage({pageSize: this.pageSize, pageNumber: this.pageNumber}).subscribe(
            response => {
                this.hotels = response.hotels;
                this.totalItems = response.totalItems;
                this.appState.setLoading(false);
            });
    }

}
