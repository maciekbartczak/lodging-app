import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-router-back',
    templateUrl: './router-back.component.html',
    styleUrls: ['./router-back.component.scss']
})
export class RouterBackComponent {

    @Input()
    public backRoute = '/';
}
