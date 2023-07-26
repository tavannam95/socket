import { Instructors0102Component } from './instructors0102/instructors0102.component';
import { Instructors0101Component } from './instructors0101/instructors0101.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    children: [
      {path: '', component: Instructors0101Component},
      {path: 'detail', component: Instructors0102Component},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InstructorsRoutingModule { }
