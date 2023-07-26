import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Sam0101Component } from './sam0101/sam0101.component';
import { Sam0102Component } from './sam0102/sam0102.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'sim0101',
    pathMatch: 'full'
  },
  {
    path: 'sam0101',
    component: Sam0101Component
  },
  {
    path: 'sam0102',
    component: Sam0102Component
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SamRoutingModule { }
