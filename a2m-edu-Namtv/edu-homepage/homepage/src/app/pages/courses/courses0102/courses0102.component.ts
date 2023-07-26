import { ActivatedRoute, Router } from '@angular/router';
import { LazyLoadScriptService } from './../../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CourseService } from 'src/app/services/course.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { environment } from 'src/environments/environment';
import { CommonUtil } from 'src/app/utils/common-util';
import { CommonService } from 'src/app/services/common.service';

@Component({
  selector: 'app-courses0102',
  templateUrl: './courses0102.component.html',
  styleUrls: ['./courses0102.component.css']
})
export class Courses0102Component implements OnInit, AfterViewInit {
  courseId: any;
  course: any = {};
  courseInfo: any = {};
  courseProgram: any[] = [];
  courseGoal :  any[] = [];
  courses: any[] = [];
  totalCourse: any;
  baseUrl = environment.apiURL;
  pagingRequest: any = {};
  constructor(
    private loadScriptService: LazyLoadScriptService,
    private activatedRoute: ActivatedRoute,
    private courseService  : CourseService,
    private router: Router,
    private commonService: CommonService,
  ) {
    this.activatedRoute.queryParams.subscribe((params: any) =>{
      this.course.id = params.id;
      this.course.courseNm = params.courseNm;
      this.courseId = params.courseId;
    });
  }

  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
  }

  ngOnInit(): void {

    this.initData()

  }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
    this.getCourse()
    this.getCoursesList1()
    this.getCourseInfo();
  }



  getCoursesList1(){
    this.courses = this.commonService.getCoursesList();
  }

  getCourseInfo(){
    let request :any ={};
    request.courseId = this.course.id;
    this.courseService.getCourseInfo(request).subscribe((response) => {
      this.courseInfo = response.list.courseInfo;
    })
  }

  getCourse(){
    this.courseService.getCourseById(this.course.id).subscribe((response) => {
      this.course = response.detail
      this.courseProgram = this.course.subjectModels.reverse();
      this.course.courseStartDate = this.course.courseStartDate? new Date(this.course.courseStartDate): null;
      this.course.courseEndDate = this.course.courseEndDate? new Date(this.course.courseEndDate): null;
      this.course.imgPath =  this.baseUrl+'/tcco-files/getFile/'+this.course.imgPath+'?filePath='+this.course.imgPath;
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

  gotoCoursesDetail(id: any){
    this.courseId =  id;
    this.initData();
    // this.router.navigate(["/courses/detail"],{
    //   queryParams: {id: id}
    // })

  }
  goToRegis(){
    let courseId = this.course.key;
    this.router.navigate(['/applyForCourse'], {
      queryParams: {course: courseId, eventId: '',},
    });
  }
}


