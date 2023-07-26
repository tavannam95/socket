import { SmtpMessage } from "./smtp-message";

export class SendMessageAction {
    message?: SmtpMessage
    username?: string = "";

    constructor(msg?: SmtpMessage, username?: string){
        this.message = msg;
        this.username = username;
    }
}
