import { ConfirmationService } from 'primeng/api';
import { SharedModule } from './../../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatDialogModule,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import {StepsModule} from 'primeng/steps';

import { GenRoutingModule } from './gen-routing.module';
import { ListTargetComponent } from './gen0101/list-target/list-target.component';
import { TargetFormComponent } from './gen0101/components/target-form/target-form.component';
import { SharedPipeModule } from 'src/app/pipe/shared-pipe.module';
import { PageProfileComponent } from './gen0301/page-profile/page-profile.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { CommonsModule } from '../commons/commons.module';
import { ListProjectComponent } from './gen0501/list-project/list-project.component';
import { ProjectFormComponent } from './gen0501/components/project-form/project-form.component';
import { MultiSelectModule } from 'primeng/multiselect';
import { FileUploadModule } from 'primeng/fileupload';
import { CalendarModule } from 'primeng/calendar';
import { Gen0601Component } from './gen0601/gen0601.component';
import { InteractiveGenComponent } from './gen0201/interactive-gen.component';
import { GojsAngularModule } from 'gojs-angular';
import { DiagramModule } from '@syncfusion/ej2-angular-diagrams';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import { ExtendsComponent } from './gen0601/components/extends/extends.component';
@NgModule({
  declarations: [
    ListTargetComponent,
    TargetFormComponent,
    PageProfileComponent,
    ListProjectComponent,
    ProjectFormComponent,
    Gen0601Component,
    InteractiveGenComponent,
    ExtendsComponent
  ],
  imports: [
    CommonModule,
    GenRoutingModule,
    SharedModule,
    ConfirmDialogModule,
    MatDialogModule,
    SharedPipeModule,
    CommonsModule,
    MultiSelectModule,
    FileUploadModule,
    CalendarModule,
    GojsAngularModule,
    DiagramModule,
    MatButtonModule,
    MatListModule,
    MatMenuModule,
    MatIconModule,
    StepsModule
  ],
  providers: [
    ConfirmationService,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
  ],
})
export class GenModule {}
