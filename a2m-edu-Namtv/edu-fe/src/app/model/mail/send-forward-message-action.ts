import { Pop3Folder } from "./pop3-folder";
import { SendMessageAction } from "./send-message-action";

export class SendForwardMessageAction extends SendMessageAction {
    uid?: number;
    folder?: Pop3Folder;
    inReplyTo?: string;
    references?: string;
}
