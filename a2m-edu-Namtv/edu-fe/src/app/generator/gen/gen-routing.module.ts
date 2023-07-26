import { ListTargetComponent } from './gen0101/list-target/list-target.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageProfileComponent } from './gen0301/page-profile/page-profile.component';
import { RoleGuard } from 'src/app/guard/role.guard';
import { ListProjectComponent } from './gen0501/list-project/list-project.component';
import { Gen0601Component } from './gen0601/gen0601.component';
import { InteractiveGenComponent } from './gen0201/interactive-gen.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'gen0101',
        component: ListTargetComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'gen0301',
        component: PageProfileComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'gen0501',
        component: ListProjectComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'gen0601',
        component: Gen0601Component
      },
      {
        path: 'gen0201',
        component: InteractiveGenComponent,
        canActivate: [RoleGuard],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GenRoutingModule {}
