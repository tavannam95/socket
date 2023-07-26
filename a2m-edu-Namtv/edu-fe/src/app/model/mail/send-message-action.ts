import { SmtpMessage } from "./smtp-message";

export class SendMessageAction {
    message?: SmtpMessage = new SmtpMessage();
    username?: string = "";
    password?: String = "";
    constructor(msg?: SmtpMessage, username?: string, password?: string){
        this.message = msg;
        this.username = username;
        this.password = password
    }
}
