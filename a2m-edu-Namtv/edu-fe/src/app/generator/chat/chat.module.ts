import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChatRoutingModule } from './chat-routing.module';
import { ChatDuoComponent } from './chat-duo/chat-duo.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ChatDuoService } from 'src/app/services/chat/chat-duo.service';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ChatDuoComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    ChatRoutingModule
  ],
  providers: [
    ChatDuoService
  ]
})
export class ChatModule { }
