import { SendMessageAction } from "./send-message-action";

export class MailSalaryInfo {
    gmailColumn?: String;
    attachmentColumn?: String;
    startRow?: number;
    finishRow?: number;
    subject?: String;
    content?: String;
    fromUser?: String;

    constructor(gmailColumn?: String, attachmentColumn?: String, startRow?: number, finishRow?: number, subject?: String, content?: String, fromUser?: String){
        this.gmailColumn = gmailColumn;
        this.attachmentColumn = attachmentColumn;
        this.startRow = startRow;
        this.finishRow = finishRow;
        this.subject = subject;
        this.content = content;
        this.fromUser = fromUser;
    }
}
