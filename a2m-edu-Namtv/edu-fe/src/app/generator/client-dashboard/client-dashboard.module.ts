import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientDashboardRoutingModule } from './client-dashboard-routing.module';
import { ClientDashboardComponent } from './client-dashboard.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {ChartModule} from "primeng/chart";
import {CommonsModule} from "../commons/commons.module";


@NgModule({
  declarations: [
    ClientDashboardComponent
  ],
    imports: [
        CommonModule,
        ClientDashboardRoutingModule,
        FontAwesomeModule,
        ChartModule,
        CommonsModule,
    ]
})
export class ClientDashboardModule {

}
