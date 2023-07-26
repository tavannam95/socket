import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/guard/role.guard';
import { IqTestFormComponent } from './iqTestSet/iqTest-form/iqTest-form.component';
import { IqTestSetListComponent } from './iqTestSet/iqTest-list/iqTestSet-list.component';
import { IqtestStatisticalResultComponent } from './iqTestSet/iqtest-statistical-result/iqTest-statistical-result.component';
import { IqTestViewComponent } from './iqTestSet/iqTest-view/iqTest-view.component';
import { IqQuesFormComponent } from './quesIQ/ques-form/ques-form.component';
import { IqQuesListComponent } from './quesIQ/ques-list/ques-list.component';


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'ques0101',
        component: IqQuesListComponent,
      },
      {
        path: 'ques0101/iqQuesForm',
        component: IqQuesFormComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'ques0102',
        component: IqTestSetListComponent,
      },
      {
        path: 'ques0102/iqTestForm',
        component: IqTestFormComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'ques0102/iqTestView',
        component: IqTestViewComponent,
        canActivate: [RoleGuard],
      },
      // {
      //   path: 'ques0102/iqTestViewPublic',
      //   component: IqTestPublicComponent,
      //   // canActivate: [RoleGuard],
      // },
      {
        path: 'ques0102/iqtestStatistic',
        // component: IqTestStatisticalComponent,
        component: IqtestStatisticalResultComponent,
        canActivate: [RoleGuard],
      }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuesIqRoutingModule { }
