import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';

import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  Inject,
  ViewChild,
  ElementRef,
} from '@angular/core';

import { DatePipe, formatDate } from '@angular/common';
import { schedule } from 'src/app/model/sys/schedule';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { AuthenticationService } from 'src/app/services/common/authentication.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { CommonService } from 'src/app/services/common/common.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu0204Service } from 'src/app/services/edu/edu0204.service';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';

@Component({
  selector: 'app-schedule-form',
  templateUrl: './schedule-form.component.html',
  styleUrls: ['./schedule-form.component.css']
})
export class ScheduleFormComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  disable:any  = true;
  disablecheck = false;
  scheduleId: any;
  submitted: any = false;
  dateForCalendar: any;
  schedule : any={};
  typeView : any;
  checkView : any = false;
  pagingRequestSchedule: any = {};
  pagingRequest: any = {};
  pagingRequestRoom: any = {};
  listSchedule: any[] = [];
  listScheduleRoomId: any[] = [];
  listScheduleSave: any[] = [];
  listScheduleDup: any[] = [];
  listClass!: any[];
  listSubject:any;
  listRoom:any;
  isDuplicate:any = false;
  classChecked !: any;
  subjectChecked !: any;
  roomChecked !: any;
  hiddenSubject: any = true;
  hourStart: any;
  minStart: any;
  hourEnd: any;
  minEnd: any;
  usageDate: any;
  stopDate:any
  startTime:any = String;
  endTime:any = String;
  userInfo : any;
  minimumDate:any;
  startDate:any;
  endDate:any;
  status: any = false;
  alertDuplicate:any;

  constructor(
    private edu0204Service: Edu0204Service,
    private toastrService: ToastrService,
    public dialogRef: MatDialogRef<ScheduleFormComponent>,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    public commonService: CommonService,
    private uploadService: FileUploadService,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private datePipe: DatePipe,
    public dialog: MatDialog,
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
      this.disablecheck = true;
    } else {
      this.typeView = 'ADD';
      // this.schedule = new schedule();
      // this.schedule.status = false;
      // this.schedule.displayStatus = false;
      // this.schedule.isComingSoon = false;
      this.disable = true;
      this.stopDate = new Date();
      this.init();
    }
  }

  getTsstUser(){
    this.commonService.getUserInfo().subscribe(
      (response) =>{
        this.userInfo = response;
        // if(this.userInfo.userType != 'STU'){
        //   this.disable = false;
        // }else{
        //   this.disable = true;
        // }
      }
    );
  }

  init() {
    if(this.dateForCalendar != undefined || this.dateForCalendar != null){
    // this.schedule.usageDate = new Date(this.dateForCalendar);
    this.usageDate = new Date(this.dateForCalendar);
    // this.usageDate.setHours(9);
    }else{
      // this.schedule.usageDate = new Date();
      this.usageDate = new Date();
    }

    this.minimumDate = new Date();
    this.startDate = new Date(this.usageDate);
    this.hourStart = this.usageDate.getHours();
    this.minStart = this.usageDate.getMinutes();
    const checkMinStart = this.minStart % 15
    if(checkMinStart != 0){
      this.minStart = this.minStart +(15-checkMinStart);
      if(this.minStart >= 60){
        this.minStart = this.minStart - 60;
        this.hourStart = this.hourStart + 1;
      }
    }

    this.startDate.setHours(this.hourStart)
    this.startDate.setMinutes(this.minStart);

    this.endDate = new Date(this.startDate);
    const minuteEndDate = this.startDate.getMinutes() + 30;
    this.hourEnd = this.startDate.getHours();
    this.minEnd = minuteEndDate;
    if(minuteEndDate >= 60){
      this.hourEnd = this.startDate.getHours() + 1;
      this.minEnd = minuteEndDate - 60;
    }else{
      this.hourEnd = this.startDate.getHours();
      this.minEnd = minuteEndDate;
    }
    this.endDate.setHours(this.hourEnd);
    this.endDate.setMinutes(this.minEnd);
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
      this.usageDate = this.schedule.usageDate;
      this.startDate = this.schedule.startDate;
      this.endDate = this.schedule.endDate;
      this.getListSubject(this.classChecked.courseId);
    });
  }
  getScheduleForRoomId(event:any){
    this.pagingRequestSchedule.roomId = event.value.key;
    // this.isDuplicate = false;
    this.edu0204Service.checkDuplicates(this.pagingRequestSchedule).subscribe((response) => {
      this.listScheduleRoomId = response.list;
    });
  }
  checkForDuplicates(schedule: any) {
    // this.isDuplicate = false;
    for(let i = 0; i < this.listScheduleRoomId.length; i++){
      const scheduleForRoom = this.listScheduleRoomId[i];
      const startDate = new Date(scheduleForRoom.startDate);
      const endDate = new Date(scheduleForRoom.endDate);
      if(startDate.getTime() > schedule.endDate.getTime() || endDate.getTime() < schedule.startDate.getTime()){
        this.isDuplicate = false;
      }else {
        this.isDuplicate = true;
        let minStartTime =  startDate.getMinutes() + '';
        if(Number(minStartTime) < 10){
          minStartTime = '0' + minStartTime;
        }
        let minEndTime =  endDate.getMinutes() + '';
        if(Number(minEndTime) < 10){
          minEndTime = '0' + minEndTime;
        }
        this.startTime = startDate.getHours() + ':' + minStartTime;
        this.endTime = endDate.getHours() + ':' + minEndTime;
        break;
      }
    }
    if(this.isDuplicate){
      this.listScheduleDup.push(schedule);
    }else{
      this.listScheduleSave.push(schedule);
    }
  }
  showEndDate(boolean:any){
    this.disable = boolean;
  }

  onChangedDate(schedule:any) {

    schedule.startDate = new Date(schedule.usageDate);
    schedule.startDate.setHours(this.hourStart);
    schedule.startDate.setMinutes(this.minStart);
    schedule.startDate.setSeconds(0);

    schedule.endDate = new Date(schedule.usageDate);
    schedule.endDate.setHours(this.hourEnd);
    schedule.endDate.setMinutes(this.minEnd);
    schedule.endDate.setSeconds(0);
  }

  onSaveAll(invalid: any){
    if (invalid) {
      this.submitted = true;
      return;
    }
    this.listScheduleSave = [];
    this.listScheduleDup = [];
    this.setScheduleList();

    let scheduleCheck = this.listSchedule[0];
    this.onChangedDate(scheduleCheck);
    const checkDate = new Date();
    if(checkDate.getTime() > scheduleCheck.startDate.getTime()){
      alert(this.trans.instant('message.alertCheckDate'))
    }else{
      if(scheduleCheck.endDate.getTime() <  scheduleCheck.startDate.getTime()){
        alert(this.trans.instant('message.alertCheckDateTime'));
      }else{
        for(let i = 0; i < this.listSchedule.length; i++){
          let schedule = this.listSchedule[i];
          schedule.roomId = this.roomChecked.key;
          schedule.classId = this.classChecked.key;
          schedule.subjectId = this.subjectChecked.key;
          schedule.isFinish = false;
          this.onChangedDate(schedule);
          this.checkForDuplicates(schedule);
        }
        this.setListSchedule();
      }
    }
  }
  setListSchedule(){
    if(this.listScheduleDup.length == 0 && this.listScheduleSave.length != 0){
      this.setConfirmSave();
    }
    if(this.listScheduleDup.length != 0){
      let dateDup = '';
      for(let i = 0; i < this.listScheduleDup.length; i++){
        let dateSchedule = this.datepipe.transform(this.listScheduleDup[i].startDate, 'dd/MM');
        if(i < this.listScheduleDup.length - 1){
          dateDup += dateSchedule + ', ';
        }else{
          dateDup += dateSchedule;
        }
      }
      let StringRoomNm = this.trans.instant('edu.edu0204.string.StringRoomNm');
      let StringClassCheck = this.trans.instant('edu.edu0204.string.StringClassCheck');
      let StringDateDup = this.trans.instant('edu.edu0204.string.StringDateDup');
      let alertDuplicate = StringRoomNm + '" ' + this.roomChecked.roomNm + ' " ' + StringClassCheck + this.startTime + '~' + this.endTime + ' ' + StringDateDup + dateDup;
      alert(alertDuplicate);
      if(this.listScheduleDup.length < this.listSchedule.length){
        this.setConfirmSaveForDup();
      }
    }
  }
  setSchedule(){
    this.schedule = new schedule();
    this.schedule.status = false;
    this.schedule.displayStatus = false;
    this.schedule.isComingSoon = false;
  }
  setScheduleList(){
    this.listSchedule = [];
    let usageDate = new Date(this.usageDate);
    let endDate = new Date(this.stopDate);
    let date = Math.ceil((endDate.getTime()-usageDate.getTime())/(24*60*60*1000));
    let idxDate = Math.floor(date/7);
    for(let i = 0; i <= idxDate; i++){
      let scheduleDate = new Date();
      scheduleDate.setDate(usageDate.getDate() + 7*i);
      this.setSchedule();
      this.schedule.usageDate = scheduleDate;
      this.listSchedule.push(this.schedule);
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
        // this.goList();
      },
    });
  }
  setConfirmSaveForDup() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.save'),
      message: this.trans.instant('edu.edu0204.message.confirm.save'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	    rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.saveAll();
      },
      reject: () => {
        // this.goList();
      },
    });
  }

  saveAll() {
    this.edu0204Service.save(this.listScheduleSave).subscribe({
      next: (response) => {
        if(!this.listSchedule[0].key){
          //this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.handleAfterSaveSuccess(this.trans.instant('message.success.saveSuccess'));
          this.saved(response[CommonConstant.KEY]);

        }else{
          //this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
          this.handleAfterSaveSuccess(this.trans.instant('message.success.updateSuccess'));
          this.saved(response[CommonConstant.KEY]);
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

}

