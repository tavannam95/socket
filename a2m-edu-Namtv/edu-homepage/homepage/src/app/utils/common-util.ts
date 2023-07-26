import { PaginationConfig } from "../config/pagination.config";
import { Buffer } from 'buffer';

export class CommonUtil {
  public static isEmpty(value: string): boolean {
    if (value != null && value != undefined && value.trim().length !== 0) {
      return false;
    }
    return true;
  }

  // public static addIndexForList(data: any[]) {
  //   let i = data.length;
  //   data.forEach((ele) => {
  //     ele.index = i;
  //     i--;
  //   });
  //   return data;
  // }

  public static addIndexForListReverse(data: any[], page: any, total: any) {
    data.forEach((ele) => {
      ele.index = total - page * PaginationConfig.PAGE_SIZE;
      total--;
    });
    return data;
  }

  public static displayDate(value: Date) {}

  public static endCodeBase64(text: string){
    return Buffer.from(text, 'utf8').toString('base64');
  }

  public static deCodeBase64(encoded: string){
    return Buffer.from(encoded, 'base64').toString('utf8');
  }
}
