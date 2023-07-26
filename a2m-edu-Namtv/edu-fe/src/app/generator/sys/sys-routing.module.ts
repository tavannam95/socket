import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/guard/role.guard';
import { MenuListComponent } from './sys0101/menu-list/menu-list.component';
import { Sys0102Component } from './sys0102/sys0102.component';
import { ListUserComponent } from './sys0103/list-user/list-user.component';
import { Sys0104Component } from './sys0104/sys0104.component';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', redirectTo: 'sys0101', pathMatch: 'full' },

      { path: 'sys0101', component: MenuListComponent, canActivate: [RoleGuard] },
      { path: 'sys0102', component: Sys0102Component, canActivate: [RoleGuard] },
      { path: 'sys0103', component: ListUserComponent, canActivate: [RoleGuard]},
      { path: 'sys0104', component: Sys0104Component, canActivate: [RoleGuard]},

    ]

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SysRoutingModule { }
