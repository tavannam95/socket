import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MultiUploadComponent } from './multi-upload/multi-upload.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'upload',
        component: MultiUploadComponent,
      }
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FileRoutingModule { }
