import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { MyHotelsListComponent } from './my-hotels/my-hotels-list.component';
import { CardModule } from 'primeng/card';
import { CoreModule } from '../common/core.module';
import { TranslateModule } from '@ngx-translate/core';
import { TableModule } from 'primeng/table';
import { ContextMenuModule } from 'primeng/contextmenu';
import { UserDashboardComponent } from './dashboard/user-dashboard.component';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { EditHotelComponent } from './my-hotels/edit-hotel/edit-hotel.component';
import { InputNumberModule } from 'primeng/inputnumber';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
      UserComponent,
      MyHotelsListComponent,
      UserDashboardComponent,
      EditHotelComponent
  ],
    imports: [
        CommonModule,
        UserRoutingModule,
        CardModule,
        CoreModule,
        TranslateModule,
        TableModule,
        ContextMenuModule,
        ButtonModule,
        RippleModule,
        InputNumberModule,
        FileUploadModule,
        InputTextModule,
        ReactiveFormsModule
    ]
})
export class UserModule { }
