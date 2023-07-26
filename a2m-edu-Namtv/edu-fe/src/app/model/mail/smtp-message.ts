import { AbstractMessageImpl } from './abstract-messag-Impl';
import { MailHeader } from './mail-header';
import { MessageAttachment } from './message-attachment';

export class SmtpMessage extends AbstractMessageImpl {
  bcc: string[] = [];
  text: string = '';
  messageAttachments: MessageAttachment[] = [];
  mailHeader: MailHeader[] = [];
}
