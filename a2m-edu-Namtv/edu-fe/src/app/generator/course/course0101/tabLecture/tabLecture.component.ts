import { AfterContentInit, AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { CommonService } from 'src/app/services/common/common.service';
import { ChapterFormComponent } from '../../course0103/component/chapter-form/chapter-form.component';
import { QuizListComponent } from '../../course0104/quiz/quiz-list/quiz-list.component';
import { ListLectureComponent } from '../list-lecture/list-lecture.component';

@Component({
  selector: 'app-tabLecture',
  templateUrl: './tabLecture.component.html',
  styleUrls: ['./tabLecture.component.css']
})
export class TabLectureComponent implements OnInit,AfterViewInit {
  courseId:any;
  subjectId:any;
  chapterId:any;
  oldChapterId :any;
  showHide : any= '';

  @ViewChild('chapterForm')
  chapterForm!: ChapterFormComponent

  @ViewChild('lectureList')
  lectureList!: ListLectureComponent

  @ViewChild('quizList')
  quizList!: QuizListComponent

  constructor(
    private route : ActivatedRoute,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private commonService : CommonService,
) { }
  ngAfterViewInit(): void {
    this.route.queryParams.subscribe( (params) => {
      if(this.oldChapterId!=undefined && this.oldChapterId!=params['chapterId']){
        this.chapterForm.initData(this.chapterId);
        this.lectureList.init();
        this.quizList.init();
      }
      const tab = params['tab'];
      if(tab){
        this.changeTab(tab)
      }
  });
  }

   ngOnInit() {
    this.route.queryParams.subscribe( (params) => {
      this.oldChapterId = this.chapterId ;
      this.courseId = params['courseId'];
      this.subjectId = params['subjectId'];
      this.chapterId = params['chapterId'];

      // this.commonService.changeCourse(this.courseId);
    });
  }
  // initData(){
  //   this.courseForm.initData();
  // }

  getBehaviorSubject(){
    this.commonService.backCourse$.subscribe((response: any) => {
      const tabNumber = response.tabNumber;
      this.changeTab(tabNumber)
    });
  }

  changeTab(tabNumber: any){
    if(this.chapterId==""){
      let mes = this.trans.instant('chapter.info.init.required');
      this.confirmationService.confirm({
        header: mes,
        acceptLabel: this.trans.instant('button.confirm.title'),
        accept: () => {
          // tabNumber=1;
        },

      });
      tabNumber=1;
    }
    const ele = document.getElementById("tabLecture"+tabNumber);
    ele?.click();
    // switch(tabNumber){
    //   case 1:
    //     this.chapterForm.ngOnInit();
    //     break;

    //   case 2:
    //     this.lectureList.ngOnInit();
    //     break;
    //   case 3:
    //     this.quizList.ngOnInit();
    //     break;
    //   case 4:
    //     this.iqList.ngOnInit();
    //     break;
    // }
  }
}
