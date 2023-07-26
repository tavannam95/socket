import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { CommonConstant } from 'src/app/constants/common.constant';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { EamStudentInfo } from 'src/app/model/gen/eam-student-info';
import { Equiqment } from 'src/app/model/sys/equipment';
import { TsstUser } from 'src/app/model/sys/tsst-user';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { Edu0104Service } from 'src/app/services/edu/edu0104.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { LangUtil } from 'src/app/utils/lang.util';
import { StudentFormComponent } from '../../../edu0101/component/student-form/student-form.component';

@Component({
  selector: 'app-equipment-form',
  templateUrl: './equipment-form.component.html',
  styleUrls: ['./equipment-form.component.css']
})
export class EquipmentFormComponent implements OnInit {

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


  equiqment : any={};
  equiqmentId : any;
  equipTypes: any[] = [];


  constructor(
    private commonService: CommonService,
    private edu0101Service: Edu0101Service,
    private toastrService: ToastrService,
    private tccoStdService: TccoStdService,
    public dialogRef: MatDialogRef<StudentFormComponent>,
    public dialog: MatDialog,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private langUtil: LangUtil,
    private edu0104Service : Edu0104Service,
    @Inject(MAT_DIALOG_DATA) public data: any

  ) {}

  ngOnInit(): void {
    this.getEquipType();
    this.equiqmentId = this.data.equiqment.key;
    if (this.equiqmentId) {
      this.equiqment = this.data.equiqment;
    } else {
      this.equiqment = new Equiqment();
      this.equiqment.equiqmentStatus = false;
    }
  }

  
  getEquipType() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.EQUIPMENT_TYPE_COMM_CD)
      .subscribe((response) => {
        this.equipTypes = response;
        this.commonService.selectLangComcode(this.equipTypes);
      });
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
    } else if (!this.equiqment.equipmentId) {
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      this.edu0104Service
      .save(this.equiqment).subscribe({
        next: (response) => {
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.save.emit(response);
          this.dialogRef.close(this.equiqment);
          dialogSpinner.close()
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
          dialogSpinner.close()
        },
      });
    } else {
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      this.tsstUser.isChangePassword = this.changePassword;
      // var dateOfBirth = new Date(this.tsstUser.eamStudentInfo.dob);
      this.tsstUser.eamStudentInfo.dob = this.datepipe.transform(this.tsstUser.eamStudentInfo.dob, 'yyyy-MM-dd');

      this.edu0101Service.update(this.tsstUser).subscribe({
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
