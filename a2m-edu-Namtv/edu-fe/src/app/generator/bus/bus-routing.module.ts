import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/guard/role.guard';
import { SubcribeManagementComponent } from './bus0101/subcribe-management/subcribe-management.component';
import { EventManagementComponent } from './bus0201/event-management/event-management.component';
import { ReportManagementComponent } from './bus0301/report-management.component';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', redirectTo: 'bus0101', pathMatch: 'full' },
      { path: 'bus0101', component: SubcribeManagementComponent, canActivate: [RoleGuard] },
      { path: 'bus0201', component: EventManagementComponent, canActivate: [RoleGuard] },
      { path: 'bus0301', component: ReportManagementComponent, canActivate: [RoleGuard] },


    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BusRoutingModule { }
