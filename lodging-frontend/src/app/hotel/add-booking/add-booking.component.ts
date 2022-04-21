import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Booking, CreateBookingRequest, HotelDto, HotelService } from '../../../core/openapi';
import { AppStateService } from '../../core/app-state.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-add-booking',
    templateUrl: './add-booking.component.html',
    styleUrls: ['./add-booking.component.scss']
})
export class AddBookingComponent {

    hotel: HotelDto;
    bookedDates: Date[] = [];
    loading = true;
    submitted = false;
    today = new Date();
    dateRange: Date[] = [];
    guestCount: number = 1;

    constructor(private route: ActivatedRoute,
                private hotelService: HotelService,
                public appState: AppStateService,
                private router: Router,
                private messageService: MessageService,
                private translateService: TranslateService) {
        this.hotel = this.route.snapshot.data['hotel'];
        this.getBookedDates([...this.hotel.bookings]);
    }

    private fetchBookings(): void {
        if (this.hotel?.id) {
            this.appState.setLoading(true);
            this.hotelService.getBookings(this.hotel.id).subscribe(bookings => {
                this.getBookedDates(bookings);
                this.appState.setLoading(false)
            });
        }
    }

    private getBookedDates(bookings: Booking[]): void {
        bookings.forEach(booking => {
            const bookedDates = this.getDatesArray(booking.bookingDetails.startDate, booking.bookingDetails?.endDate);
            this.bookedDates.push(...bookedDates);
        })
    }

    private getDatesArray(start: string, end: string): Date[] {
        const dates: Date[] = [];
        for (let dt = new Date(start); dt <= new Date(end); dt.setDate(dt.getDate() + 1)) {
            dates.push(new Date(dt));
        }
        return dates;
    }

    createBooking(): void {
        if (!this.hotel || this.dateRange.length < 1) {
            return
        }

        const createBookingRequest: CreateBookingRequest = {
            guestCount: this.guestCount,
            startDate: this.dateRange[0].toLocaleDateString('sv'),
            endDate: this.dateRange[1] ? this.dateRange[1].toLocaleDateString('sv') :
                this.dateRange[0].toLocaleDateString('sv')
        };

        this.hotelService.createBooking(this.hotel.id, createBookingRequest).subscribe({
            next: _ => {
                this.fetchBookings();
                this.dateRange = [];
                this.messageService.add({
                    severity: 'success',
                    summary: this.translateService.instant('hotel.booking.toast.success.header'),
                    detail: this.translateService.instant('hotel.booking.toast.success.content')
                });
            },
            error: _ => {
                this.messageService.add({
                    severity: 'error',
                    summary: this.translateService.instant('error.generic'),
                    detail: this.translateService.instant('hotel.booking.toast.error.content')
                });
            }
        });

    }

}
