import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';

import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  Inject,
  Input,
} from '@angular/core';
import { TsstUser } from 'src/app/model/sys/tsst-user';

import { DatePipe, formatDate } from '@angular/common';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TranslateService } from '@ngx-translate/core';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { LangUtil } from 'src/app/utils/lang.util';
import { EamTeacherInfo } from 'src/app/model/gen/eam-teacher-info';
import { Edu0105Service } from 'src/app/services/edu/edu0105.service';
import { Sys0103Service } from 'src/app/services/sys/sys0103.service';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';

@Component({
  selector: 'app-teacher-form',
  templateUrl: './teacher-form.component.html',
  styleUrls: ['./teacher-form.component.css']
})
export class TeacherFormComponent implements OnInit {

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
  pagingReq: any = {};
  listUser!: any[];
  username: any;



  constructor(
    private sys0103Service: Sys0103Service,
    private edu0105Service: Edu0105Service,
    private toastrService: ToastrService,
    private tccoStdService: TccoStdService,
    public dialogRef: MatDialogRef<TeacherFormComponent>,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private langUtil: LangUtil,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialog,
  ) {}

  ngOnInit(): void {
    if (this.data.tsstUser.userUid) {

      this.tsstUser = this.data.tsstUser;
      if (!this.tsstUser.eamTeacherInfo ){
        this.tsstUser.eamTeacherInfo = new EamTeacherInfo();
      }else{
        var date = this.tsstUser.eamTeacherInfo.dob? new Date(this.tsstUser.eamTeacherInfo.dob): null;
        this.tsstUser.eamTeacherInfo.dob = date;
      }
      this.disable = true;

    } else {
      this.tsstUser = new TsstUser();
      this.tsstUser.eamTeacherInfo = new EamTeacherInfo();
      this.disable = false;
      this.tsstUser.eamTeacherInfo.emailVerifyKey = this.makeToken(30);
      this.tsstUser.eamTeacherInfo.twoFAKey = this.makeToken(30);
      this.tsstUser.eamTeacherInfo.gender = true;
      this.tsstUser.eamTeacherInfo.deleteFlag = false;
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

  getTsstUserList(username:any){
    this.pagingReq.searchText = '';
    this.sys0103Service.getTsstUserList(this.pagingReq).subscribe((response) => {
      this.listUser = response.list;
      for(let i = 0; i < this.listUser.length; i++){
        const userNameTsst = this.listUser[i].userId;
        if(userNameTsst == username){
          alert('Existing username. Please choose again!');
        }
      }
    });
  }

  onSave(invalid: any) {
    this.getTsstUserList(this.username);
    if (invalid) {
      this.submitted = true;
      return;
    } else if (!this.tsstUser.userUid) {
      let tmpDate =  this.tsstUser.eamTeacherInfo.dob;
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      this.tsstUser.eamTeacherInfo.dob = this.datepipe.transform(this.tsstUser.eamTeacherInfo.dob, 'yyyy-MM-dd');

      this.tsstUser.userType = "TEA";
      this.edu0105Service.save(this.tsstUser).subscribe({
        next: (response) => {
          if(!response){
            this.toastrService.error(this.trans.instant('message.error.saveUserFailed'));
            this.tsstUser.eamTeacherInfo.dob = tmpDate;
          }else{
            this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
            this.save.emit(response);
            this.dialogRef.close(this.tsstUser);
          }
          dialogSpinner.close()
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
          this.tsstUser.eamTeacherInfo.dob = tmpDate;
          dialogSpinner.close()
        },
      });
    } else {
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      this.tsstUser.isChangePassword = this.changePassword;
      // var dateOfBirth = new Date(this.tsstUser.eamTeacherInfo.dob);
      this.tsstUser.eamTeacherInfo.dob = this.datepipe.transform(this.tsstUser.eamTeacherInfo.dob, 'yyyy-MM-dd');

      this.edu0105Service.update(this.tsstUser).subscribe({
        next: (response) => {
          this.toastrService.success('Success', ' Updated !');
          this.save.emit(response);
          this.dialogRef.close(this.tsstUser);
          dialogSpinner.close()
        },
        error: () => {
          this.toastrService.error('Failed', 'Update Failed !');
          dialogSpinner.close()
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

