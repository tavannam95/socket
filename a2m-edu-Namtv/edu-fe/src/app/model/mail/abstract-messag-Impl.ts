export class AbstractMessageImpl {
    from: string = "";
    subject: string = "";
    replyTo: string = "";
    to: string[] = [];
    cc: string[] = [];
    index?: number;
    hasAttachment?: boolean;
}
