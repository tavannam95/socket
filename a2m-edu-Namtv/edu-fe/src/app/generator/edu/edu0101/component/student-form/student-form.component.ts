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
import { EamStudentInfo } from 'src/app/model/gen/eam-student-info';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu020101Service } from 'src/app/services/edu/edu020101.service';
import { Sys0103Service } from 'src/app/services/sys/sys0103.service';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { ConfirmationService } from 'primeng/api';



@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent implements OnInit {

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
  pagingRequest: any = {};
  pagingReq: any = {};
  listClass!: any[];
  listCourse!: any[];
  studentId:any = {};
  listClassChecked !: any[];
  listCourseChecked !: any[];
  listUser!: any[];
  username: any;
  typicalFlag = false ;

  constructor(
    private edu0102Service : Edu0102Service,
    private edu020101Service : Edu020101Service,
    private edu0201Service : Edu0201Service,
    private edu0101Service: Edu0101Service,
    private toastrService: ToastrService,
    private tccoStdService: TccoStdService,
    public dialogRef: MatDialogRef<StudentFormComponent>,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private langUtil: LangUtil,
    public dialog: MatDialog,
    private confirmationService: ConfirmationService,

    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    this.pagingRequest.searchText='';
    this.listClassChecked = [];

    this.getListClass();

    // this.getListCourse();

    if (this.data.tsstUser.userUid) {

      this.studentId =  this.data.tsstUser.eamStudentInfo.studentInfoId;

      this.edu020101Service.getClassByStudentId(this.studentId).subscribe((response) => {
        this.listClassChecked = response.list;
      });

      this.edu020101Service.getCourseByStudentId(this.studentId).subscribe((response) => {
        this.listCourseChecked = response.list;
      });

      this.tsstUser = this.data.tsstUser;
      if (!this.tsstUser.eamStudentInfo ){
        this.tsstUser.eamStudentInfo = new EamStudentInfo();


      }else{
        var date = this.tsstUser.eamStudentInfo.dob? new Date(this.tsstUser.eamStudentInfo.dob): null;
        this.tsstUser.eamStudentInfo.dob = date;
        this.typicalFlag = this.tsstUser.eamStudentInfo.typicalFlag;
      }
      this.disable = true;

    } else {
      this.tsstUser = new TsstUser();
      this.tsstUser.eamStudentInfo = new EamStudentInfo();
      this.disable = false;
      this.tsstUser.eamStudentInfo.emailVerifyKey = this.makeToken(30);
      this.tsstUser.eamStudentInfo.twoFAKey = this.makeToken(30);
      this.tsstUser.eamStudentInfo.gender = true;
      this.tsstUser.eamStudentInfo.deleteFlag = false;
    }
    this.getPosition();
    //this.getTsstUserList();
  }

  getListClass(){
    this.pagingRequest.getAll = true;
    this.pagingRequest.searchStatus = true;

    this.edu0201Service.search(this.pagingRequest).subscribe( (response) =>  {
      this.listClass =  response.list
   });
  }

  // getListCourse(){
  //   this.edu0102Service.search(this.pagingRequest).subscribe( (response) =>  {

  //     this.listCourse =  response.list
  //  });
  // }

  getPosition() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.POSITION_COMM_CD)
      .subscribe((response) => {
        this.position = response;
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  onSave(invalid: any) {
    let strings = ''
    this.listClassChecked.forEach((element: any) => {
      strings += element.key + ','
    });

    if(this.listClassChecked.length==0 ){
      this.popupValidQuestion(this.trans.instant('edu.class.required'));
      return
    }
    this.tsstUser.listClassChecked = strings;

    // if( !this.listCourseChecked ||  this.listCourseChecked.length==0){
    // if( !this.listCourseChecked){
    //   this.popupValidQuestion(this.trans.instant('edu.course.required'));
    //   return
    // }
    // strings = '';
    // this.listCourseChecked.forEach((element: any) => {
    //   strings += element.key + ','
    // });


    // this.tsstUser.listCourseChecked = strings;

    this.username = this.tsstUser.userId;
    // this.getTsstUserList(this.username);
    if (invalid) {
      this.submitted = true;
      return;
    } else if (!this.tsstUser.userUid) {
      let tmpDate = this.tsstUser.eamStudentInfo.dob;
      this.tsstUser.eamStudentInfo.typicalFlag = this.typicalFlag
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
        this.tsstUser.eamStudentInfo.dob = this.datepipe.transform(this.tsstUser.eamStudentInfo.dob, 'yyyy-MM-dd');
        this.tsstUser.userType = "STU";
        this.edu0101Service.save(this.tsstUser).subscribe({
          next: (response) => {
            if(!response){
              this.toastrService.error(this.trans.instant('message.error.saveUserFailed'));
              this.tsstUser.eamStudentInfo.dob = tmpDate;
            }else{
              this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
              this.save.emit(response);
              this.dialogRef.close(this.tsstUser);
            }
            dialogSpinner.close()
          },
          error: () => {
            this.toastrService.error(this.trans.instant('message.error.saveFailed'));
            this.tsstUser.eamStudentInfo.dob = tmpDate;
            dialogSpinner.close()
          },
        });

    } else {
      this.tsstUser.eamStudentInfo.typicalFlag = this.typicalFlag
      if(!this.typicalFlag){
        this.tsstUser.eamStudentInfo.idea = '';
      }

      this.tsstUser.isChangePassword = this.changePassword;

      // var dateOfBirth = new Date(this.tsstUser.eamStudentInfo.dob);
      this.tsstUser.eamStudentInfo.dob = this.datepipe.transform(this.tsstUser.eamStudentInfo.dob, 'yyyy-MM-dd');

      this.edu0101Service.update(this.tsstUser).subscribe({
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

  saveAll(invalid: any){

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

  changeCheckBox(event : any){
    this.typicalFlag = event.checked;
  }

  popupValidQuestion(mes: any) {
    this.confirmationService.confirm({
      header: mes,
      message:""
      // this.trans.instant('course0104.quiz.quizDetail.contentValidQuiz')
      ,
      acceptLabel:
      this.trans.instant('message.confirm.title')
      ,
      acceptVisible: true,
      accept: () => {
      },
      reject: () => {

      },
    });
  }

}

