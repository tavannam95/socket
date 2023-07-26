import { AbstractMessageImpl } from "./abstract-message-impl"
import { MailHeader } from "./mail-header"
import { MessageAttachment } from "./message-attachment"

export class SmtpMessage extends AbstractMessageImpl{
    // from: string
    bcc: string[] = []
    text: string = ""
    messageAttachments: MessageAttachment[] = []
    mailHeaders: MailHeader[] = []
}
