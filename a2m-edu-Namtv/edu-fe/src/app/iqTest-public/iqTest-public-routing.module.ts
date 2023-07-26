import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IqTestPublicComponent } from './iqTest-public.component';


const routes: Routes = [
  {
    path: '',
    component: IqTestPublicComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class iqTestPublicRoutingModule { }
