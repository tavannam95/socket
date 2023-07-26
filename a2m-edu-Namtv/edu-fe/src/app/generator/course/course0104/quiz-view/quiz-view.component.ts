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
  selector: 'app-quiz-view',
  templateUrl: './quiz-view.component.html',
  styleUrls: ['./quiz-view.component.css']
})
export class QuizViewComponent implements OnInit {
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

  getScaleCorrectByQuizItem(id: any){
    this.quizService.getScaleCorrectByQuizItem(id).subscribe(
      (response) => {
        this.totalCorrect = response[CommonConstant.DETAIL_KEY].correct;
        this.totalAnswer = response[CommonConstant.DETAIL_KEY].total;
    });
  }

  async getQuiz(){
    await this.quizService.getQuizById(this.quizId).toPromise().then((response) => {
      this.quiz = response[CommonConstant.DETAIL_KEY];
      this.listQuizItem = this.quiz.listQuizItem;

      this.quizItemCurrent = this.listQuizItem[0];
      this.goQuestion(0);
      this.startQuiz();
    });
  }

  getListStuSubtmited(){
    this.listClassSelected.forEach((item: any, index : number) => {
      if(index == 0){
        this.studentSubmitSearch.classIds = item.key + ", "
      }else if(index > 0){
        this.studentSubmitSearch.classIds += item.key + ", "
      }
      if(index == this.listClassSelected.length-1){
        this.studentSubmitSearch.classIds = this.studentSubmitSearch.classIds.substring(0, this.studentSubmitSearch.classIds.length-2)
      }
    });

    this.quizService.getListStuSubtmited(this.studentSubmitSearch).subscribe((response) => {
      this.listStuSubmited = response[CommonConstant.LIST_KEY];
      this.listStuSubmitedFilter = this.listStuSubmited;

      if(this.listStuSubmited.length>0){
        var initStu = this.listStuSubmited[0];
        this.gotoStudent(initStu, 0);
      }

    });
  }

  gotoStudent(student: any, stuIdx: any){
    this.stuIdxSelected = stuIdx;
    this.getQuizSubmit(this.quizId, student.studentId);
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
      }else{

        this.listQuizItem = this.quiz.listQuizItem;
        this.startQuiz()
      }

      this.totalCorrectAnswer = this.quiz.totalCorrectAnswer;
      this.totalWrongAnswer = this.quiz.totalWrongAnswer;
      this.totalNotChoose = this.quiz.totalNotChoose;

