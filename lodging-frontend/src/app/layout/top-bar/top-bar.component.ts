import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserDto } from '../../../core/openapi';
import { MenuItem, PrimeIcons } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { Language } from '../../main/main.component';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss']
})
export class TopBarComponent implements OnInit {

    userItems: MenuItem[] = [];
    actionItems: MenuItem[] = [];
    languageItems: MenuItem[] = [
        { label: 'Polski', command: () => this.changeLanguage('pl') },
        { label: 'English', command: () => this.changeLanguage('en') },
    ]

    @Input()
    user?: UserDto;

    @Output()
    onLogout: EventEmitter<void> = new EventEmitter<void>();
    @Output()
    onLanguageChange: EventEmitter<Language> = new EventEmitter<Language>()

    constructor(private translateService: TranslateService) {
    }

    ngOnInit(): void {
        this.populateMenus();
    }

    private populateMenus(): void {
        this.translateService.get('topBar.menu.user.profile').subscribe(
            v => this.userItems.push({label: v, icon: PrimeIcons.USER})
        );
        this.translateService.get('topBar.language').subscribe(
            v => this.userItems.push({label: v, icon: PrimeIcons.FLAG, items: this.languageItems, })
        );
        this.translateService.get('topBar.menu.user.logout').subscribe(
            v => this.userItems.push(
                {
                    label: v,
                    icon: PrimeIcons.POWER_OFF,
                    command: _ => this.onLogout.emit()
                })
        );

        this.translateService.get('topBar.auth.login').subscribe(
            v => this.actionItems.push({label: v, icon: PrimeIcons.UNLOCK, routerLink: '/auth/login'})
        );
        this.translateService.get('topBar.auth.register').subscribe(
            v => this.actionItems.push({label: v, icon: PrimeIcons.USER_PLUS, routerLink: '/auth/register'})
        );
        this.translateService.get('topBar.language').subscribe(
            v => this.actionItems.push({label: v, icon: PrimeIcons.FLAG, items: this.languageItems, })
        );
    }

    private changeLanguage(language: Language): void {
        this.userItems = [];
        this.actionItems = [];

        this.onLanguageChange.emit(language);
        this.populateMenus();
    }
}
