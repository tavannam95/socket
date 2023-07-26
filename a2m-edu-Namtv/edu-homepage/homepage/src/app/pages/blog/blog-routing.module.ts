import { Blog0102Component } from './blog0102/blog0102.component';
import { Blog0101Component } from './blog0101/blog0101.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    children: [
      {path: '', component: Blog0101Component},
      {path: 'detail', component: Blog0102Component},
    ]    
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BlogRoutingModule { }
