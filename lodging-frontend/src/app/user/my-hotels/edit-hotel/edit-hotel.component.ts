import { Component, OnInit } from '@angular/core';
import { AddHotelRequest, HotelDto, HotelService } from '../../../../core/openapi';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-edit-hotel',
    templateUrl: './edit-hotel.component.html',
    styleUrls: ['./edit-hotel.component.scss']
})
export class EditHotelComponent {

    hotel: HotelDto;
    hotelForm: FormGroup;
    submitted = false;
    loading = false;

    model: AddHotelRequest;

    constructor(private route: ActivatedRoute,
                private formBuilder: FormBuilder,
                private hotelService: HotelService,
                private messageService: MessageService,
                private translate: TranslateService) {
        this.hotel = this.route.snapshot.data['hotel'];
        this.hotelForm = formBuilder.group({
            name: ['', Validators.required],
            country: ['', Validators.required],
            city: ['', Validators.required],
            street: ['', Validators.required],
            maxGuests: [1, [Validators.required, Validators.min(1)]],
            pricePerNight: [1, [Validators.required, Validators.min(1)]],
        });
        this.model = {
            name: this.hotel.name,
            address: this.hotel.address,
            maxGuests: this.hotel.maxGuests,
            pricePerNight: this.hotel.pricePerNight,
        }
    }

    addHotel() {
        this.submitted = true;

        if(this.hotelForm.invalid) {
            return;
        }

        this.loading = true;

        this.hotelService.editHotel(this.hotel.id, this.model).subscribe({
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
        const file = $event.files[0];

        const reader = new FileReader();
        reader.onload = (e: any) => {
            const image = new Image();
            image.src = e.target.result;
            console.log(image.src);
            image.onload = rs => {
                this.model.image = e.target.result
            }

        }

        reader.readAsDataURL(file)
    }
}
