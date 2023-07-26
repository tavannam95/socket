export class AbstractMessageImpl {
    from: string = ""
    subject: string = ""
    repplyto: string = ""
    to: string[] = []
    cc: string[] = []
    hasAttachment?: boolean;
}
