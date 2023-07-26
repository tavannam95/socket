import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BusRoutingModule } from './bus-routing.module';
import { SubcribeManagementComponent } from './bus0101/subcribe-management/subcribe-management.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MatDialogModule } from '@angular/material/dialog';
import { ConfirmationService, SharedModule } from 'primeng/api';
import { SharedPipeModule } from 'src/app/pipe/shared-pipe.module';
import { TreeTableModule } from 'primeng/treetable';
import { SubcribeMngFormComponent } from './bus0101/subcribe-mng-form/subcribe-mng-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { EventManagementComponent } from './bus0201/event-management/event-management.component';
import { EventManagementFormComponent } from './bus0201/event-management-form/event-management-form.component';
import { PaginatorModule } from 'primeng/paginator';
import {CalendarModule} from "primeng/calendar";
import { ReportManagementComponent } from './bus0301/report-management.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ChartModule} from "primeng/chart";
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import { CommonsModule } from '../commons/commons.module';
import { TooltipModule } from 'primeng/tooltip';


@NgModule({
  declarations: [
    SubcribeManagementComponent,
    SubcribeMngFormComponent,
    EventManagementComponent,
    EventManagementFormComponent,
    ReportManagementComponent
  ],
  imports: [
    CommonModule,
    CommonsModule,
    TooltipModule,
    BusRoutingModule,
    SharedModule,
    ConfirmDialogModule,
    MatDialogModule,
    SharedPipeModule,
    TreeTableModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    TranslateModule,
    PaginatorModule,
    CalendarModule,
    FontAwesomeModule,
    ChartModule,
    MatDatepickerModule,
    MatNativeDateModule

  ],
  providers: [ConfirmationService],
})
export class BusModule { }
