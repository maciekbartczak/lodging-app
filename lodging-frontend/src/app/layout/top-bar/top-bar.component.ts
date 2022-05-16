import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserDto } from '../../../core/openapi';
import { MenuItem, PrimeIcons } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { Language } from '../../main/main.component';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent {


    languageItems: MenuItem[] = [
        { label: 'Polski', command: () => this.changeLanguage('pl') },
        { label: 'English', command: () => this.changeLanguage('en') },
    ]
    userItems: MenuItem[] = [
        { label: this.translateService.instant('topBar.menu.user.profile'), icon: PrimeIcons.USER, routerLink: '/user' },
        { label: this.translateService.instant('topBar.language'), icon: PrimeIcons.FLAG, items: this.languageItems },
        {
            label: this.translateService.instant('topBar.menu.user.logout'),
            icon: PrimeIcons.POWER_OFF,
            command: _ => this.onLogout.emit()
        }
    ];
    adminItems: MenuItem[] = [
        { label: this.translateService.instant('admin.panel'), icon: PrimeIcons.SERVER, routerLink: '/user' },
        { label: this.translateService.instant('topBar.language'), icon: PrimeIcons.FLAG, items: this.languageItems },
        {
            label: this.translateService.instant('topBar.menu.user.logout'),
            icon: PrimeIcons.POWER_OFF,
            command: _ => this.onLogout.emit()
        }
    ];
    actionItems: MenuItem[] = [
        { label: this.translateService.instant('topBar.auth.login'), icon: PrimeIcons.UNLOCK, routerLink: '/auth/login' },
        { label: this.translateService.instant('topBar.auth.register'), icon: PrimeIcons.USER_PLUS, routerLink: '/auth/register' },
        { label: this.translateService.instant('topBar.language'), icon: PrimeIcons.FLAG, items: this.languageItems }
    ];


    @Input()
    user?: UserDto;

    @Output()
    onLogout: EventEmitter<void> = new EventEmitter<void>();
    @Output()
    onLanguageChange: EventEmitter<Language> = new EventEmitter<Language>()

    constructor(private translateService: TranslateService) {
    }

    private changeLanguage(language: Language): void {
        this.onLanguageChange.emit(language);
    }
}
