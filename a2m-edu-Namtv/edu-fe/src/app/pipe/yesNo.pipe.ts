import { Pipe, PipeTransform } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CommonConstant } from '../constants/common.constant';

@Pipe({
    name: 'yesNo'
})
export class YesNoPipe implements PipeTransform {

    constructor(
        private trans: TranslateService) {
    }

    transform(yesNo: string): any {
        let yesNoLabel = "";
        switch(yesNo) {
            case CommonConstant.YES:
                yesNoLabel = 'common.status.yes';
                break;
            case CommonConstant.NO:
                yesNoLabel = 'common.status.no';
                break;
        }
        return yesNoLabel;
    }
}