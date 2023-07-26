import { ActivatedRoute, Router } from '@angular/router';
import { LazyLoadScriptService } from '../../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CourseService } from 'src/app/services/course.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { environment } from 'src/environments/environment';
import { CommonUtil } from 'src/app/utils/common-util';
import { CommonService } from 'src/app/services/common.service';
import { EventService } from 'src/app/services/event.service';

@Component({
  selector: 'app-eventCourse',
  templateUrl: './eventCourse.component.html',
  styleUrls: ['./eventCourse.component.css'],
})
export class EventCourseComponent implements OnInit, AfterViewInit {
  courseId: any;
  event: any = {};
  course: any = {};
  courseInfo: any = {};
  courseProgram: any[] = [];
  courseGoal: any[] = [];
  courses: any[] = [];
  totalCourse: any;
  baseUrl = environment.apiURL;
  pagingRequest: any = {};
  constructor(
    private loadScriptService: LazyLoadScriptService,
    private activatedRoute: ActivatedRoute,
    private courseService: CourseService,
    private router: Router,
    private route: ActivatedRoute,
    private commonService: CommonService,
    private eventService: EventService,
  ) {}

  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(async (params) => {
      this.event.key = CommonUtil.deCodeBase64( params['eventId'] ).replace(CommonConstant.ATOWM_EDU_CONSTANT, "");
      // this.courseId = CommonUtil.deCodeBase64( params['courseId'] ).replace(CommonConstant.ATOWM_EDU_CONSTANT, "");
      this.getEventById();
    });
    this.initData();
  }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;

    this.getCourse();
    this.getCoursesList1();
    this.getCourseInfo();
  }

  getEventById() {
    this.eventService.getById(this.event.key).subscribe((response) => {
        this.event.title = response.detail.eventTitle;
        this.event.content = response.detail.eventContent;
        this.event.courseId = response.detail.courseModel.key;
        this.courseId = response.detail.courseModel.key;
    });
  }

  getCoursesList1() {
    this.courses = this.commonService.getCoursesList();
  }

  getCourseInfo() {
    let request: any = {};
    request.courseId = this.course.id;
    this.courseService.getCourseInfo(request).subscribe((response) => {
      this.courseInfo = response.list.courseInfo;
    });
  }

  getCourse() {
    this.courseService.getCourseById(this.courseId).subscribe((response) => {
      this.course = response.detail;
      this.courseProgram = this.course.subjectModels.reverse();
      this.course.courseStartDate = this.course.courseStartDate
        ? new Date(this.course.courseStartDate)
        : null;
      this.course.courseEndDate = this.course.courseEndDate
        ? new Date(this.course.courseEndDate)
        : null;
      this.course.imgPath =
        this.baseUrl +
        '/tcco-files/getFile/' +
        this.course.imgPath +
        '?filePath=' +
        this.course.imgPath;
    });
  }

  // getListCourse() {
  //   this.courseService.search(this.pagingRequest).subscribe((response) => {

  //     console.log(this.courses)
  //     response.list.forEach((element:any) => {
  //       element.imgPath =  this.baseUrl+'/tcco-files/getFile/'+element.imgPath+'?filePath='+element.imgPath;
  //     });
  //     this.courses = response.list;
  //     this.courses = this.courses.filter(Element => Element.key !=this.courseId);

  //     this.totalCourse = response.totalItems;
  //     CommonUtil.addIndexForListReverse(this.courses, this.pagingRequest.page, this.totalCourse);
  //   });
  // }

  goToRegis() {
    // let a = document.createElement('a');
    // a.href = '/apply';
    // a.click();

    let eventId = this.event.key;
    this.router.navigate(['/applyForCourse'], {
      queryParams: {
        // courseId: CommonUtil.endCodeBase64( CommonConstant.ATOWM_EDU_CONSTANT + this.courseId ),
        eventId: CommonUtil.endCodeBase64( CommonConstant.ATOWM_EDU_CONSTANT + eventId ),
      },
      // state: { searchRequest: $.extend(true, {}, this.request) },
    });
  }
}
