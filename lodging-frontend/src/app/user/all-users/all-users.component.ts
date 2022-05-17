import { Component, OnInit } from '@angular/core';
import { HotelDto, HotelService, UserDto, UserService } from '../../../core/openapi';
import { MenuItem, MessageService, PrimeIcons } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AppStateService } from '../../common/app-state.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss']
})
export class AllUsersComponent implements OnInit {

    users: UserDto[] = [];
    selected?: UserDto;

    items: MenuItem[] = [
        { label: this.translateService.instant('menu.promote'), icon: PrimeIcons.USER, command: () => this.promoteUser(this.selected) },
    ]

    constructor(private userService: UserService,
                private translateService: TranslateService,
                private messageService: MessageService,
                private appState: AppStateService) {
    }

    ngOnInit(): void {
        this.fetchUsers();
    }

    private fetchUsers(): void {
        this.appState.setLoading(true);
        this.userService.getAllUsers().subscribe(response => {
            this.users = response;
            this.appState.setLoading(false);
        });
    }

    private promoteUser(selected: UserDto | undefined) {
        if (!selected) {
            return ;
        }

        this.appState.setLoading(true);
        this.userService.promoteUser(selected.id).subscribe({
            next: _ => {
                this.messageService.add({
                    severity: 'success',
                    summary: this.translateService.instant('hotel.booking.toast.success.header'),
                    detail: this.translateService.instant('user.promote.success')
                });
                this.fetchUsers();
            },
            error: _ => {
                this.messageService.add(
                    {
                        severity: 'error',
                        summary: this.translateService.instant('error.generic'),
                        detail: this.translateService.instant('error.try-again')
                    });
            }
        });
    }

}
