import { Students0101Component } from './students0101/students0101.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Students0102Component } from './students0102/students0102.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {path: '', component: Students0101Component},
      // {path: 'detail', component: Students0102Component},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentsRoutingModule { }
