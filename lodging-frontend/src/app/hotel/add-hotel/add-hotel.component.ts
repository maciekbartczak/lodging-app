import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {  HotelService } from '../../../core/openapi';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AddHotelRequest } from '../../../core/openapi/model/addHotelRequest';

@Component({
    selector: 'app-add-hotel',
    templateUrl: './add-hotel.component.html',
    styleUrls: ['./add-hotel.component.scss']
})
export class AddHotelComponent {

    hotelForm: FormGroup;
    submitted = false;
    loading = false;
    image?: Blob;

    model: AddHotelRequest = {
        name: '',
        address: {
            country: '',
            city: '',
            street: ''
        },
        maxGuests: 0,
        pricePerNight: 0
    };

    constructor(private formBuilder: FormBuilder,
                private hotelService: HotelService,
                private messageService: MessageService,
                private translate: TranslateService) {
        this.hotelForm = formBuilder.group({
            name: ['', Validators.required],
            country: ['', Validators.required],
            city: ['', Validators.required],
            street: ['', Validators.required],
            maxGuests: [1, [Validators.required, Validators.min(1)]],
            pricePerNight: [1, [Validators.required, Validators.min(1)]],
        });
    }

    addHotel() {
        this.submitted = true;

        if(this.hotelForm.invalid || !this.image) {
            return;
        }

        this.loading = true;

        this.hotelService.addHotel(this.model, this.image).subscribe({
                next: _ => {
                    this.messageService.add(
                        {
                            severity: 'success',
                            summary: this.translate.instant('hotel.add.success')
                        });
                    this.loading = false;
                }
            });
    }

    imageSelected($event: any) {
        this.image = $event.files[0];
    }
}
