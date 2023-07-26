import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatDuoComponent } from './chat-duo/chat-duo.component';

const routes: Routes = [
  {
    path: '',
    component: ChatDuoComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChatRoutingModule { }
