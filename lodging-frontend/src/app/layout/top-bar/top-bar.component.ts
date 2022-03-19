import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserDto } from '../../../core/openapi';
import { MenuItem, PrimeIcons } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit {

    constructor(private translateService: TranslateService) {
    }

    userItems: MenuItem[] = [];

    @Input()
    user?: UserDto;
    @Input()
    loading = false;

    @Output()
    logout: EventEmitter<void> = new EventEmitter<void>();

    ngOnInit(): void {
        this.translateService.get('topBar.menu.user.profile').subscribe(
            v => this.userItems.push({label: v, icon: PrimeIcons.USER})
        );
        this.translateService.get('topBar.menu.user.logout').subscribe(
            v => this.userItems.push(
                {
                    label: v,
                    icon: PrimeIcons.POWER_OFF,
                    command: _ => this.logout.emit()
                })
        );
    }
}
