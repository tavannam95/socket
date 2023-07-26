import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ChartModule } from 'primeng/chart';
import { CommonsModule } from '../commons/commons.module';
import { TranslateModule } from '@ngx-translate/core';
import { EduModule } from '../edu/edu.module';
import { FullCalendarModule } from '@fullcalendar/angular';

@NgModule({
  declarations: [DashboardComponent],
  imports: [
    FullCalendarModule,
    CommonModule,
    DashboardRoutingModule,
    FontAwesomeModule,
    ChartModule,
    CommonsModule,
    TranslateModule,
    EduModule,
  ],
  exports: [
    
  ],
})
export class DashboardModule {}
