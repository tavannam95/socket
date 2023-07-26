import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CalendarOptions } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import timeGrigPlugin from '@fullcalendar/timegrid';
import { TranslateService } from '@ngx-translate/core';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0204Service } from 'src/app/services/edu/edu0204.service';
import { I18nService } from 'src/app/services/i18n.service';

// import $ from "jquery";
// declare var $: any;

@Component({
  selector: 'app-schedule-calendar',
  templateUrl: './ScheduleCalendar.component.html',
  styleUrls: ['./ScheduleCalendar.component.css']
})
export class ScheduleCalendarComponent implements OnInit {
  constructor(
    private edu0204Service: Edu0204Service,
    private trans: TranslateService,
    public dialog: MatDialog,
    private i18nService: I18nService,
    private router: Router,
    public commonService: CommonService,
  ) { this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  language: any;
  isStudent = this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT );
  calendarOptions: CalendarOptions = {
    initialView: 'dayGridMonth',
    themeSystem: 'bootstrap5',
    plugins: [dayGridPlugin, timeGrigPlugin, interactionPlugin],
    events: [],
    eventColor: '#bbffcc',
    eventTextColor: '#495057',
    nowIndicator: true,
    height: 700,
    dayMaxEvents: true,
    selectable: true,
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay'
    },
    titleFormat: { // will produce something like "Tuesday, September 18, 2018"
      month: '2-digit',
      year: 'numeric',
      day: '2-digit',
    },
    slotLabelFormat:{
      hour:'2-digit',
      minute:'2-digit',
      hour12:false
    },
    eventClick: (info) => this.eventClick(info),
    // eventMouseEnter: (info) => this.eventMouseEnter(info),
    dateClick: (info) => this.dateClick(info),
  };

  pagingRequest: any = {};
  schedule!:any;
  listSchedule :any[] = [];
  totalSchedule!:any;
  events: any = [];

  scheduleSearch: any = {
    userId : "",
    userType : "",
  }

  ngOnInit(): void {
    this.initData();
    // this.searchData()
  }

  initData(){
    this.getDataByRole();
  }

  // customCssEvent() {
  //   $('a .fc-event').css({
  //     border: '1px solid #c8c8c8',
  //     color: 'white',
  //     padding: '0.571em 1em',
  //   });
  // }

  searchData() {
    this.pagingRequest.getAll = true;
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    // this.edu0204Service.search(this.pagingRequest).subscribe((response) => {
    this.edu0204Service.getScheduleList(this.scheduleSearch).subscribe((response) => {
      this.listSchedule = response.list;
      this.totalSchedule = response.totalItems;
      dialogSpinner.close();
      this.setEvents();
    });
  }

  setEvents(){
    for(let i = 0; i < this.listSchedule.length; i++){
      const schedule = this.listSchedule[i];
      const title = 'Môn học: ' + schedule.subjectNm + ' / Lớp: ' + schedule.classNm + ' / Phòng: ' + schedule.roomNm;
      const event = {title: title, start : schedule.startDate, end : schedule.endDate, id: schedule.key, background:'green'};
      this.events.push(event);
    }
    this.calendarOptions.events = this.events;
  }

  handleDateClick(calDate:any) {
    // console.log(calDate);
  }

  eventClick(info:any) {
    const event = info.event._def;
    const scheduleId = event.publicId;
    this.router.navigate(['/edu/edu0204Form'], {
      queryParams: {
        scheduleId: scheduleId
      },
    });
  }

  dateClick(info:any){
    if(!this.isStudent){
      const date = new Date(info.date);
      const minDate = new Date();
      date.setHours(minDate.getHours());
      date.setMinutes(minDate.getMinutes()+10);
      if(date.getTime() >= minDate.getTime()){
        this.router.navigate(['/edu/edu0204Form'], {
          queryParams: {
            scheduleId: '',
            date: date,
          },
        });
      }else{
        alert(this.trans.instant('edu.edu0204.string.alertCheckDay'));
      }
    }
  }

  getDataByRole(){
    this.commonService.getUserInfo().toPromise().then(res=>{

      let listRole = res.roles.map((element:any) => {return element.roleId});

      if(listRole.includes(CommonConstant.ROLE_SYS_ADMIN) || listRole.includes(CommonConstant.ROLE_SYS_MANAGER)){
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'admin';
        // this.searchData();
      } if(listRole.includes(CommonConstant.ROLE_SYS_TEA_ASSIS)){
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'assist';
        // this.searchData();
      } if(listRole.includes(CommonConstant.ROLE_SYS_TEACHER) && !(listRole.includes(CommonConstant.ROLE_SYS_ADMIN) || listRole.includes(CommonConstant.ROLE_SYS_MANAGER))){
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'teacher';
        // this.searchData();
      } if(listRole.includes(CommonConstant.ROLE_SYS_STUDENT)){
        this.scheduleSearch.userId = res.userInfoId;
        this.scheduleSearch.userType = 'student';
        // this.searchData();
      }
      this.searchData();
    })
  }

}
