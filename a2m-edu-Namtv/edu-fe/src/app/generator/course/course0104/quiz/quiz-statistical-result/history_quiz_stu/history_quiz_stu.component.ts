import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Quiz } from 'src/app/model/course/quiz';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { QuizViewSubmittedStuComponent } from '../../quiz-view-submittedStu/quiz-view-submittedStu.component';


@Component({
  selector: 'app-history_quiz_stu',
  templateUrl: './history_quiz_stu.component.html',
  styleUrls: ['./history_quiz_stu.component.css']
})
export class HistoryQuizStudentComponent implements OnInit {

  pageRequest = new Quiz();
  userInfo: any = {};
  courseId: any;
  subjectId: any;
  chapterId: any;
  quizId: any;
  listHisStu: any[] = [];
  totalHis = 0;
  studentSubmitSearch: any = {
    studentInfoId : NaN,
    quizId : NaN
  }
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

      this.courseId = params['courseId'];
      this.subjectId = params['subjectId'];
      this.chapterId = params['chapterId'];
      this.quizId = params['quizId'];
      this.studentSubmitSearch.quizId = this.quizId;
    });
    this.getUserInfo();

  }

  getUserInfo(){
    this.commonService.getUserInfo().toPromise().then(res=>{
      this.userInfo = res;
      this.studentSubmitSearch.studentInfoId = this.userInfo.userInfoId;
      this.initData();
    });

  }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();

  }

  showAddForm() {
    this.router.navigate(['/course/course0101/quizform'], {
      queryParams: {
        quizId : '',
        courseId: this.courseId
      },
    });
  }

  showUpdateForm(quizStudentId: any, event:any) {
    const dialogRef = this.dialog.open(QuizViewSubmittedStuComponent, {
      data: {
        quizStuId: quizStudentId,
      },

    });
    dialogRef.afterClosed().subscribe(result => {

    });
  }
  getListHis(){
    let param = Object.assign(this.studentSubmitSearch, this.pageRequest)
    this.quizService.getListHisQuizStuSubtmited(param).subscribe((response) => {
      this.listHisStu = response[CommonConstant.LIST_KEY];
      this.totalHis = response[CommonConstant.COUNT_ITEMS_KEY];
      CommonUtil.addIndexForListReverse(this.listHisStu, this.pageRequest.page, this.totalHis);
      this.listHisStu.forEach((ele:any) =>{
        // ele.insDate = new Date(ele.insDate);
        ele.totalQuestion = ele.totalCorrect + ele.totalNotChoose + ele.totalWrong;
        ele.marks = Math.floor(10/ele.totalQuestion)*ele.totalCorrect;
      })

    });
  }

  retakeQuiz(){
    this.router.navigate(['/course/course0201Quiz'], {
      queryParams: {
        quizId: this.quizId,
        courseId : this.courseId
      },
    });
  }

  backSubject(){
    let data = {
      subjectId : this.subjectId,
      chapterId: this.chapterId,
      docId: this.quizId,
    }
    this.commonService.changeBackCourse(data);
    this.router.navigate(['/course/course0201Preview'], {
      queryParams: { courseId: this.courseId},
    });
  }

  onSearch(){
    this.getListHis();
  }

  initData() {
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
    this.onSearch()
  }

}
