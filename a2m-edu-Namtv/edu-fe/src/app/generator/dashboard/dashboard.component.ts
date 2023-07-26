import {Component, OnDestroy, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { faServer } from '@fortawesome/free-solid-svg-icons';
import {Subscription} from "rxjs";
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu020101Service } from 'src/app/services/edu/edu020101.service';
import { Edu020102Service } from 'src/app/services/edu/edu020102.service';
import { Edu0202Service } from 'src/app/services/edu/edu0202.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { environment } from 'src/environments/environment';
import { CalendarOptions } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGrigPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { ApproveService } from 'src/app/services/common/approve.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Edu0204Service } from 'src/app/services/edu/edu0204.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  faServer = faServer;

  basicData: any;
  basicData2: any;


  basicOptions: any = {
    plugins: {
      legend: {
        display: false
      }
    },
    maintainAspectRatio: false};

  subscription: Subscription | undefined;

  userInfo:any;
  authS: number = 0;
  genModelS: number = 0;
  genDataS: number = 0;
  DeliveryS: number = 0;
  baseUrl:string = environment.apiURL;
  roles : any =  AuthenticationUtil.getUserRole();
  userUid : any =  AuthenticationUtil.getUserUid();
  isAdmin : Boolean = false ;
  listClass : any = [] ;
  listCourse : any[] = [] ;
  listStudent : any = [] ;
  listApprover : any = [] ;
  totalClass : number = 0;
  totalStudent : number = 0;
  totalCourse : number = 0;
  totalCandidate : number = 0;
  totalClassInprogress : number = 0;
  totalStudentInprogress : number = 0;
  totalCourseInprogress : number = 0;
  totalCandidateToday : number = 0;
  type : String = "";

  scheduleSearch: any = {
    userId : "",
    userType : "",
  }
  listSchedule: any[] = [];
  events: any = [];

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
    // dateClick: (info) => this.dateClick(info),
  };

  // calendarOptions: CalendarOptions = {
  //   initialView: 'dayGridMonth',
  //   plugins: [dayGridPlugin],
  //   events: [
  //     // { title: 'event ', date: new Date }
  //   ],
  //   // dateClick :  this.handleDateClick.bind(this),
  //   eventClick : this.handleDateClick.bind(this),
  // };

  // handleDateClick(arg : any) {
  //   alert('date click! ' );
  // }


  constructor(
    private edu0102Service : Edu0102Service,
    private eud0201Service : Edu0201Service,
    private edu020101Service : Edu020101Service,
    private edu020102Service : Edu020102Service,
    private edu0204Service: Edu0204Service,
    private commonService : CommonService,
    private edu0101Service : Edu0101Service,
    private edu0202Service: Edu0202Service,
    private approveService: ApproveService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.initData();
    this.basicData = {
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
      datasets: [
        {
          label: 'Second Dataset',
          data: [28, 48, 40, 19, 86, 27, 90],
          fill: false,
          borderColor: '#FFA726',
          tension: .4
        }
      ]
    };

    this.basicData2 = {
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
      datasets: [
        {
          label: 'First Dataset',
          data: [6500, 5900, 8000, 8100, 9600, null, null],
          fill: false,
          borderColor: '#42A5F5',
          tension: .4
        },
        {
          label: 'First Dataset',
          data: [null, null, null, null, 9600, 5500, 7000],
          fill: false,
          borderColor: '#ad42f5',
          tension: .4
        },

      ]
    };
  }


  initData(){
    this.getDataByRole();
  }


  getDataByRole(){
    this.commonService.getUserInfo().toPromise().then(res=>{
      this.userInfo = res;
      let listRole = res.roles.map((element:any) => {return element.roleId});

      if(listRole.includes(CommonConstant.ROLE_SYS_ADMIN) || listRole.includes(CommonConstant.ROLE_SYS_MANAGER)){
        this.type = 'admin';
        this.isAdmin = true ;
        this.getListClassInprogress();
        this.getListStudent();
        this.getListStudentInprogress();
        this.getListCourse();
        this.getListCourseInprogress();
        this.getTotalCandidate();
        this.getTotalCandidateToday();
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'admin';
        this.getListStuSubtmited();
      } if(listRole.includes(CommonConstant.ROLE_SYS_TEA_ASSIS)){
        // this.getListStudentByUserUid();
        this.getListCourseByUserUid();
        this.type = 'assist';
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'assist';
        this.getListStuSubtmited();
      } if(listRole.includes(CommonConstant.ROLE_SYS_TEACHER)){

        // this.getListStudentByUserUid();
        this.getListCourseByUserUid();
        this.type = 'teacher';
        this.scheduleSearch.userId = res.userUid;
        this.scheduleSearch.userType = 'teacher';
        this.getListStuSubtmited();
      } if(listRole.includes(CommonConstant.ROLE_SYS_STUDENT)){
        // this.getListStudentByUserUid();
        this.getListCourseByUserUid();
        this.type = 'student';
        this.scheduleSearch.userId = res.userInfoId;
        this.scheduleSearch.userType = 'student';
        this.getListStuSubtmited();
      }if(listRole.includes(CommonConstant.ROLE_SYS_APPROVER)){
        this.getListCourseByUserUid();
        this.getListApprover();
        this.type = 'approver';
      }
      this.getListClass();

      // if(!this.isAdmin){
         // this.getListStudentByUserUid();
      // }

    })
  }

  getListStuSubtmited(){
    this.edu0204Service.getScheduleList(this.scheduleSearch).subscribe((response) => {
      this.listSchedule = response[CommonConstant.LIST_KEY];
      this.setEvents();
    });
  }

  setEvents(){
    for(let i = 0; i < this.listSchedule.length; i++){
      const schedule = this.listSchedule[i];
      const title = 'Môn học: ' + schedule.subjectNm + ' / Lớp: ' + schedule.classNm + ' / Phòng: ' + schedule.roomNm;
      const event = {title: title, start : schedule.startDate, end : schedule.endDate, id: schedule.key};
      this.events.push(event);
    }
    this.calendarOptions.events = this.events;
  }

  getListApprover(){
    this.approveService.searchListPeding( AuthenticationUtil.getUserUid(), false ).subscribe((response) => {

      this.listApprover = response[CommonConstant.LIST_KEY];
    });
  }

  getListCourseByUserUid(){
      let param : any = {};
      param.key = this.userUid;
      //
      if(!this.roles.includes("R000")){
        this.edu0102Service.getListCourseByUserUid(param).subscribe((res:any)=>{
          this.listCourse = res.list;
          if(this.listCourse ) this.convertUrlPath(this.listCourse);
        })
      }else{
        let param : any = {};
        param.getAll = true;
        this.edu0102Service.search(param).subscribe((res:any)=>{
          this.listCourse = res.list;
          if(this.listCourse.length>0) this.convertUrlPath(this.listCourse);
        })
      }
    }

   getListClass( ){
    if(this.isAdmin){
      let request :any = {};
      request.getAll = true;
      this.eud0201Service.search(request).subscribe((response:any)=>{
        this.totalClass = response.totalItems;
      })
    }
    else{
      this.eud0201Service.getListClassByUserUid(this.userUid).subscribe(async (res: any) =>{
        this.listClass = await res.list;
        this.listClass.forEach((element:any) => {

          var currentDate = new Date();
          var startDate = new Date(element.startDate);
          var endDate = new Date(element.endDate);
          // var Difference_In_Time = endDate.getTime() - startDate.getTime();
          var totalDate = (endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24);
          if(currentDate.getTime() > startDate.getTime()){
            var completedDays = (currentDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24);
            var  percentComplete = completedDays/totalDate * 100 ;
            element.percentComplete = (Math.round(percentComplete * 100) / 100).toFixed(0);
            if(element.percentComplete>100) element.percentComplete = 100;
          }else{
            element.percentComplete = 0;
          }
          element.listStudent = [];
          element.listTeacher = [];
          const subjectModels = element.courseModel.subjectModels;
          const docCheck: any[] = [];
          const listDoc: any[] = [];
          subjectModels.forEach((sub:any) => {
            const listChapter = sub.chapterModels;
            listChapter.forEach((chap:any) => {
              chap.lectureModels.forEach((lec:any) => {
                this.getListViewDoc(lec);
                listDoc.push(lec);
                if(lec.listViewDoc.length > 0 && lec.listViewDoc[0].isComplete){
                  docCheck.push(lec);
                }
              });
              chap.listQuiz.forEach((quiz:any) => {
                quiz.typeDocument = 'QUIZ';
                this.getListViewDoc(quiz);
                listDoc.push(quiz);
                if(quiz.listViewDoc.length > 0 && quiz.listViewDoc[0].isComplete){
                  docCheck.push(quiz);
                }
              });
            });
          });
          if(docCheck.length > 0){
            var  percentDocComplete = docCheck.length/listDoc.length * 100 ;
            element.percentDocComplete = (Math.round(percentDocComplete * 100) / 100).toFixed(0);
            if(element.percentDocComplete>100) element.percentDocComplete = 100;
          }else{
            element.percentDocComplete = 0;
          }
        });
        this.listClass.forEach(async (element:any) => {
          await this.edu020101Service.getStudentByClass(element.key).subscribe(async (response:any) => {
            element.listStudent = await response.list ;

          });

         await this.edu020102Service.getTeacherByClass(element.key).subscribe(async (response:any) => {
            element.listTeacher = await response.list ;
          });
        });
      })
    }
  }

  getListViewDoc(doc:any){
    if(doc.listViewDoc.length >0){
      doc.listViewDoc.forEach((e:any) => {
        if(e.userUid != this.userInfo.userUid){
          doc.listViewDoc = doc.listViewDoc.filter((item:any) => item != e);
        }
      })
    }
  }

  getTotalCandidate(){
    let request :any = {};
    request.getAll = true;
    this.edu0202Service.search(request).subscribe((response:any)=>{
      this.totalCandidate = response.totalItems;
    })
  }

  getTotalCandidateToday(){
    this.edu0202Service.getCountCandidateAllType().subscribe((response : any)=>{
      let string = response.totalItems;
      let totalItems = string.split("!#@");
      this.totalCandidateToday = totalItems[0];
      // this.pending = totalItems[1];
      // this.inProgress = totalItems[2];
    })

  }

  getListCourse(){
    let request :any = {};
    request.getAll = true;
    this.edu0102Service.search(request).subscribe((response:any)=>{
      this.listCourse = response.list;
      if(this.listCourse ) this.convertUrlPath(this.listCourse);
      this.totalCourse = response.totalItems;
    })
  }

  getListCourseInprogress(){
    let request :any = {};
    request.getAll = true;
    this.edu0102Service.getListCourseInprogress(request).subscribe((response:any)=>{
      this.totalCourseInprogress = response.list.length;
    })
  }

  getListStudentInprogress(){
    let request :any = {};
    request.getAll = true;

    this.edu0101Service.getListStudentInprogress(request).subscribe((response:any)=>{
      this.totalStudentInprogress = response.totalItems ;
    })
  }

  getListStudent(){
    let request :any = {};
    request.getAll = true;
    request.page = 0;
    request.rows = 10;
    this.edu0101Service.search(request).subscribe((response:any)=>{
      this.totalStudent = response.totalItems;
    })
  }

  getListStudentByUserUid(){
    let request :any = {};
    request.searchText = this.userUid ;
    this.edu0101Service.getListStudentByUserUid(request).subscribe((response)=>{
      this.listStudent = response.list ;
      if(this.listStudent) this.convertUrlPath(this.listStudent);
    })
  }

  getListClassInprogress(){
    let request :any = {};
    request.getAll = true;
    this.eud0201Service.getListClassInprogress(request).subscribe((response:any)=>{
      this.totalClassInprogress = response.totalItems;
    })
  }


  convertUrlPath(list : any){
    list.forEach((element:any) => {
      let imgUrl = this.baseUrl +'/tcco-files/getFile' + "/file" + '?filePath='+element.imgPath;
      element.imgUrl = imgUrl;

    });
  }

  gotoClass(clss : any){
    // this.commonService.changeIsScroll(true);
    this.router.navigate(["/edu/edu0201Form"],{
      queryParams: {id: clss.key }
    })
  }

  gotoClassNotification(clss : any){
    // this.commonService.changeIsScroll(true);
    this.router.navigate(["/dashboard"],{
      queryParams: {id: clss.key }
    })
  }

  gotoCoursePreview(courseId : String){
    this.router.navigate(["/course/course0201Preview"],{
      queryParams: {courseId: courseId }
    })
  }


  updateChartOptions() {
    this.applyLightTheme();
  }

  goDocument(approver: any){
    const url = approver.documentUrl.substring(1);
    this.router.navigateByUrl(url);
  }

  applyLightTheme() {
    this.basicOptions = {

      responsive: false,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false

          // labels: {
          //   color: '#495057'
          // }
        }
      },
      scales: {
        x: {
          ticks: {
            color: '#495057'
          },
          grid: {
            color: '#ebedef'
          }
        },
        y: {
          ticks: {
            color: '#495057'
          },
          grid: {
            color: '#ebedef'
          }
        }
      }
    };
  }

  ngOnDestroy() {
  }

  eventClick(info:any) {
    //debugger
    const event = info.event._def;
    const scheduleId = event.publicId;
    this.router.navigate(['/edu/edu0204Form'], {
      queryParams: {
        scheduleId: scheduleId
      },
    });
  }

  dateClick(info:any){
    //debugger
    this.router.navigate(['/edu/edu0204Form'], {
      queryParams: {
        scheduleId: ''
      },
    });
  }
}
