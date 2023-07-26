import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Sys0103Service } from 'src/app/services/sys/sys0103.service';
import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  Inject,
  Input,
} from '@angular/core';
import { TsstUser } from 'src/app/model/sys/tsst-user';
import { TsstUserInfo } from 'src/app/model/gen/tsst-user-info';
import { DatePipe, formatDate } from '@angular/common';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TranslateService } from '@ngx-translate/core';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { LangUtil } from 'src/app/utils/lang.util';

@Component({
  selector: 'app-target-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
})
export class UserFormComponent implements OnInit {
  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  disable = false;
  changePassword = false;
  tsstUser:any = {};
  position: any[] = [];
  emailPattern = PatternValidate.EMAIL_PATTERN;
  phonePattern = PatternValidate.PHONE_PATTERN;
  phonePatternBasic = PatternValidate.PHONE_PATTERN_BASIC;
  submitted: any = false;



  constructor(
    private sys0103Service: Sys0103Service,
    private toastrService: ToastrService,
    private tccoStdService: TccoStdService,
    public dialogRef: MatDialogRef<UserFormComponent>,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private langUtil: LangUtil,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data.tsstUser.userUid) {

      this.tsstUser = this.data.tsstUser;
      if (!this.tsstUser.tsstUserInfo ){
        this.tsstUser.tsstUserInfo = new TsstUserInfo();
      }else{
        var date = this.tsstUser.tsstUserInfo.dob? new Date(this.tsstUser.tsstUserInfo.dob): null;
        this.tsstUser.tsstUserInfo.dob = date;
      }
      this.disable = true;

    } else {
      this.tsstUser = new TsstUser();
      this.tsstUser.tsstUserInfo = new TsstUserInfo();
      this.disable = false;
      this.tsstUser.tsstUserInfo.emailVerifyKey = this.makeToken(30);
      this.tsstUser.tsstUserInfo.gender = true ;
      this.tsstUser.tsstUserInfo.twoFAKey = this.makeToken(30);
    }
    this.getPosition();
  }

  getPosition() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.POSITION_COMM_CD)
      .subscribe((response) => {
        this.position = response;
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    } else if (!this.tsstUser.userUid) {

      // var dateOfBirth = new Date(this.tsstUser.tsstUserInfo.dob);
      this.tsstUser.tsstUserInfo.dob = this.datepipe.transform(this.tsstUser.tsstUserInfo.dob, 'yyyy-MM-dd');

      this.tsstUser.userType = "EMP";
      this.sys0103Service.save(this.tsstUser).subscribe({
        next: (response) => {
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.save.emit(response);
          this.dialogRef.close(this.tsstUser);
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    } else {

      this.tsstUser.isChangePassword = this.changePassword;
      // var dateOfBirth = new Date(this.tsstUser.tsstUserInfo.dob);
      this.tsstUser.tsstUserInfo.dob = this.datepipe.transform(this.tsstUser.tsstUserInfo.dob, 'yyyy-MM-dd');

      this.sys0103Service.update(this.tsstUser).subscribe({
        next: (response) => {
          this.toastrService.success('Success', ' Updated !');
          this.save.emit(response);
          this.dialogRef.close(this.tsstUser);
        },
        error: () => {
          this.toastrService.error('Failed', 'Update Failed !');
        },
      });
    }
  }

  onCancel() {
    if (this.tsstUser.userUid) {
      this.dialogRef.close(this.tsstUser);
    } else {
      this.dialogRef.close();
    }
  }

  makeToken(length: number) {
    var result = '';
    var val = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    var charactersLength = val.length;
    for (var i = 0; i < length; i++) {
      result += val.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result.toLocaleUpperCase();
  }
}
