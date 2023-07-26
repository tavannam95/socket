import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import { BehaviorSubject } from 'rxjs';
import * as SockJS from 'sockjs-client';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  // notification = new BehaviorSubject<any>({});
  // getNotification = this.notification.asObservable();

  socketClient: any;
  username: string = "";
  ws: any;
  constructor() {
    this.username = AuthenticationUtil.getUserUid();
  }

  initSocket() {
    this.close();
    console.log("Initialize WebSocket Connection");
    this.ws = new SockJS(environment.wsURL);
    this.socketClient = Stomp.over(this.ws);
    // this.socketClient.connect({ username: this.username }, () => {
      this.socketClient.connect({ username: 'namtv' }, () => {
      console.log("Connexion started");
      this.getMessages();
    },
      this.errorCallBack
    );
  };

  errorCallBack() {
    setTimeout(() => {
      // initSocket();
    }, 5000);
  }

  getMessages() {
    this.socketClient.subscribe(
      '/chat/topic/messages',
      (msg: any) => {
        // this.notification.next(JSON.parse(msg.body));
        console.log(msg);
        
      },
      (err: any) => {
        // this.notification.error(err);
      }
    );
  }

  close() {
    if (this.socketClient !== null && this.socketClient != undefined) {
      this.socketClient.disconnect();
      this.socketClient = null;
    }
    console.log("Disconnected");
  }
}
