import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router, RoutesRecognized} from '@angular/router';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material/dialog';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { Course0101Service } from 'src/app/services/course/course0101.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { I18nService } from 'src/app/services/i18n.service';
import { CommonService } from 'src/app/services/common/common.service';
import { I18nConfig } from 'src/app/config/i18n.config';


@Component({
  selector: 'bar-my-course',
  templateUrl: './bar-my-course.component.html',
  styleUrls: ['./bar-my-course.component.css']
})
export class BarMyCourseComponent implements OnInit {
  private url = '';
  public currentMenu: any;
  public parentMenu: any;
  language: any;
  pagingRequest: any = {};

  chapterId : any;
  lectureId: any;
  subjectId : any;
  courseId : any;
  quizId : any;

  courseNm: any;
  subjectNm: any;
  chapterNm: any;
  lectureNm: any;
  quizNm : any;
  constructor(
    private edu0102Service: Edu0102Service,
    private course0101Service: Course0101Service,
    private course0102Service: Course0102Service,
    private course0103Service: Course0103Service,
    private quizService: QuizService,
    private toastr: ToastrService,
    private i18nService: I18nService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    private route : ActivatedRoute,
    private commonService: CommonService,
  ) {
    router.events.subscribe((routerEvent) => {
      if (routerEvent instanceof NavigationEnd) {
        this.url = router.url;
      }
    });
    this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  ngOnInit(): void {
    // this.getdasboard();
    this.route.queryParams.subscribe(async (params) => {
      this.lectureId = Number(params['lectureId']);
      this.chapterId = Number(params['chapterId']);
      this.subjectId = Number(params['subjectId']);
      this.courseId = Number(params['courseId']);
      this.quizId = Number(params['quizId']);

    });
    this.pagingRequest.courseId = this.courseId.toString();
    this.pagingRequest.subjectId = this.subjectId.toString();
    this.pagingRequest.chapterId = this.chapterId.toString();
    this.pagingRequest.lectureId = this.lectureId.toString();
    this.pagingRequest.quizId = this.quizId.toString();
    this.getNameProgress();
    // if(this.courseId){
    //   this.getCourse(this.courseId);
    // }
    // if(this.lectureId){
    //   this.getLecture(this.lectureId);
    // }
    // if(this.quizId){
    //   this.getQuiz(this.quizId);
    // }
  }

  getNameProgress(){
    this.edu0102Service.getNameProgress(this.pagingRequest).subscribe((response) => {
      this.courseNm = response.COURSE_NM;
      // this.subjectNm = response.SUBJECT_NM;
      // this.chapterNm = response.CHAPTER_NM;
      this.lectureNm = response.LECTURE_NM;
      this.quizNm = response.QUIZ_NM;
    });
  }
  // getCourse(param : any){
  //   this.edu0102Service.getCourseById(param).subscribe((response) => {
  //     this.courseNm = response[CommonConstant.DETAIL_KEY].courseNm;
  //   });
  // }

  // getLecture(param : any){
  //   this.course0101Service.getLectureById(param).subscribe((response) => {
  //     this.lectureNm = response[CommonConstant.DETAIL_KEY].lectureNm;
  //   });
  // }

  // getQuiz(param : any){
  //   this.quizService.getQuizById(this.quizId).subscribe((response) => {
  //     this.quizNm = response[CommonConstant.DETAIL_KEY].quizNm;
  //   });
  // }


  moveCourse(){
    this.router.navigate(['course/course0201Preview'], {
      queryParams: { courseId: this.courseId },
    });
  }
  moveLecture(){
    this.router.navigate(['/course/course0201Lecture'], {
      queryParams: {
        lectureId: this.lectureId,
        courseId : this.courseId
      },
    });
  }
  moveQuiz(){
    this.router.navigate(['/course/course0201Quiz'], {
      queryParams: {
      quizId: this.quizId,
      courseId : this.courseId},
    });
  }
}
