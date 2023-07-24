import axios from "axios";

export class ChatService {

  private static _chatService: ChatService;

  public socketClient: any
  public ws: any;

  public static getInstance(): ChatService {
    if (!ChatService._chatService) {
      ChatService._chatService = new ChatService();
    }
    return ChatService._chatService;
  }

  public send(msg: string){
    return axios.get("http://localhost:8080/socket/send?content="+ msg);
  }

}
