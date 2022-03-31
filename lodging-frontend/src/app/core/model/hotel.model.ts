import { HotelDto } from '../../../core/openapi';
import { SafeUrl } from '@angular/platform-browser';

export interface Hotel {
    hotel: HotelDto,
    image: SafeUrl
}
