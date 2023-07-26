import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/guard/role.guard';
import { SearchTagComponent } from './search-tag.component';

const routes: Routes = [
  {
    path: '',
    component: SearchTagComponent
    // canActivate: [RoleGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchTagRoutingModule { }
