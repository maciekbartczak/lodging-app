import { Component, OnInit } from '@angular/core';
import { Booking, BookingService } from '../../../core/openapi';
import { MenuItem, MessageService, PrimeIcons } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AppStateService } from '../../common/app-state.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-bookings-list',
  templateUrl: './my-bookings-list.component.html',
  styleUrls: ['./my-bookings-list.component.scss']
})
export class MyBookingsListComponent implements OnInit {

    bookings: Booking[] = [];
    selected?: Booking;

    items: MenuItem[] = [
        { label: this.translateService.instant('menu.delete'), icon: PrimeIcons.TIMES, command: () => this.deleteBooking(this.selected) },
    ]

    constructor(private bookingService: BookingService,
                private translateService: TranslateService,
                private messageService: MessageService,
                private appState: AppStateService) {
    }

    ngOnInit(): void {
        this.fetchBookings();
    }

    private fetchBookings(): void {
        this.appState.setLoading(true);
        this.bookingService.getMyBookings().subscribe(response => {
            this.bookings = response;
            this.appState.setLoading(false);
        });
    }

    private deleteBooking(selected: Booking | undefined) {
        if (!selected || !selected.id) {
            return ;
        }

        this.appState.setLoading(true);
        this.bookingService.deleteBooking(selected.id).subscribe(_ => {
            this.messageService.add({ severity: 'success', summary: this.translateService.instant('user.booking.delete.success') });
            this.fetchBookings();
        });

    }

}
