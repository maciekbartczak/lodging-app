import { Component, OnInit } from '@angular/core';
import {
    NavigationCancel,
    NavigationEnd,
    NavigationError,
    NavigationStart,
    Router,
} from '@angular/router';
import { filter, map, Observable, of } from 'rxjs';

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html'
})
export class AppComponent implements OnInit {
    loading$: Observable<any> = of(false);

    constructor(private router: Router) {
    }

    ngOnInit(): void {
        this.loading$ = this.router.events.pipe(
            filter(
                (e) =>
                    e instanceof NavigationStart ||
                    e instanceof NavigationEnd ||
                    e instanceof NavigationCancel ||
                    e instanceof NavigationError
            ),
            map((e) => e instanceof NavigationStart)
        );
    }

}
