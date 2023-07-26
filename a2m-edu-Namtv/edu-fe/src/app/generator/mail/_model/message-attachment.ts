export class MessageAttachment {
    contentType?: string = ""
    size?: number
    name?: string =  ""

    constructor(contentType?: string, size?: number, name?: string){
        this.contentType = contentType;
        this.size = size;
        this.name = name;
    }
}
