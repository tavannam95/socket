import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ChatDuoService } from 'src/app/services/chat/chat-duo.service';
import { SocketService } from 'src/app/services/socket/socket.service';

@Component({
  selector: 'app-chat-duo',
  templateUrl: './chat-duo.component.html',
  styleUrls: ['./chat-duo.component.css']
})
export class ChatDuoComponent implements OnInit {

  content: any;

  constructor(
    private socketService: SocketService,
    private chatDuoService: ChatDuoService,
  ) {}

  

  ngOnInit(): void {
    this.socketService.initSocket();
  }

  // logout() {
  //   this.socketService.close();
  //   this.socketService.notification.next({});
  // }

  send(){
    console.log(this.content);
    this.chatDuoService.sendMessage(this.content).subscribe({
      next: res=>{
        console.log(res);
        
      },
      error: e=>{
        console.log(e);
        
      }
    })
  }

}
