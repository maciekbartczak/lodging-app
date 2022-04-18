import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookingDetails, HotelService } from '../../../core/openapi';
import { AppStateService } from '../../core/app-state.service';

@Component({
    selector: 'app-add-booking',
    templateUrl: './add-booking.component.html',
    styleUrls: ['./add-booking.component.scss']
})
export class AddBookingComponent implements OnInit {

    hotelId?: number;
    newBooking?: BookingDetails;
    bookedDates: Date[] = [];
    loading = true;

    today = new Date();
    dateRange: Date[] = [];

    constructor(private route: ActivatedRoute,
                private hotelService: HotelService,
                public appState: AppStateService) {
        this.route.params.subscribe(data => {
            this.hotelId = data['id'];
        });
    }

    ngOnInit(): void {
        if (this.hotelId) {
            this.appState.setLoading(true);
            this.hotelService.getBookings(this.hotelId).subscribe(bookings => {
                bookings.forEach(booking => {
                    const bookedDates = this.getDatesArray(booking.bookingDetails.startDate, booking.bookingDetails?.endDate);
                    this.bookedDates.push(...bookedDates);
                })
                this.appState.setLoading(false)
            });
        }
    }


    private getDatesArray(start: string, end: string): Date[] {
        const dates: Date[] = [];
        for (let dt = new Date(start); dt <= new Date(end); dt.setDate(dt.getDate() + 1)) {
            dates.push(new Date(dt));
        }
        return dates;
    };
}
