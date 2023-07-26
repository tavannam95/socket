import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MailComponent } from './mail.component';
import { Mail0101Component } from './mail0101/mail0101.component';
import { Mail0102Component } from './mail0102/mail0102.component';
import { Mail0103Component } from './mail0103/mail0103.component';
import { Mail0104Component } from './mail0104/mail0104.component';
import { Mail0105Component } from './mail0105/mail0105.component';
import { Mail0106Component } from './mail0106/mail0106.component';
import { RoleGuard } from 'src/app/guard/role.guard';

const routes: Routes = [
  {
    path: '',
    component: MailComponent,
    children: [
      // {
      //   path: '',
      //   redirectTo: 'mail0101',
      //   pathMatch: 'full',
      // },
      {
        path: 'mail0101',
        component: Mail0101Component, canActivate: [RoleGuard]
      },
      {
        path: 'mail0102',
        component: Mail0102Component, canActivate: [RoleGuard]
      },
      {
        path: 'mail0103',
        component: Mail0103Component, canActivate: [RoleGuard]
      },

      {
        path: 'mail0104',
        component: Mail0104Component, canActivate: [RoleGuard]
      },
      {
        path: 'mail0106',
        component: Mail0106Component, canActivate: [RoleGuard]
      },
      {
        path: 'mail0101/detail',
        component: Mail0105Component, canActivate: [RoleGuard]
      },
      {
        path: 'mail0102/detail',
        component: Mail0105Component, canActivate: [RoleGuard]
      },
      {
        path: 'mail0104/detail',
        component: Mail0105Component
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MailVNRoutingModule { }
