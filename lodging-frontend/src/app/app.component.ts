import { Component, OnInit } from '@angular/core';
import {
    NavigationCancel,
    NavigationEnd,
    NavigationError,
    NavigationStart,
    Router,
} from '@angular/router';
import { filter, map, Observable, of } from 'rxjs';
import { AppStateService } from './core/app-state.service';

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html'
})
export class AppComponent implements OnInit {
    loading = false;

    constructor(private router: Router,
                private appState: AppStateService) {
    }
    
 

    ngOnInit(): void {
        this.router.events.pipe(
            filter(
                (e) =>
                    e instanceof NavigationStart ||
                    e instanceof NavigationEnd ||
                    e instanceof NavigationCancel ||
                    e instanceof NavigationError
            ),
            map((e) => e instanceof NavigationStart)
        ).subscribe(loading => this.loading = loading);
        
        this.appState.loading.subscribe(
            loading => {
                this.loading = loading;
            });
    }

}
