import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';

import { TreeTableModule } from 'primeng/treetable';
import { TabViewModule } from 'primeng/tabview';
import {CheckboxModule} from 'primeng/checkbox';
import {CalendarModule} from 'primeng/calendar';
import {MenuModule} from 'primeng/menu';


import { SysRoutingModule } from './sys-routing.module';
import { SysComponent } from './sys.component';
import { MenuListComponent } from './sys0101/menu-list/menu-list.component';
import { MenuDetailFormComponent } from './sys0101/components/menu-detail-form/menu-detail-form.component';
import { MenuSearchFormComponent } from './sys0101/components/menu-search-form/menu-search-form.component';
import { ConfirmationService } from 'primeng/api';
import { Sys0101Service } from 'src/app/services/sys/sys0101.service';
import { Sys0101wComponent } from './sys0101/components/sys0101w/sys0101w.component';
import { DropdownModule } from 'primeng/dropdown';
import { Sys0102Component } from './sys0102/sys0102.component';
import { RoleListComponent } from './sys0102/components/role-list/role-list.component';
import { RoleUserComponent } from './sys0102/components/role-user/role-user.component';
import { UserRoleComponent } from './sys0102/components/user-role/user-role.component';
import { ProgramRoleComponent } from './sys0102/components/program-role/program-role.component';
import { RoleProgramComponent } from './sys0102/components/role-program/role-program.component';


import { SharedPipeModule } from 'src/app/pipe/shared-pipe.module';
import { TranslateModule } from '@ngx-translate/core';
import { ListUserComponent } from './sys0103/list-user/list-user.component';
import { UserFormComponent } from './sys0103/component/user-form/user-form.component';

import { SharedModule } from 'src/app/shared/shared.module';
import { CommonsModule } from '../commons/commons.module';
import { TsstUserInfo } from 'src/app/model/gen/tsst-user-info';
import { Sys0104Component } from './sys0104/sys0104.component';
import { Sys0104wComponent } from './sys0104/sys0104w/sys0104w.component';
import { RoleFormComponent } from './sys0102/components/role-list/role-form/role-form.component';




@NgModule({
  declarations: [
    SysComponent,
    MenuListComponent,
    MenuDetailFormComponent,
    MenuSearchFormComponent,
    Sys0101wComponent,
    Sys0102Component,
    RoleListComponent,
    RoleUserComponent,
    UserRoleComponent,
    ProgramRoleComponent,


    RoleProgramComponent,
    ListUserComponent,
    UserFormComponent,
    Sys0104Component,
    Sys0104wComponent,
    RoleFormComponent,
  ],
  imports: [
    CommonModule,
    SysRoutingModule,
    TreeTableModule,
    DropdownModule,
    TabViewModule,
    CheckboxModule,
    SharedModule,
    CommonsModule,
    SharedPipeModule,
    CalendarModule,
    MenuModule
  ],
  providers: [
    ConfirmationService,
    Sys0101Service,
    TsstUserInfo,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
  ],
})
export class SysModule {}
