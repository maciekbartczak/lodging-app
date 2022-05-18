import { Component, OnInit } from '@angular/core';
import { UserDto, UserService } from '../../../core/openapi';
import { MenuItem, MessageService, PrimeIcons } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { AppStateService } from '../../common/app-state.service';
import { ContextMenu } from 'primeng/contextmenu';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss']
})
export class AllUsersComponent implements OnInit {

    users: UserDto[] = [];
    selected?: UserDto;
    currentUser?: UserDto;

    menuItems: MenuItem[] = [
        { label: this.translateService.instant('menu.promote'), icon: PrimeIcons.USER_PLUS, command: () => this.promoteUser(this.selected) },
        { label: this.translateService.instant('menu.demote'), icon: PrimeIcons.USER_MINUS, command: () => this.demote(this.selected) }
    ]
    constructor(private userService: UserService,
                private translateService: TranslateService,
                private messageService: MessageService,
                private appState: AppStateService) {
    }

    ngOnInit(): void {
        this.appState.user.subscribe(user => this.currentUser = user);
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

    private demote(selected: UserDto | undefined) {
        if (!selected) {
            return ;
        }

        this.appState.setLoading(true);
        this.userService.demoteUser(selected.id).subscribe({
            next: _ => {
                this.messageService.add({
                    severity: 'success',
                    summary: this.translateService.instant('hotel.booking.toast.success.header'),
                    detail: this.translateService.instant('user.demote.success')
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

    contextMenu($event: any, cm: ContextMenu) {

        if (this.selected?.id === this.currentUser?.id) {
            cm.hide();
        }
    }

    private isAdmin(user?: UserDto): boolean {
        console.log(user?.roles?.includes('ADMIN'))
        return user?.roles?.includes('ADMIN') ?? false;
    }

    getMenuItems() {
        if (this.isAdmin(this.selected)) {
            this.menuItems[0].visible = false;
            this.menuItems[1].visible = true;
        } else {
            this.menuItems[0].visible = true;
            this.menuItems[1].visible = false;
        }
        return this.menuItems;
    }
}
