import { Component, OnInit } from '@angular/core';
import { HotelDto, HotelService } from '../../../core/openapi';
import { MenuItem, MessageService, PrimeIcons } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AppStateService } from '../../common/app-state.service';

@Component({
  selector: 'app-all-hotels',
  templateUrl: './all-hotels.component.html',
  styleUrls: ['./all-hotels.component.scss']
})
export class AllHotelsComponent {

    hotels: HotelDto[] = [];
    selected?: HotelDto;

    items: MenuItem[] = [
        { label: this.translateService.instant('menu.delete'), icon: PrimeIcons.TIMES, command: () => this.deleteHotel(this.selected) },
    ]

    constructor(private hotelService: HotelService,
                private translateService: TranslateService,
                private messageService: MessageService,
                private appState: AppStateService) {
    }

    ngOnInit(): void {
        this.fetchHotels();
    }

    private fetchHotels(): void {
        this.appState.setLoading(true);
        this.hotelService.getAll().subscribe(response => {
            this.hotels = response;
            this.appState.setLoading(false);
        });
    }

    private deleteHotel(selected: HotelDto | undefined) {
        if (!selected) {
            return ;
        }

        this.appState.setLoading(true);
        this.hotelService.deleteById(selected.id).subscribe({
            next: _ => {
                this.messageService.add({
                    severity: 'success',
                    summary: this.translateService.instant('hotel.booking.toast.success.header'),
                    detail: this.translateService.instant('user.hotels.delete.toast.success.content')
                });
                this.fetchHotels();
            },
            error: _ => {
                this.messageService.add(
                    {
                        severity: 'error',
                        summary: this.translateService.instant('error.generic'),
                        detail: this.translateService.instant('error.try-again')
                    });
            }
        });
    }
}
