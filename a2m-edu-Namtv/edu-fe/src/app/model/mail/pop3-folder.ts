export class Pop3Folder{
    children: Pop3Folder[] = [];
    name: string = "";
    fullName: string = "";
    delimiter: string = "";
    messageCount?: number;
    unseenMessageCount?: number;
    subcribed: boolean = false;
    hasChilden: boolean = false;
}
