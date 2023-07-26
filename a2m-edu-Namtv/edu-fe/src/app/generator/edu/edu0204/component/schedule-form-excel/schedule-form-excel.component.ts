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
import { CommonConstant } from 'src/app/constants/common.constant';
import { TranslateService } from '@ngx-translate/core';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { ConfirmationService } from 'primeng/api';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { Edu0204Service } from 'src/app/services/edu/edu0204.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonService } from 'src/app/services/common/common.service';



@Component({
  selector: 'app-schedule-form-excel',
  templateUrl: './schedule-form-excel.component.html',
  styleUrls: ['./schedule-form-excel.component.css']
})
export class ScheduleFormExcelComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  excel= true;
  disable = true;
  disableDel = false;
  scheduleId: any;
  submitted: any = false;
  dateForCalendar: any;
  schedule : any={};
  typeView : any;
  checkView : any = false;
  pagingRequestSchedule: any = {};
  pagingRequest: any = {};
  pagingRequestRoom: any = {};
  listClass!: any[];
  listSubject:any;
  listRoom:any;
  isDuplicate:any = false;
  listChecked : any[] = [];
  listItemForExcel:any = [];
  fileExcelNm:any;
  pdfFilePath: any = {};
  classChecked !: any;
  subjectChecked !: any;
  roomChecked !: any;
  hiddenSubject: any = true;
  hourStart: any;
  minStart: any;
  hourEnd: any;
  minEnd: any;
  usageDate: any;
  startTime:any = String;
  endTime:any = String;
  userInfo : any;
  minimumDate:any;
  constructor(
    private edu0204Service: Edu0204Service,
    public dialogRef: MatDialogRef<ScheduleFormExcelComponent>,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private router: Router,
    public commonService: CommonService,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    public dialog: MatDialog,
    private toastrService: ToastrService,
    private fileUploadService: FileUploadService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    this.getTsstUser();
    this.getListClass();
    this.getRooms();
    this.route.queryParams.subscribe(async (params) => {
      this.scheduleId = params['scheduleId'];
      this.dateForCalendar = params['date'];
    });
    if (this.scheduleId) {
      this.typeView = 'VIEW';
      this.hiddenSubject = false;
      this.initData(this.scheduleId);
    } else {
      this.typeView = 'ADD';
      this.schedule = new this.schedule();
      this.schedule.status = false;
      this.schedule.displayStatus = false;
      this.schedule.isComingSoon = false;
      this.disable = true;
      this.init();
    }
  }

  getTsstUser(){
    this.commonService.getUserInfo().subscribe(
      (response) =>{
        this.userInfo = response;
        if(this.userInfo.userType != 'STU'){
          this.disable = false;
        }else{
          this.disable = true;
        }
      }
    );
  }

  init() {
    if(this.dateForCalendar != undefined || this.dateForCalendar != null){
    this.schedule.usageDate = new Date(this.dateForCalendar);
    }else{
      this.schedule.usageDate = new Date();
    }

    this.minimumDate = new Date();
    this.schedule.startDate = new Date(this.schedule.usageDate);
    // this.schedule.startDate.setHours(8);
    // this.schedule.startDate.setMinutes(0);
    // this.schedule.startDate.setSeconds(0);
    this.hourStart = this.schedule.usageDate.getHours();
    this.minStart = this.schedule.usageDate.getMinutes();


    this.schedule.endDate = new Date(this.schedule.usageDate);
    const minuteEndDate = this.schedule.usageDate.getMinutes() + 30;
    if(minuteEndDate >= 60){
    this.schedule.endDate.setHours(this.schedule.usageDate.getHours()+1);
    this.schedule.endDate.setMinutes(minuteEndDate-60);
    }
    this.schedule.endDate.setMinutes(minuteEndDate);
    // this.schedule.endDate.setSeconds(0);
    this.hourEnd = this.schedule.endDate.getHours();
    this.minEnd = this.schedule.endDate.getMinutes();;
  }

  getRooms(){
    this.pagingRequestRoom.getAll = true;
    this.edu0204Service.getRoomList(this.pagingRequestRoom).subscribe( (response) =>  {
      this.listRoom =  response.list;
   });
  }

  getListClass(){
    this.pagingRequest.getAll = true;
    this.edu0204Service.getClassList(this.pagingRequest).subscribe( (response) =>  {
      this.listClass =  response.list;
   });
  }

  changeClass(event : any){
    const courseId = event.value.courseId;
    this.getListSubject(courseId);
    this.hiddenSubject = false;
  }

  getListSubject(courseId:any){
    this.edu0204Service.getListSubjectByCourseId(courseId).subscribe( (response) =>  {
      this.listSubject=  response.list;
   });
  }

  initData(param : any){
    this.edu0204Service.getScheduleById(param).subscribe((response) => {
      this.schedule = response[CommonConstant.DETAIL_KEY];

      this.roomChecked = this.schedule.roomModel;
      this.classChecked = this.schedule.classModel;
      this.subjectChecked = this.schedule.subjectModel;
      this.schedule.usageDate = new Date(this.schedule.usageDate);
      this.schedule.startDate = new Date(this.schedule.startDate);
      this.schedule.endDate = new Date(this.schedule.endDate);
      this.getListSubject(this.classChecked.courseId);
    });
  }

  checkForDuplicates(roomId:any) {
    this.pagingRequestSchedule.roomId = roomId;
    this.isDuplicate = false;
    this.edu0204Service.checkDuplicates(this.pagingRequestSchedule).subscribe((response) => {
      const listSchedule = response.list;
      for(let i = 0; i < listSchedule.length; i++){
        const scheduleForRoom = listSchedule[i];
        const startDate = new Date(scheduleForRoom.startDate);
        const endDate = new Date(scheduleForRoom.endDate);
        if(startDate.getTime() > this.schedule.endDate.getTime() || endDate.getTime() < this.schedule.startDate.getTime()){
          this.isDuplicate = false;
        }else {
          this.isDuplicate = true;
          this.startTime = startDate.getHours() + ':' + startDate.getMinutes();
          this.endTime = endDate.getHours() + ':' + endDate.getMinutes();

          break;
        }
      }
      if(this.isDuplicate){
        const alertDuplicate = 'Phòng "' + listSchedule[0].roomNm + '" đã có lớp học từ ' + this.startTime + '~' + this.endTime;
        alert(alertDuplicate);
      }else{
        this.setConfirmSave();
      }
    });
  }

  onEditorChange(event: any) {

  }

  onChangedDate() {

    this.schedule.startDate = new Date(this.schedule.usageDate);
    this.schedule.startDate.setHours(this.hourStart);
    this.schedule.startDate.setMinutes(this.minStart);
    this.schedule.startDate.setSeconds(0);

    this.schedule.endDate = new Date(this.schedule.usageDate);
    this.schedule.endDate.setHours(this.hourEnd);
    this.schedule.endDate.setMinutes(this.minEnd);
    this.schedule.endDate.setSeconds(0);
  }

  onSaveAll(invalid: any){
    if (invalid) {
      this.submitted = true;
      return;
    }
    this.schedule.roomId = this.roomChecked.key;
    this.schedule.classId = this.classChecked.key;
    this.schedule.subjectId = this.subjectChecked.key;
    this.schedule.isFinish = false;
    this.onChangedDate();
    const checkDate = new Date();
    if(checkDate.getTime() > this.schedule.startDate.getTime()){
      alert(this.trans.instant('message.alertCheckDate'))
    }else{
      if(this.schedule.endDate.getTime() <  this.schedule.startDate.getTime()){
        alert(this.trans.instant('message.alertCheckDateTime'));
      }else{
        this.checkForDuplicates(this.schedule.roomId);

      }
    }
  }

  setConfirmSave() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.save'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	    rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.saveAll();
      },
      reject: () => {
        // this.onCancel();
      },
    });
  }

  saveAll() {
    this.edu0204Service.save(this.schedule).subscribe({
      next: (response) => {
        if(!this.schedule.key){
          //this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.saved(response[CommonConstant.KEY]);
          this.handleAfterSaveSuccess(this.trans.instant('message.success.saveSuccess'));
        }else{
          //this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
          this.saved(response[CommonConstant.KEY]);
          this.handleAfterSaveSuccess(this.trans.instant('message.success.updateSuccess'));
        }
      },
      error: () => {
        if(!this.schedule.key){
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
          //this.handleAfterSaveSuccess(this.trans.instant('message.error.saveFailed'));
        }else{
          this.toastrService.error(this.trans.instant('message.success.updateFailed'));
          //this.handleAfterSaveSuccess(this.trans.instant('message.success.updateFailed'));
        }
      },
    });

  }

  saved(scheduleId: any) {
    this.router.navigate(['/edu/edu0204Form'], {
      queryParams: {
        scheduleId: scheduleId,
      },
    });
  }

  handleAfterSaveSuccess(mes: any) {
    this.confirmationService.confirm({
      header: mes,
      message: this.trans.instant('message.confirm.goListScreen'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	  rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.goList()
      },
      reject: () => {
      this.typeView = 'VIEW';
      },
    });
  }

  goList(){
    this.router.navigateByUrl('edu/edu0204');
  }

  onCancel() {
    // if (this.tsstUser.userUid) {
    //   this.dialogRef.close(this.tsstUser);
    // } else {
    //   this.dialogRef.close();
    // }
    this.router.navigateByUrl('edu/edu0204');
  }

  showUpdatescheduleForm(){
    this.typeView = 'MODIF';
    // this.getSubjectHistory(this.subjectId);
  }

  onSelect(event:any, type: string) {
    if (event) {
      let hourClick = new Date(event).getHours();
      let minClick = new Date(event).getMinutes();

      if (type == 'START_TIME') {
        this.schedule.startDate = new Date(this.schedule.usageDate);
        this.schedule.startDate.setMinutes(minClick);
        this.schedule.startDate.setHours(hourClick);
        this.hourStart = hourClick;
        this.minStart = minClick;
      } else {
        this.schedule.endDate = new Date(this.schedule.usageDate);
        this.schedule.endDate.setMinutes(minClick);
        this.schedule.endDate.setHours(hourClick);
        this.hourEnd = hourClick;
        this.minEnd = minClick;
      }
    }
  }


  importExcel(event: any){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    const fileExcel = event.target.files[0];
    this.edu0204Service.importExcel(fileExcel).subscribe({
      next: (response) =>{
        this.listItemForExcel = response[CommonConstant.LIST_KEY];
        // this.getListTsstUser();
        this.toastrService.success('Success', '  Imported !');
        this.fileExcelNm = fileExcel.name;
        if(this.listItemForExcel.length > 0){
          this.disable = false;
        }else{
          this.disable = true;
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
    this.edu0204Service
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
      console.log(this.listChecked);
    }else{
      this.changeFieldList(this.listChecked,false);
      this.listChecked =  this.listChecked.filter(item => !this.listItemForExcel.includes(item));
      this.changeFieldList(this.listChecked,true);
    }
  }


}

