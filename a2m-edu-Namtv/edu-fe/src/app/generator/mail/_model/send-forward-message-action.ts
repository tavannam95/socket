import { ImapFolder } from "./imap-folder";
import { SendMessageAction } from "./send-message-action";

export class SendForwardMessageAction extends SendMessageAction {
    uid?: number;
    folder?: ImapFolder;
    inReplyTo?: string;
    references?: string;
}