      this.quizItemCurrent = this.listQuizItem[0];
      this.goQuestion(0);
    });
  }


  getListClass() {
    this.edu0201Service.search({}).subscribe((response) => {
      this.listClass = response.list;
    });
  }

  buttonSubmit(){
    this.confirmationService.confirm({
      header: this.trans.instant('message.headerSubmitTest'),
      message: this.trans.instant('message.submitTest'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	    rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.doSubmit();
      },
      reject: () => {

        return
      },
    });
  }

  doSubmit(){
    this.submit = true;
    this.totalCorrectAnswer = 0;
    this.totalWrongAnswer = 0;
    this.totalNotChoose = 0;
    this.listQuizItem.forEach((quizItem: any) => {
      if(quizItem.quizItemType == "08-03"){
        quizItem.correctA = false;
        quizItem.correctB = false;
        quizItem.correctC = false;
        quizItem.correctD = false;
        quizItem.answerCorrect.forEach((answer: any) => {
          if(answer.value == "A") quizItem.correctA = true;
          if(answer.value == "B") quizItem.correctB = true;
          if(answer.value == "C") quizItem.correctC = true;
          if(answer.value == "D") quizItem.correctD = true;
        });

        if(!quizItem.chooseA) quizItem.chooseA = false;
        if(!quizItem.chooseB) quizItem.chooseB = false;
        if(!quizItem.chooseC) quizItem.chooseC = false;
        if(!quizItem.chooseD) quizItem.chooseD = false;

        if(quizItem.correctA == quizItem.chooseA && quizItem.correctB == quizItem.chooseB && quizItem.correctC == quizItem.chooseC && quizItem.correctD == quizItem.chooseD){
          this.totalCorrectAnswer++;
          quizItem.result = 'correct';
        }else if ( (quizItem.chooseA || quizItem.chooseB|| quizItem.chooseC || quizItem.chooseD) ){
          this.totalWrongAnswer++;
          quizItem.result = 'wrong';
        }else{
          this.totalNotChoose++;
          quizItem.result = 'notchoose';
        }
      }
      if(quizItem.quizItemType == "08-01"){
        var listAnswer = quizItem.listAnswer;
        var flag = true;
        var notchoose = false;
        listAnswer.forEach((answer: any) => {
          if(answer.isAnswerCorrect != answer.isChoose){
            flag = false;
          }
          if(answer.isChoose){
            notchoose = true;
          }
        });

        if(!notchoose){
          this.totalNotChoose++;
          quizItem.result = 'notchoose';
        }else if(flag){
          this.totalCorrectAnswer++;
          quizItem.result = 'correct';
        }else {
          this.totalWrongAnswer++;
          quizItem.result = 'wrong';
        }
      }
      if(quizItem.quizItemType == "08-02"){
        if(quizItem.answerCorrectString.trim() != "" ){
          this.totalCorrectAnswer++;
          quizItem.result = 'correct';
        }else{
          this.totalNotChoose++;
          quizItem.result = 'notchoose';
        }
      }

    });

    var correctMes = this.trans.instant('course0104.quiz.quizDetail.correct');
    var wrongMes = this.trans.instant('course0104.quiz.quizDetail.wrong');
    var notChooseMes = this.trans.instant('course0104.quiz.quizDetail.notChoose');


    var messages = correctMes + this.totalCorrectAnswer + wrongMes +this.totalWrongAnswer+ notChooseMes +this.totalNotChoose

    var submitRequest = this.createSubmitRequest();
    this.quizService.submit(submitRequest).subscribe({
      next: (response) =>{

        this.handleSubmitResponse(response, messages);
      },
      error: () =>{

      }
    })
  }

  handleSubmitResponse(response: any, messages: any) {

    this.confirmationService.confirm({
      header: this.trans.instant('course0104.quiz.quizDetail.congratulate'),
      message: this.trans.instant(messages),
      rejectLabel: this.trans.instant('course0104.quiz.quizDetail.doAgain'),
      acceptLabel: this.trans.instant('course0104.quiz.quizDetail.confirm'),
      rejectVisible: false,
      accept: () => {

      },
      reject: () => {

      },
      key: "resultDialogQuiz"
    });
  }

  createSubmitRequest(){
    this.listQuizItem = this.listQuizItem.map((item: any)=>{
      item.quizItemId = item.key;
      return item;
    })
    var submitRequest = {
      totalCorrectAnswer: this.totalCorrectAnswer,
      totalWrongAnswer: this.totalWrongAnswer,
      totalNotChoose: this.totalNotChoose,
      studentInfoId: null,
      listQuizItemResult: this.listQuizItem,
      quizId: this.quiz.key,
      listQuizItem: this.listQuizItem,
    };

    return submitRequest;
  }

  doAgain(){
    this.confirmationService.confirm({
      header: this.trans.instant('course0104.quiz.quizDetail.doAgain'),
      message: this.trans.instant('course0104.quiz.quizDetail.doAgain'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	    rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.acceptDoAgain();
      },
      reject: () => {

      },
    });
  }

  async acceptDoAgain(){
    this.submit = false;
    await this.getQuiz();
    this.startQuiz();
  }

  interval: ReturnType<typeof setTimeout> | undefined;
  startQuiz(){
    this.time = this.quiz.quizTime*60;
    this.interval = setInterval(() => {

      var tmpTime = this.time;
      this.hour = Math.floor( tmpTime / 3600 );
      tmpTime = tmpTime - this.hour *3600
      this.minute = Math.floor( tmpTime / 60 );
      this.second = Math.floor( tmpTime  % 60 );

      this.time --;
      if(this.time <= -1){
        clearInterval(this.interval);
        if(this.submit == false){
          this.doSubmit();
        }
      }
      if(this.submit == true){
        clearInterval(this.interval);
      }

    }, 1000);
  }

  goQuestion(index: any){
    //next
    this.quizItemCurrent = this.listQuizItem[index];
    this.quizItemIndexCurrent = index;

    if(this.submit){
      this.getScaleCorrectByQuizItem(this.quizItemCurrent.quizItemId);
    }
  }

  chageAnswer0801(quizItemCurrent: any){
    var listAnswer = quizItemCurrent.listAnswer;
    var flag = true;
    var checked = false;
    listAnswer.forEach((answer: any) => {
      if(answer.isAnswerCorrect != answer.isChoose){
        flag = false;
      }
      if(answer.isChoose){
        checked = true;
      }
    });
    quizItemCurrent.checked = checked;
  }

  onCancel(){

  }
}
