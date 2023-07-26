import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonService } from 'src/app/services/common/common.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';


@Component({
  selector: 'app-quiz-view-submitted',
  templateUrl: './quiz-view-submitted.component.html',
  styleUrls: ['./quiz-view-submitted.component.css']
})
export class QuizViewSubmittedComponent implements OnInit {
  time : any;
  hour : any = 0;
  minute: any = 0;
  second : any = 0;

  display: any;
  percent: any = 100;

  totalCorrect = 0;
  totalAnswer = 0;
  submit = false;
  totalCorrectAnswer = 0;
  totalWrongAnswer = 0;
  totalNotChoose = 0;
  quizId: any = "";
  quiz: any;
  listClass: any[] = [];
  listClassSelected: any[] = [];
  listQuizItem: any[] = [];
  listStuSubmited: any[] = [];
  listStuSubmitedFilter: any[] = [];
  stuIdxSelected : any;
  userInfo : any = {
    userType: ''
  };
  quizItemCurrent: any = {
    chooseA : false,
    chooseC : false,
    chooseB : false,
    chooseD : false,
  };
  quizItemIndexCurrent : any;

  studentSubmitSearch: any = {
    fullName : "",
    classIds : "",
    quizId : null
  }

  constructor(
    private route : ActivatedRoute,
    private quizService: QuizService,
    private toastrService: ToastrService,
    public dialog: MatDialog,
    private commonService: CommonService,
    private edu0201Service: Edu0201Service,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    @Inject(MAT_DIALOG_DATA) public mat_data: any,
  ) { }

  ngOnInit(){

    if(this.mat_data.student){
      const student = this.mat_data.student;
      this.getQuizSubmit(student.quizId, student.studentId); //student info id
      this.submit = true;
      return;
    }
    this.getListClass();

    this.route.queryParams.subscribe(async (params) => {
      this.quizId = params['quizId'];
    });

    this.studentSubmitSearch.quizId = this.quizId;

    this.commonService.getUserInfo().subscribe(
      (response) =>{
        this.userInfo = response;
        if(this.userInfo.userType != 'STU'){
          this.getQuiz();
          //this.getListStuSubtmited();
        }else{
          this.getQuizSubmit(this.quizId, this.userInfo.userInfoId); //student info id
        }
      }
    );
  }

  async getQuiz(){
    await this.quizService.getQuizById(this.quizId).toPromise().then((response) => {
      this.quiz = response[CommonConstant.DETAIL_KEY];
      this.listQuizItem = this.quiz.listQuizItem;

      // this.quizItemCurrent = this.listQuizItem[0];
    });
  }

  getQuizSubmit(quizId: any, userInfoId: any){
    var param = {
      quizId: quizId,
      userInfoId: userInfoId
    }
    this.quizService.getQuizSubmit(param).subscribe((response) => {
      this.quiz = response[CommonConstant.DETAIL_KEY];
      if(this.quiz.listQuizItemResult){
        this.listQuizItem = this.quiz.listQuizItemResult;
        this.submit = true;
      }

      this.totalCorrectAnswer = this.quiz.totalCorrectAnswer;
      this.totalWrongAnswer = this.quiz.totalWrongAnswer;
      this.totalNotChoose = this.quiz.totalNotChoose;

      // this.quizItemCurrent = this.listQuizItem[0];
    });
  }


  getListClass() {
    this.edu0201Service.search({}).subscribe((response) => {
      this.listClass = response.list;
    });
  }
  onCancel(){

  }
}
