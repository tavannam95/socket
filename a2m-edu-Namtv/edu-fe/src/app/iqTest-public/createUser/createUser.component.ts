import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Inject, OnInit, Output, SimpleChanges } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TsstUserInfo } from 'src/app/model/gen/tsst-user-info';
import { IqTestPublicService } from 'src/app/services/quesIq/iqTestPublic.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { LangUtil } from 'src/app/utils/lang.util';

@Component({
  selector: 'app-createUser',
  templateUrl: './createUser.component.html',
  styleUrls: ['./createUser.component.css']
})
export class CreateUserComponent implements OnInit {
  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  disable = false;
  changePassword = false;
  tsstUserInfo:any = {};
  position: any[] = [];
  submitted: any = false;



  constructor(
    private iqTestPublicService: IqTestPublicService,
    private toastrService: ToastrService,
    private tccoStdService: TccoStdService,
    public dialogRef: MatDialogRef<CreateUserComponent>,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private langUtil: LangUtil,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
      this.tsstUserInfo = new TsstUserInfo();
      this.disable = false;
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    }
    if (!this.tsstUserInfo.userUid) {

      this.iqTestPublicService.saveNonUser(this.tsstUserInfo).subscribe({
        next: (response) => {
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.tsstUserInfo.key = response[CommonConstant.KEY];
          this.save.emit(response);
          this.dialogRef.close(this.tsstUserInfo);
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    }
  }

}
