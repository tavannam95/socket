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
  selector: 'app-quiz-view-submittedStu',
  templateUrl: './quiz-view-submittedStu.component.html',
  styleUrls: ['./quiz-view-submittedStu.component.css']
})
export class QuizViewSubmittedStuComponent implements OnInit {
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
  quizStudentId : any;
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
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) { }

  ngOnInit(){
    if(this.data.quizStuId){
      this.quizStudentId = this.data.quizStuId;
    }
    this.route.queryParams.subscribe(async (params) => {
      this.quizId = params['quizId'];
    });

    this.studentSubmitSearch.quizId = this.quizId;
    this.commonService.getUserInfo().subscribe(
      (response) =>{
        this.userInfo = response;
        this.getQuizSubmit(this.quizId, this.userInfo.userInfoId); //student info id
      }
    );
  }

  getQuizSubmit(quizId: any, userInfoId: any){
    var param = {
      quizId: quizId,
      userInfoId: userInfoId,
      quizStudentId : this.quizStudentId
    }
    this.quizService.getQuizSubmitStu(param).subscribe((response) => {
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

  onCancel(){

  }
}
