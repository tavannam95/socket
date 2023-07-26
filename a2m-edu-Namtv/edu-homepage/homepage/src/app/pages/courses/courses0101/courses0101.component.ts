import { Router } from '@angular/router';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import {CommonService} from "../../../services/common.service";
import {TranslateService} from "@ngx-translate/core";
import { LazyLoadScriptService } from 'src/app/services/lazy-load-script.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CourseService } from 'src/app/services/course.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-courses0101',
  templateUrl: './courses0101.component.html',
  styleUrls: ['./courses0101.component.css']
})
export class Courses0101Component implements OnInit, AfterViewInit {

  courses: any[] = [];
  totalCourse!:any;
  teachers: any[] = [];
  pagingRequest: any = {};
  url : any ;

  baseUrl:string = environment.apiURL
  constructor(
    private commonService: CommonService,
    public translate: TranslateService,
    private loadScriptService: LazyLoadScriptService,
    private router: Router,
    private  courseSerVice : CourseService ,
  ) {

  }

  ngOnInit(): void {
    this.initData()
    this.getTeachersList();
    this.getCoursesList();
  }


  ngAfterViewInit(): void {
    this.loadScriptService.loadScript();
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth'
});
  }

  counter(i: number) {
    return new Array(i);
  }
  private getCoursesList() {
    this.courses = this.commonService.getCoursesList();
    this.courses.sort((a, b) => {
      return b.key - a.key;
  });
    this.searchData();
  }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
  }


  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();

  }

  searchData() {
    let pagingRequest : any = {};
    pagingRequest.getAll = true;
    pagingRequest.displayStatus = true;
    this.courseSerVice.search(pagingRequest).subscribe((response) => {
      // //debugger
      this.courses = response.list.reverse();
      this.courses.forEach(element => {
        element.time = element.courseInfoModel!=null ? element.courseInfoModel.time : 0;
        element.courseGoal = element.courseInfoModel!=null ? element.courseInfoModel.courseGoal : element.courseGoal;
        element.imgPath =  this.baseUrl+'/tcco-files/getFile/'+element.imgPath+'?filePath='+element.imgPath;
      });

      this.url='';
      // this.url+=this.baseUrl+'/tcco-files/getFile/'+_response.imgPath+'?filePath='+_response.imgPath
      this.totalCourse = response.totalItems;
      CommonUtil.addIndexForListReverse(this.courses, this.pagingRequest.page, this.totalCourse);
    });
  }

  private getTeachersList() {
    this.teachers = this.commonService.getTeachersList();
  }

  gotoCoursesDetail(course: any){
    if(!course.isComingSoon){
      this.router.navigate(["/courses/detail"],{
        queryParams: {id: course.key , courseNm : course.courseNm}
      })
    }
  }
  }

