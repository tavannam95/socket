export class ImapFolder {
    children: ImapFolder[] = []
    name: string = ""
    fullName: string = ""
    delimiter: string = ""
    messageCount?: number;
    unseenMessageCount?: number;
    subcribed: boolean = false;
    hasChildren: boolean = false;

}
