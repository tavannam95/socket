import { PaginationConfig } from "../config/pagination.config";

export class CommonUtil {
  public static isEmpty(value: string): boolean {
    if (value != null && value != undefined && value.trim().length !== 0) {
      return false;
    }
    return true;
  }

  public static addIndexForList(data: any[], page?: any, total?: any) {
    let i = 1;
    data.forEach((ele) => {
      ele.index = i;
      i++;
    });
    return data;
  }

  public static addIndexForListReverse(data: any[], page: any, total: any) {
    data.forEach((ele) => {
      ele.index = total - page * PaginationConfig.PAGE_SIZE;
      total--;
    });
    return data;
  }

  public static determineFileType(listFile: any) {
    listFile.forEach((file: any) => {
      let type: any;
      let fileExt = file.name
        .slice(file.name.lastIndexOf('.') + 1)
        .toLowerCase();
      if (fileExt == 'docx' || fileExt == 'doc') {
        type = 'word';
      } else if (fileExt == 'pptx') {
        type = 'ppt';
      } else if (fileExt == 'pdf') {
        type = 'pdf';
      } else if (fileExt == 'xlsx' || fileExt == 'xls' || fileExt == 'csv') {
        type = 'excel';
      } else if (
        fileExt == 'zip' ||
        fileExt == 'rar' ||
        fileExt == '7z' ||
        fileExt == 'jar'
      ) {
        type = 'zip';
      } else {
        type = 'media';
      }
      file.type = type;
    });
    return listFile;
  }
  public static displayDate(value: Date) {}
}
