import { Component, OnInit } from '@angular/core';
import { AppStateService } from 'src/app/core/app-state.service';
import { HotelService } from '../../../core/openapi';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Hotel } from '../../core/model/hotel.model';

@Component({
    selector: 'app-hotel-list',
    templateUrl: './hotel-list.component.html',
    styleUrls: ['./hotel-list.component.scss']
})
export class HotelListComponent implements OnInit {

    hotels: Hotel[] = [];
    pageNumber = 0;
    pageSize = 5;
    totalItems = 0;
    loading = false;
    image?: SafeUrl;

    constructor(private hotelService: HotelService,
                private appState: AppStateService,
                private sanitizer: DomSanitizer) {
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
        this.hotelService.getPage({pageSize: this.pageSize, pageNumber: this.pageNumber}).subscribe(
            (data) => {
                const hotels = data.hotels;
                this.totalItems = data.totalItems;
                hotels.forEach(hotel => {
                    this.hotelService.getFile(hotel.imageName).subscribe(
                        response => {
                            const blob = new Blob([response], { type: 'image/png' });
                            const unsafeImage = URL.createObjectURL(blob);
                            const image = this.sanitizer.bypassSecurityTrustUrl(unsafeImage);
                            this.hotels.push({hotel: hotel, image: image});
                        });
                });
                this.appState.setLoading(false);
            });
    }

}
