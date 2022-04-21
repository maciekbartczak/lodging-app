import { Component, Input, OnInit } from '@angular/core';
import { HotelDto, HotelService } from '../../../../../core/openapi';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Component({
    selector: 'app-hotel-card',
    templateUrl: './hotel-card.component.html',
    styleUrls: ['./hotel-card.component.scss']
})
export class HotelCardComponent implements OnInit {

    @Input()
    hotel?: HotelDto;
    @Input()
    showBookingButton = true;
    image?: SafeUrl;
    loading = false;


    constructor(private hotelService: HotelService,
                private sanitizer: DomSanitizer,
                private router: Router) {
    }

    ngOnInit(): void {
        if (!this.hotel) {
            return ;
        }

        this.loading = true;
        this.hotelService.getFile(this.hotel.imageName).subscribe(
            response => {
                const blob = new Blob([response], { type: 'image/png' });
                const unsafeImage = URL.createObjectURL(blob);
                this.image = this.sanitizer.bypassSecurityTrustUrl(unsafeImage);
                this.loading = false;
            });
    }

    navigateToBooking(hotel: HotelDto): void {
        this.router.navigate([`/hotel/${hotel.id}/booking`], { state: { hotel: hotel } });
    }
}
