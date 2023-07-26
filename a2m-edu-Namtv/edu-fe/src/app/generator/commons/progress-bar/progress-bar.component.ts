import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router, RoutesRecognized} from '@angular/router';
import {CommonService} from "../../../services/common/common.service";
import {I18nConfig} from "../../../config/i18n.config";
import {I18nService} from "../../../services/i18n.service";
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


@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.css']
})
export class ProgressBarComponent implements OnInit {
  private history = [];
  private menu = [];
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
    // if(this.courseId){
    //   this.getCourse(this.courseId);
    // }
    // if(this.lectureId){
    //   this.getLecture(this.lectureId);
    // }
    // if(this.subjectId){
    //   this.getSubject(this.subjectId);
    // }
    // if(this.chapterId){
    //   this.getChapter(this.chapterId);
    // }
    // if(this.quizId){
    //   this.getQuiz(this.quizId);
    // }
    this.getNameProgress();
    this.ChangeData();
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

  // getSubject(param : any){
  //   this.course0102Service.getSubjectById(param).subscribe((response) => {
  //     this.subjectNm = response[CommonConstant.DETAIL_KEY].subjectNm;
  //   });
  // }

  // getChapter(param : any){
  //   this.course0103Service.getChapterById(param).subscribe((response) => {
  //     this.chapterNm = response[CommonConstant.DETAIL_KEY].chapterNm;
  //   });
  // }

  // getQuiz(param : any){
  //   this.quizService.getQuizById(this.quizId).subscribe((response) => {
  //     this.quizNm = response[CommonConstant.DETAIL_KEY].quizNm;
  //   });
  // }
  moveCourseByMyCourse(){
    this.router.navigate(['course/course0201Preview'], {
      queryParams: { courseId: this.courseId },
    });
  }
  ChangeData(){
    this.commonService.currentInFoCourse.subscribe((response) => {
      if(response.courseId){
        this.pagingRequest.courseId = response.courseId;
        this.pagingRequest.subjectId = response.subjectId;
        this.pagingRequest.chapterId = response.chapterId;
        this.pagingRequest.lectureId =''
        this.pagingRequest.quizId = ''
        this.getNameProgress();
      }
    })
  }

  getNameProgress(){
    this.edu0102Service.getNameProgress(this.pagingRequest).subscribe((response) => {
      this.courseNm = response.COURSE_NM;
      this.subjectNm = response.SUBJECT_NM;
      this.chapterNm = response.CHAPTER_NM;
      this.lectureNm = response.LECTURE_NM;
      this.quizNm = response.QUIZ_NM;
    });
  }

  moveCourse(){
    this.router.navigate(['/course/course0102'], {
      queryParams: { courseId: this.courseId },
    });
  }
  moveSubject(){
    this.router.navigate(['/course/course0103'], {
      queryParams: { subjectId: this.subjectId,
        courseId: this.courseId
      },
    });
  }
  moveChapter(){
    this.router.navigate(['/course/course0101'], {
      queryParams: {
        chapterId: this.chapterId,
        subjectId : this.subjectId,
        courseId: this.courseId
      },
    });
  }
  moveLecture(){
    this.router.navigate(['/course/course0101/lecture/'], {
      queryParams: {
        lectureId : this.lectureId,
        chapterId : this.chapterId,
        subjectId : this.subjectId,
        courseId : this.courseId
      },
    });
  }
  moveQuiz(){
    this.router.navigate(['/course/course0101/quizform'], {
      queryParams: {
        quizId: this.quizId,
        subjectId : this.subjectId,
        chapterId : this.chapterId,
        courseId: this.courseId
      },
    });
  }
}
