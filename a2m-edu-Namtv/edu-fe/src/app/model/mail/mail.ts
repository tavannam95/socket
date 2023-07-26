export class MailMessage {
    USERNAME?: string;
    FOLDER_NM?: string;
    SEARCH?: string;
    REAL_COUNT?: number;
    START?: number;
    OFFSET?: number;
    UIDS: any[] = [];
    TYPE?:any;
    FOLDER_TO?:any;
}
