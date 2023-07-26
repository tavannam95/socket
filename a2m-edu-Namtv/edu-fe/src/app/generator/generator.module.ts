import { SharedModule } from './../shared/shared.module';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GeneratorRoutingModule } from './generator-routing.module';
import { GeneratorComponent } from './generator.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { MenuComponent } from './components/menu/menu.component';
import { ThemeSettingComponent } from './components/theme-setting/theme-setting.component';
import { MenuItemComponent } from './components/menu/menu-item/menu-item.component';
import { PopupModule } from './popup/popup.module';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { Edu0102Service } from '../services/edu/edu0102.service';
import { PostService } from '../services/community/post.service';
import { Course0101Service } from '../services/course/course0101.service';
import { ListApprovalComponent } from './components/header/list-approval/list-approval.component';
import { ApproveService } from '../services/common/approve.service';
import { TotalCandidateComponent } from './components/header/total-candidate/total-candidate.component';
import { ListPostNotificatonComponent } from './components/header/list-post-notificaton/list-post-notificaton.component';
import { ListApprovedComponent } from './components/header/list-approved/list-approved.component';

@NgModule({
  declarations: [
    GeneratorComponent,
    HeaderComponent,
    FooterComponent,
    MenuComponent,
    ThemeSettingComponent,
    MenuItemComponent,
    ListApprovalComponent,
    TotalCandidateComponent,
    ListPostNotificatonComponent,
    ListApprovedComponent
  ],
  imports: [
    CommonModule,
    GeneratorRoutingModule,
    SharedModule,
    PopupModule,
    AutoCompleteModule
  ],
  providers: [
    Edu0102Service,
    PostService,
    Course0101Service,
    ApproveService
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ]
})
export class GeneratorModule {}
