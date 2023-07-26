import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { Quiz } from 'src/app/model/course/quiz';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';
import {CdkDragDrop, DragDropModule, moveItemInArray} from '@angular/cdk/drag-drop';


@Component({
  selector: 'app-quiz-list',
  templateUrl: './quiz-list.component.html',
  styleUrls: ['./quiz-list.component.css']
})
export class QuizListComponent implements OnInit {

  pageRequest = new Quiz();
  chapterId : any;
  chapter : any = {};
  subjectId: any;
  courseId: any;
  listDocument: any[] = [];
  listQuiz: any[] = [];
  totalChapter = 0;
  listTypeDoc: any[] = [];
  typeView : any;
  checkView : any = true;
  submitted: any = false;
  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  constructor(
    private quizService: QuizService,
    private course0103Service: Course0103Service,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    private route : ActivatedRoute,
    private commonService : CommonService,
    private tccoStdService: TccoStdService,
  ){ }

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.route.queryParams.subscribe(async (params) => {
      this.chapterId = params['chapterId']?params['chapterId']:"";
      this.subjectId = params['subjectId']?params['subjectId']:"";
      this.courseId  = params['courseId']?params['courseId']:"";
    });
    this.initData();
    this.onSearch();
    if(this.chapterId){
      this.getchapterById();
    }
    this.typeView = 'VIEW';

  }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.pageRequest.chapterId = this.chapterId;
    this.onSearch();

  }

  delete(quiz: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.quizService.delete(quiz.key).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.onSearch();
            } else {
              this.toastr.error(
                this.trans.instant('message.error.deleteFailed')
              );
            }
          },
          error: () => {
            this.toastr.error(
              this.trans.instant('message.error.deleteFailed')
            );
          },
        });
      },
      reject: () => {},
    });
  }

  showAddForm() {
    this.router.navigate(['/course/course0101/quizform'], {
      queryParams: {
        quizId : '',
        subjectId : this.subjectId,
        chapterId : this.chapterId,
        courseId: this.courseId
      },
    });
  }

  showUpdateForm(ele: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I'){
      if(ele.typeDocument == 'Quiz'){
        this.router.navigate(['/course/course0101/quizform'], {
          queryParams: {
            quizId: ele.key,
            subjectId : this.subjectId,
            chapterId : this.chapterId,
            courseId: this.courseId
          },
        });
      }else{
        this.router.navigate(['/course/course0101/lecture/'], {
          queryParams: {
            lectureId : ele.key,
            chapterId : this.chapterId,
            subjectId : this.subjectId,
            courseId : this.courseId
          },
        });
      }

    }


  }
  getchapterById(){
    this.listDocument = [];
    this.course0103Service.getChapterById(this.chapterId).subscribe((response) => {
      this.chapter = response[CommonConstant.DETAIL_KEY];
      let listLecture = this.chapter.lectureModels;
      this.listQuiz = this.chapter.listQuiz;
      if(listLecture.length > 0) {
        listLecture.forEach((element:any) =>{

          this.listDocument.push(element);
        });
      }
      if(this.listQuiz.length > 0){
        this.listQuiz.forEach((element:any) =>{
          element.typeDocument = 'Quiz';
          this.listDocument.push(element);
        });
      }
      this.listDocument.sort((a, b) => a.ordNo - b.ordNo);
      CommonUtil.addIndexForList(this.listDocument);
      this.initCheckView();

      // var startDate = this.chapter.startDt? new Date(this.chapter.startDt): null;
      // var endDate = this.chapter.endDt? new Date(this.chapter.endDt): null;
      // this.chapter.startDt = startDate;
      // this.chapter.endDt = endDate;
      // this.chapter.subjectId = this.subjectId;

      // this.initCheckView();
    });
  }

  initCheckView(){
    if(this.listDocument[0].insUid != null){
      if(this.commonService.permModify(this.listDocument[0].insUid, true)){
        this.checkView = false;
      }else{
        this.checkView = true;
      }
    }
  }

  showUpdateDocumentForm(){
    this.typeView = 'MODIF';
    //this.getSubjectHistory(this.subjectId);
  }

  changedRow(rowIndex: any){
    this.listDocument[rowIndex].state = 'U';
  }

  showAddFormDocument(){
    if(this.chapterId){
      this.router.navigate(['/course/course0101/lecture/'], {
        queryParams: { lectureId : '', chapterId : this.chapterId, subjectId : this.subjectId, courseId : this.courseId },
      });
    }else {
      alert("Create A Chapter !");
    }
  }

  onSearch(){
   this.getQuizByChapter();
  }

  getQuizByChapter(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.pageRequest.chapterId = this.chapterId;
    this.quizService.getQuizByChapter(this.pageRequest).subscribe((response) => {
      this.listQuiz = response[CommonConstant.LIST_KEY];
      this.totalChapter = response[CommonConstant.COUNT_ITEMS_KEY];
      CommonUtil.addIndexForListReverse(this.listQuiz, this.pageRequest.page, this.totalChapter);
      dialogSpinner.close();
    });
  }

  onSearchReset(){
    this.pageRequest = new Quiz();
    this.initData();
    this.onSearch();
  }

  initData() {
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
    this.getTypeDocumentByCommCode();
  }

  async getTypeDocumentByCommCode() {
   await this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.TYPE_DOCUMENT)
      .toPromise().then((response) => {
          this.listTypeDoc = response;
          this.commonService.selectLangComcode(this.listTypeDoc);

        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  getTypeDocNm(typeDocument : any){

    const commCode = this.listTypeDoc.find(
      (element) => element.commCd == typeDocument
    );
      if(!commCode) return "";
    return commCode.label;

  }

  SaveOrdNo() {

      // this.lecture.startDt = this.datepipe.transform(this.lecture.startDt, 'yyyy-MM-dd');
      // this.lecture.endDt = this.datepipe.transform(this.lecture.endDt, 'yyyy-MM-dd');

      this.course0103Service.saveOrdNo(this.chapter).subscribe({
        next: (response) => {
          // this.getchapterById();
          this.init();
        },
        error: () => {
          this.toastr.error(this.trans.instant('message.success.updateFailed'));
        },
      });
  }
  drop(event: any) {
    moveItemInArray(this.listDocument, event.previousIndex, event.currentIndex);
  }
}
