export class Menu {
    CREATED_BY: string;
    UP_MENU_ID: string;
    CREATED_DATE: string;
    UPDATED_BY: string;
    UPDATED_DATE: string;
    MENU_ID: string;
    MENU_NM_EN: string;
    MENU_NM_VI: string;
    LEV: number;
    ORD_NO: number;
    MENU_NM: string;
    URL: string;

    constructor(data?:any) {
        this.CREATED_BY = data.CREATED_BY;
        this.UP_MENU_ID = data.UP_MENU_ID
        this.CREATED_DATE = data.CREATED_DATE
        this.UPDATED_BY = data.UPDATED_BY
        this.UPDATED_DATE = data.UPDATED_DATE
        this.MENU_ID = data.MENU_ID
        this.MENU_NM_EN = data.MENU_NM_EN
        this.MENU_NM_VI = data.MENU_NM_VI
        this.LEV = data.LEV
        this.ORD_NO = data.ORD_NO
        this.MENU_NM = data.MENU_NM
        this.URL = data.URL
    }
}
