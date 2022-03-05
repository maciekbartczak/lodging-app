import {Component, OnInit} from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import {AuthService, FooControllerService} from "../core/openapi";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
    title = 'lodging-frontend';

    token: String = '';

    constructor(private authService: AuthService,
                private fooService: FooControllerService,
                private translate: TranslateService) {

                    translate.setDefaultLang('en');
                    translate.use('pl');
    }

    ngOnInit(): void {
        this.authService.login({username: "string", password: "string"})
            .subscribe(res => {
                this.token = res.token;
                console.log(res);
            });

        this.fooService.getBar()
            .subscribe();
    }


}
