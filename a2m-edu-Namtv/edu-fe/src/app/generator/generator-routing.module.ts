import { GeneratorComponent } from './generator.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  {
    path: '',
    component: GeneratorComponent,
    children: [
      {
        path: '',
        // component: DashboardComponent,
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(m=>m.DashboardModule),
      },
      {
        path: 'client-dashboard',
        loadChildren: () => import('./client-dashboard/client-dashboard.module').then(m=>m.ClientDashboardModule)
      },
      {
        path: 'searchtag',
        loadChildren: () => import('./search-tag/search-tag/search-tag.module').then(m=>m.SearchTagModule)
      },
      {
        path: 'my-profile',
        loadChildren: () => import('./page-profile/page-profile.module').then(m=>m.PageProfileModule)
      },
      {
        path: 'sys',
        loadChildren: () => import('./sys/sys.module').then(m=>m.SysModule)
      },
      {
        path: 'gen',
        loadChildren: () => import('./gen/gen.module').then(m=>m.GenModule)
      },
      {
        path: 'bus',
        loadChildren: () => import('./bus/bus.module').then(m=>m.BusModule)
      },
      {
        path: 'sam',
        loadChildren: () => import('./sam/sam.module').then(m=>m.SamModule)
      },
      {
        path: 'edu',
        loadChildren: () => import('./edu/edu.module').then(m=>m.EduModule)
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m=>m.CourseModule)
      },
      {
        path: 'ques',
        loadChildren: () => import('./iqTest/quesIq.module').then(m=>m.QuesIqModule)
      },
      {
        path: 'multi-upload',
        loadChildren: () => import('./file/file.module').then(m=>m.FileModule)
      },
      {
        path: 'community',
        loadChildren: () => import('./community/community.module').then(m=>m.CommunityModule)
      },
      {
        path: 'mail',
        loadChildren: () => import('./mail/mail.module').then(m=>m.MailModule)
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GeneratorRoutingModule { }
