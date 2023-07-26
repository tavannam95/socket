import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/guard/role.guard';
import { PageProfileComponent } from './page-profile.component';

const routes: Routes = [
  {
    path: '',
    component: PageProfileComponent,
    canActivate: [RoleGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PageProfileRoutingModule { }
