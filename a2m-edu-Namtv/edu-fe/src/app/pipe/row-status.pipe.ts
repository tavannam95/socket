import { Pipe, PipeTransform } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { RowStatus } from '../constants/rowStatus.constant';

@Pipe({
    name: 'rowStatus'
})
export class RowStatusPipe implements PipeTransform {

    constructor(
        private trans: TranslateService) {
    }

    transform(statusCode: string): String {
        let statusName = "";
        switch(statusCode) {
            case RowStatus.CREATE:
                statusName = this.trans.instant('common.status.create');
                break;
            case RowStatus.UPDATE:
                statusName = this.trans.instant('common.status.updated');
                break;
            case RowStatus.DELETE:
                statusName = this.trans.instant('common.status.deleted');
                break;
        }
        return statusName;
    }
}
