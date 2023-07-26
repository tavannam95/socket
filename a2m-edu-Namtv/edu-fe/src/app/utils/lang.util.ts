import { TranslateService } from '@ngx-translate/core';
import { DatePipe } from '@angular/common'
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})

export class LangUtil {

  constructor(private translate: TranslateService, private datepipe: DatePipe){}

  displayDate(date: Date) {
    if(this.translate.currentLang != 'kr'){
      return  this.datepipe.transform(date, 'dd/MM/yyyy');
    }else{
      return  this.datepipe.transform(date, 'yyyy/MM/dd');
    }
  }

  displayDateFormat() {
    if(this.translate.currentLang != 'kr'){
      return 'dd/MM/yyyy';
    }else{
      return 'yyyy/MM/dd';
    }
  }

  displayDateFormatNgPrime() {
    if(this.translate.currentLang != 'kr'){
      return 'dd/mm/yy';
    }else{
      return 'yy/mm/dd';
    }
  }

}
