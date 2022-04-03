import { Component, OnInit, ViewChild } from '@angular/core';
import { AppStateService } from 'src/app/core/app-state.service';
import { HotelDto, HotelFilter, HotelService } from '../../../core/openapi';
import { HotelSearchQuery } from '../../core/model/hotelSearchData.model';
import { Paginator } from 'primeng/paginator';

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
    filter: HotelFilter = {
        city: '',
        bookingDetails: {
            startDate: '',
            endDate: ''
        }
    };

    @ViewChild('paginator', { static: true })
    paginator?: Paginator

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
        this.appState.setLoading(true);

        this.hotelService.getPage({pageSize: this.pageSize, pageNumber: this.pageNumber, filter: this.filter}).subscribe(
            response => {
                this.hotels = response.hotels;
                this.totalItems = response.totalItems;
                this.appState.setLoading(false);
            });
    }

    search(event: HotelSearchQuery) {
        this.filter = {
            city: event.city,
            bookingDetails: {
                startDate: event.startDate ? event.startDate.toISOString().substring(0, 10) : '',
                endDate: event.endDate ? event.endDate.toISOString().substring(0, 10) : ''
            }
        }
        this.pageNumber = 0;
        this.paginator?.changePage(this.pageNumber);

        this.fetchPage();
    }
}
