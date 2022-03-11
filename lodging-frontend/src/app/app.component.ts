import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { UserStateService } from './core/user-state.service';
import { UserDto } from '../core/openapi';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    title = 'lodging-frontend';

    user?: UserDto;

    constructor(private translate: TranslateService,
                private userState: UserStateService) {

        translate.setDefaultLang('en');
        translate.use('pl');
    }

    ngOnInit(): void {

        this.userState.user.subscribe(
            user => this.user = user
        );
    }

}
