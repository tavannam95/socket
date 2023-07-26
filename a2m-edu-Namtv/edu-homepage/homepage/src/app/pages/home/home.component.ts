import { Router } from '@angular/router';
import { LazyLoadScriptService } from './../../services/lazy-load-script.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import {I18nService} from "../../services/i18n.service";
import {TranslateService} from "@ngx-translate/core";
import {I18nConfig} from "../../constants/i18n.config";
import {CommonService} from "../../services/common.service";
import { forkJoin } from 'rxjs';
import { Platform } from '@angular/cdk/platform';
import { NewsService } from 'src/app/services/news.service';
import { EventService } from 'src/app/services/event.service';
import { environment } from 'src/environments/environment';
import { TypicalService } from 'src/app/services/typical.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { CommonConstant } from 'src/app/constants/common.constant';
// import sal from 'sal.js'
declare var sal: any;
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {

  langKey?: any;
  courses: any[]= [];
  students: any[]= [];
  news: any[] = [];
  lang: any;
  faqs: any [] = [];
  teachers: any[] = [];
  teacherMaster : any;
  listEvent : any [] = [];
  listTypical : any [] = [];
  mobileFlag = false;
  baseUrl = environment.apiURL;
  urlImage =  this.baseUrl+'/tcco-files/getFile/'+'1'+'?filePath=';
  param :any = {};




  constructor(
    private platform: Platform,
    private lazyLoadService: LazyLoadScriptService,
    public translate: TranslateService,
    private commonService: CommonService,
    private newsService: NewsService,
    private eventService: EventService,
    private router: Router,
    private typicalService : TypicalService,

  ){

  }

  ngAfterViewInit(): void {
    this.lazyLoadService.loadScript();
  }

  ngOnInit(): void {
    this.getCoursesList1();
    this.getStudentsList1();
    this.getNews();
    this.getFAQs();
    this.getTeachersList();
    this.checkPlatform();
    this.param.searchStatus= true;
    this.getListEvent();
  }

  checkPlatform(){
    if (this.platform.ANDROID || this.platform.IOS || this.platform.SAFARI) {
      this.mobileFlag = true;
    }
  }

  counter(i: number) {
    return new Array(i);
  }

  getCoursesList1(){
    this.courses = this.commonService.getCoursesList();
  }

  getStudentsList1(){
    this.students = this.commonService.getStudentsList();

    // this.typicalService.getList(this.param).subscribe(res=>{
    //   this.students = res.list;
    //   if(this.students){
    //     this.students.forEach(element => {
    //       element.img =  this.urlImage + element.img;
    //     });
    //   }
    // })
  }

  getNews(){
    // this.newsService.getNews({start: 0,limit: 3}).subscribe((res: any)=>{
    //   this.news = res.datas;
    // });
  }

  private getFAQs() {
    this.faqs = this.commonService.getFAQs();
  }

  goToNewsDetail(id: any){
    this.router.navigate(["/blog/detail"],{
      queryParams: {id: id}
    })
  }

  gotoCoursesDetail(id: any){
    this.router.navigate(["/courses/detail"],{
      queryParams: {id: id}
    })
  }

  gotoCoursesEventCourse(item: any){
    // let courseId = CommonUtil.endCodeBase64( CommonConstant.ATOWM_EDU_CONSTANT + item.courseModel.key );
    let eventId = CommonUtil.endCodeBase64( CommonConstant.ATOWM_EDU_CONSTANT + item.key );
    this.router.navigate(["/courses/event"],{
      queryParams: {
        eventId: eventId,
        // courseId: courseId,
      }
    })
  }

  goToRegis(course: any){
    // let a = document.createElement('a');
    // a.href = '/apply';
    // a.click();
    let courseId = course.key;
    let courseNm = course.courseNm;


    this.router.navigate(['/apply'], {
      queryParams: {'courseId': courseId, 'courseNm': courseNm},
      // state: { searchRequest: $.extend(true, {}, this.request) },
    });
  }

  private getTeachersList() {
    this.teachers = this.commonService.getTeachersList()
    // this.teacherMaster = this.teachers[3]
    // this.teachers = this.teachers.filter(element => element.id!=3);
  }

  getListEvent(){


    this.eventService.getList(this.param).subscribe(res=>{
      // //debugger
      this.listEvent = res.list;
    })
  }

  // gotoTeacherDetail(id: any){
  //   this.router.navigate(["/instructors/detail"],{
  //     queryParams: {id: id}
  //   })
  // }


}
