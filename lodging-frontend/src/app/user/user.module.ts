import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { MyHotelsListComponent } from './my-hotels/my-hotels-list.component';
import { CardModule } from 'primeng/card';
import { CoreModule } from '../core/core.module';
import { TranslateModule } from '@ngx-translate/core';
import { TableModule } from 'primeng/table';
import { ContextMenuModule } from 'primeng/contextmenu';
import { UserDashboardComponent } from './dashboard/user-dashboard.component';


@NgModule({
  declarations: [
      UserComponent,
      MyHotelsListComponent,
      UserDashboardComponent
  ],
    imports: [
        CommonModule,
        UserRoutingModule,
        CardModule,
        CoreModule,
        TranslateModule,
        TableModule,
        ContextMenuModule
    ]
})
export class UserModule { }
