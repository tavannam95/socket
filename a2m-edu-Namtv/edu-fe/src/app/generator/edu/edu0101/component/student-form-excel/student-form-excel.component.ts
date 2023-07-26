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
import { FileUploadService } from 'src/app/services/file/file-upload.service';



@Component({
  selector: 'app-student-form-excel',
  templateUrl: './student-form-excel.component.html',
  styleUrls: ['./student-form-excel.component.css']
})
export class StudentFormExcelComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  hiddenClass: any = true;
  disable = true;
  changePassword = false;
  tsstUser:any = {};
  student:any = {};
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
  ClassChecked : any;
  courseChecked : any;
  listUser!: any[];
  username: any;
  fileExcelNm:any;
  listTsstUser:any = [];
  listItemForExcel:any = [];
  typicalFlag = false ;
  listChecked:  any [] = [];
  excel= true;
  tsstUserList:any[] = [];
  pdfFilePath: any = {};
  constructor(
    private edu0102Service : Edu0102Service,
    private edu020101Service : Edu020101Service,
    private edu0201Service : Edu0201Service,
    private edu0101Service: Edu0101Service,
    private toastrService: ToastrService,
    private tccoStdService: TccoStdService,
    public dialogRef: MatDialogRef<StudentFormExcelComponent>,
    private trans: TranslateService,
    public datepipe: DatePipe,
    private langUtil: LangUtil,
    public dialog: MatDialog,
    private confirmationService: ConfirmationService,
    private fileUploadService: FileUploadService,

    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    // this.getListClass();
    this.getListCourse();
    this.excel = this.data.excel;
    this.tsstUserList = this.data.listTsstUser;
    if(this.excel == true){
      this.courseChecked = this.tsstUserList[0].courseModel;
      this.getTsstUserCandidate();
      if(this.courseChecked.key != null){
        this.getListClass(this.courseChecked.key);
        this.hiddenClass = false;
      }
    }

  }

  getListClass(courseId:any){
    this.pagingRequest.courseId = courseId;
    this.pagingRequest.getAll = true;
    this.pagingRequest.searchStatus = true;
    this.edu0201Service.search(this.pagingRequest).subscribe( (response) =>  {
      this.listClass =  response.list
   });
  }

  getListCourse(){
    this.pagingReq.getAll = true;
    this.pagingReq.searchStatus = true;
    this.edu0102Service.search(this.pagingReq).subscribe( (response) =>  {
      this.listCourse =  response.list
   });
  }

  changCourse(event : any){
    const courseId = event.value.key;
    this.getListClass(courseId);
    this.hiddenClass = false;
  }

  setTsstUser(){
    let strings = ''
    strings = this.ClassChecked.key + ','
    if(this.ClassChecked.key = null ){
      this.popupValidQuestion(this.trans.instant('edu.class.required'));
      return
    }
    for(let i = 0; i < this.listTsstUser.length; i++){
      this.listTsstUser[i].listClassChecked = strings;

      this.username = this.listTsstUser[i].userId;
      this.listTsstUser[i].eamStudentInfo.typicalFlag = this.typicalFlag
      this.listTsstUser[i].eamStudentInfo.dob = this.datepipe.transform(this.listTsstUser[i].eamStudentInfo.dob, 'yyyy-MM-dd');
      this.listTsstUser[i].userType = "STU";
    }
  }
  getTsstUserCandidate(){
    this.courseChecked = this.tsstUserList[0].courseModel;
    for(let i = 0; i < this.tsstUserList.length; i++){
      this.student = new TsstUser();
      this.student.eamStudentInfo.fullName = this.tsstUserList[i].candidateName;
      this.student.eamStudentInfo.email = this.tsstUserList[i].candidateEmail;
      this.student.eamStudentInfo.cellPhone = this.tsstUserList[i].candidatePhone;
      this.student.eamStudentInfo.dob = null;
      this.student.eamStudentInfo.emailVerifyKey = this.makeToken(30);
      this.student.eamStudentInfo.twoFAKey = this.makeToken(30);
      this.student.eamStudentInfo.gender = true;
      this.student.eamStudentInfo.deleteFlag = false;
      this.student.password = "123456789";
      this.student.confirmPassword = "123456789";
      // let listClassModel = this.tsstUserList[i].courseModel.listClassModel;
      // for(let j = 0; j < listClassModel.length;j++){
      //   let strings =''
      //   strings += listClassModel[j].key + ',';
      //   this.student.listClassChecked = strings;
      // }
      // this.listTsstUser.push(this.student);
      this.listItemForExcel.push(this.student.eamStudentInfo);
    }
    if(this.listItemForExcel.length > 0){
      this.disable = false;
    }

  }
  getListTsstUser(){
    for(let i = 0; i < this.listChecked.length; i++){
      this.tsstUser = new TsstUser();
      this.tsstUser.eamStudentInfo = this.listChecked[i];
      this.tsstUser.eamStudentInfo.emailVerifyKey = this.makeToken(30);
      this.tsstUser.eamStudentInfo.twoFAKey = this.makeToken(30);
      this.tsstUser.eamStudentInfo.gender = true;
      this.tsstUser.eamStudentInfo.deleteFlag = false;
      this.tsstUser.password = "123456789";
      this.tsstUser.confirmPassword = "123456789";
      this.listTsstUser.push(this.tsstUser);
    }
  }

  onSaveAll() {
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.edu0101Service.saveForExcel(this.listTsstUser).subscribe({
      next: (response) => {
        if(!response){
          this.toastrService.error(this.trans.instant('message.error.saveUserFailed'));
        }else{
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.save.emit(response);
          this.exportExcel(response)
          this.dialogRef.close(this.tsstUser);
        }
        dialogSpinner.close()
      },
      error: () => {
        this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        dialogSpinner.close()
      },
    });
  }

  onSave(invalid: any) {
    if(this.excel){
      invalid = false;
    }
    if (invalid) {
      this.submitted = true;
      return;
    } else{
      this.getListTsstUser();
      this.setTsstUser();
      if(this.listTsstUser.length != 0){
        this.confirmationService.confirm({
          header: this.trans.instant('message.confirm.save'),
          acceptLabel: this.trans.instant('button.confirm.title'),
          rejectLabel: this.trans.instant('button.cancel.title'),
          accept: () => {
            this.onSaveAll();
          },
          reject: () => {
            this.onCancel();
          },
        });
      }else{
        alert(this.trans.instant('message.requiredSelectUser'));
      }
    }
  }

  importExcel(event: any){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    const fileExcel = event.target.files[0];
    this.edu0101Service.importExcel(fileExcel).subscribe({
      next: (response) =>{
        this.listItemForExcel = response[CommonConstant.LIST_KEY];
        // this.getListTsstUser();
        this.toastrService.success('Success', '  Imported !');
        this.fileExcelNm = fileExcel.name;
        if(this.listItemForExcel.length > 0){
          this.disable = false;
        }
        dialogSpinner.close();
      },
      error: ()=>{
        this.toastrService.error('Failed', 'Imported  Failed !');
      }
    })
  }

  exportExcel(tsstUser: any){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.edu0101Service
      .exportExcel(tsstUser)
      .toPromise()
      .then((response) => {
        const myArray = response.detail.split('!#@');
        this.pdfFilePath.fleNm = "Danh sách sinh vien-" + myArray[1];
        this.pdfFilePath.fleTp = '.xlsx';
        this.pdfFilePath.flePath = myArray[0] + this.pdfFilePath.fleTp;
        this.fileUploadService.download(this.pdfFilePath, true);
        dialogSpinner.close();
      });
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

  changeFieldList(list:any,checked:boolean){
    list.forEach((element:any) => {
      element.checked = checked;
    });
  }

  checkStudent(student : any){
    return student.checked?true: false ;
    }

  handleListCheked(student : any){
    if(this.listChecked.includes(student)){
      var index = this.listChecked.indexOf(student);
      if (index > -1) {
        student.checked = false ;
        this.listChecked.splice(index, 1);
      }
    }else{
      student.checked = true ;
      this.listChecked.push(student);
    }
  }

  handleMultiSelect(event : any){
    let isChecked =  event.currentTarget.checked;
    if(isChecked){
      let tempList =  this.listItemForExcel.filter((item: any) =>  !this.listChecked.includes(item))
      this.listChecked.push(...tempList);
      this.changeFieldList(this.listChecked,true);
      // console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listItemForExcel.includes(item));
      this.changeFieldList(this.listChecked,true);
    }
  }

}

