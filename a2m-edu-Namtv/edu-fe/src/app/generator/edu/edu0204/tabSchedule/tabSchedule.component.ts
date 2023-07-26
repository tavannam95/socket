import { state } from '@angular/animations';
import { AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CourseFormComponent } from 'src/app/generator/edu/edu0102/component/course-form/course-form.component';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { ListScheduleComponent } from '../list-schudle/list-schedule.component';
import { ScheduleCalendarComponent } from './ScheduleCalendar/ScheduleCalendar.component';

@Component({
  selector: 'app-tabSchedule',
  templateUrl: './tabSchedule.component.html',
  styleUrls: ['./tabSchedule.component.css']
})
export class TabScheduleComponent implements OnInit, AfterViewInit {

  @ViewChild('listSchedule')
  listSchedule!: ListScheduleComponent
  @ViewChild('ScheduleCalendar')
  ScheduleCalendar!: ScheduleCalendarComponent

  courseId: any;
  subjectId:any;
  tab:any;
  couSubjectLst: any[] = [];
  courseObj: any = new Object();
  col:string = 'col-lg-12';
  constructor(
    private route : ActivatedRoute,
    private edu0102Service: Edu0102Service,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    private router: Router,
  ) {
    // const s = this.router.getCurrentNavigation().extras.state;
  }

   ngOnInit()  {
     this.route.queryParams.subscribe( (params) => {
      this.courseId = params['courseId'];
      this.tab = params['tab'];
      if(this.tab){
        this.changeTab(this.tab);
      }
    });
  }

  ngAfterViewInit(){
    this.route.queryParams.subscribe( (params) => {
      const tab = params['tab'];
      if(tab){
        this.changeTab(tab);
      }
    });
  }

  //  getData(){
  //   let requestModel = new searchModel();
  //   requestModel.key = this.courseId;
  //   this.edu0102Service.getCourseByCondition(requestModel).subscribe( (response:any) => {
  //   this.courseObj =  response[CommonConstant.DETAIL_KEY];
  //   if(this.courseObj.subjectModels.length>0){
  //     this.couSubjectLst = this.courseObj.subjectModels.reverse();
  //   }
  //   });
  // }

  changeTab(tabNumber: any){
    // if(this.courseId==""){
    //   let mes = this.trans.instant('course.info.init.required')
    //   this.confirmationService.confirm({
    //     header: mes,
    //     acceptLabel: this.trans.instant('button.confirm.title'),
    //     accept: () => {
    //       // tabNumber=1;
    //     },

    //   });
    //   tabNumber=1;
    // }

    const ele = document.getElementById('tabSubject'+tabNumber);


    ele?.click();
      // let doan = "";
    // switch(tabNumber){
    //   case 1:
    //     // this.roleList.getData();
    //     this.courseForm.ngOnInit();
    //     break;

    //   case 2:
    //     this.subjectList.ngOnInit();
    //     break;

    // }
  }

}
