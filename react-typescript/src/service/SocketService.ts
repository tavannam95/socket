
import Stomp from "stompjs";
import SockJS from "sockjs-client";
export class SocketService {

  private static _socketService: SocketService;

  public socketClient: any
  public ws: any;

  public static getInstance(): SocketService {
    if (!SocketService._socketService) {
      SocketService._socketService = new SocketService();
    }
    return SocketService._socketService;
  }

  public initSocket() {
    this.close();
    console.log("Initialize WebSocket Connection");
    this.ws = new SockJS("http://localhost:8080/hello");
    this.socketClient = Stomp.over(this.ws);
    // this.socketClient.connect({ username: this.username }, () => {
    //   console.log("Connexion started");
    //   this.getMessages();
    // },
    //   this.errorCallBack
    // );
  }

  public close() {
    if (this.socketClient !== null && this.socketClient !== undefined) {
      this.socketClient.disconnect();
      this.socketClient = null;
    }
    console.log("Disconnected");
  }

}
