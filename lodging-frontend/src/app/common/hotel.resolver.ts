import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { HotelDto, HotelService } from '../../core/openapi';

@Injectable({
    providedIn: 'root'
})
export class HotelResolver implements Resolve<HotelDto> {

    constructor(private hotelService: HotelService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<HotelDto> {
        const id = route.params['id'];
        return this.hotelService.getHotelById(id);
    }
}
