import { Component, OnInit } from '@angular/core';
import { HotelDto, HotelService } from '../../../core/openapi';
import { MenuItem, PrimeIcons } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-my-hotels-list',
    templateUrl: './my-hotels-list.component.html',
    styleUrls: ['./my-hotels-list.component.scss']
})
export class MyHotelsListComponent implements OnInit {

    hotels: HotelDto[] = [];
    selected?: HotelDto;

    items: MenuItem[] = [
        { label: this.translateService.instant('menu.edit'), icon: PrimeIcons.PENCIL },
        { label: this.translateService.instant('menu.delete'), icon: PrimeIcons.TIMES },
    ]

    constructor(private hotelService: HotelService,
                private translateService: TranslateService) {
    }

    ngOnInit(): void {
        this.hotelService.getOwnedHotels().subscribe(response => {
            this.hotels = response;
        });
    }

}
