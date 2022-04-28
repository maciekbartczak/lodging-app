import { Component } from '@angular/core';
import { AppStateService } from '../../core/app-state.service';
import { UserDto } from '../../../core/openapi';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.scss']
})
export class UserDashboardComponent {

    currentUser?: UserDto;

    constructor(public appState: AppStateService) {
        this.appState.user.subscribe(user => {
            this.currentUser = user;
        });
    }

}
