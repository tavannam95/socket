import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SuccessfullComponent } from 'src/app/generator/commons/modal/successfull/successfull.component';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { IqTestService } from 'src/app/services/quesIq/iqTest.service';
import { Cookie } from 'ng2-cookies';
import { ProgressComponent } from './progress/progress.component';

@Component({
  selector: 'app-iqTest-view',
  templateUrl: './iqTest-view.component.html',
  styleUrls: ['./iqTest-view.component.css']
})
export class IqTestViewComponent implements OnInit, OnDestroy {

  pages: any;
  rows: any;
  totalQues : any;
  iqTestId: any = "";
  iqTest: any;
  listQues: any[] = [];
  listQuesPaging: any[] = [];
  submit = false;
  totalCorrectAnswer = 0;
  totalWrongAnswer = 0;
  totalNotChoose = 0;
  listClass: any[] = [];
  listClassSelected: any[] = [];
  listStuSubmited: any[] = [];
  listStuSubmitedFilter: any[] = [];
  stuIdxSelected : any;
  userInfo : any = {
    userType: ''
  };
  changeIndex : any;
  tsstUser: any;
  userUid: any;
  listUser : any;
  userId : any;
  userType : any;

  listQuesArea: any;
  AreaId : any;

  display: any;
  percent: any = 100;
  timerInterval: ReturnType<typeof setTimeout> | undefined;
  iqItemCurrent: any = {};
  quesIndexCurrent : any;
  quesContents: any;
  idx : any = "idx";
  nonUser : any;
  nonUserId : any;
  constructor(
    private route : ActivatedRoute,
    private iqTestService: IqTestService,
    private toastrService: ToastrService,
    public dialog: MatDialog,
    private trans: TranslateService,
    private commonService: CommonService,
    private confirmationService: ConfirmationService,
  ) { }

  ngOnInit(){
    this.route.queryParams.subscribe(async (params) => {
      this.iqTestId = params['iqTestId']?params['iqTestId']:"";
    });
    this.commonService.getUserInfo().subscribe(
      (response) =>{
        this.userInfo = response;
        this.userUid = this.userInfo.userUid;
        this.userType = this.userInfo.userType;
        // if(this.userType == 'TEA'){
        //   this.createNewUser(this.iqTestId, this.userInfo.userInfoId);
        // }
        // if(this.userType != 'TEA'){
          this.getIqTestSubmit(this.iqTestId, this.userInfo.userInfoId);
        // }
      }
    );
  }
  ngOnDestroy() {
    clearInterval(this.timerInterval);
  }
  shuffle(listQues: any) {
    let currentIndex = listQues.length,  randomIndex;

    while (currentIndex != 0) {

      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex--;

      [listQues[currentIndex], listQues[randomIndex]] = [
        listQues[randomIndex], listQues[currentIndex]];
    }

    return listQues;
  }

  getIqTestSubmit(iqTestId: any, userInfoId: any){
    var param = {
      iqTestId: iqTestId,
      userInfoId: userInfoId,
    }
    this.iqTestService.getIqTestSubmit(param).subscribe((response) => {
      this.iqTest = response[CommonConstant.DETAIL_KEY];
      this.listUser = this.iqTest.listUser;
      if(this.listUser.length != 0){
        for(let i = 0; i < this.listUser.length;i++){
          this.tsstUser = this.listUser[i].tsstUserModel;
          if(this.tsstUser != null){
            this.userId = this.tsstUser.userUid;
            if(this.userId == this.userUid){
              this.listQues = this.iqTest.listQuesResult;
              this.submit = true;
            }else{
              this.listQues = this.iqTest.listQues;
              this.timer(parseInt(this.iqTest.iqTestTime));
              this.shuffle(this.listQues);
            }
          }else{
            this.listQues = this.iqTest.listQues;
            this.timer(parseInt(this.iqTest.iqTestTime));
            this.shuffle(this.listQues);
          }
        }
      }else{
        this.listQues = this.iqTest.listQues;
        this.timer(parseInt(this.iqTest.iqTestTime));
        this.shuffle(this.listQues);
      }
      this.totalCorrectAnswer = this.iqTest.totalCorrectAnswer;
      this.totalWrongAnswer = this.iqTest.totalWrongAnswer;
      this.totalNotChoose = this.iqTest.totalNotChoose;

      this.iqItemCurrent = this.listQues[0];
      this.goQuestion(0);
    });
  }

  Submit(){
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

  SubmitOverTime(){
    const dialogRef = this.dialog.open(ProgressComponent, {
      width: '0px',
      height: '0px',
      // data: { listChoose: list },
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {
      this.doSubmit();
    });
  }

  // SubmitOverTime(){
  //   this.confirmationService.confirm({
  //     header: this.trans.instant('message.headerSubmitTest'),
  //     // message: this.trans.instant('button.submit.title'),
  //     acceptLabel: this.trans.instant('button.confirm.title'),
	//     // rejectLabel: this.trans.instant('button.cancel.title'),
  //     accept: () => {
  //       this.doSubmit();
  //     },
  //   });
  // }

  doSubmit(){
    this.submit = true;
    this.totalCorrectAnswer = 0;
    this.totalWrongAnswer = 0;
    this.totalNotChoose = 0;
    this.listQues.forEach((ques: any) => {
      if(ques.quesType == "09-02"){
        var answerList = ques.answerList;
        var flag = true;
        var notchoose = false;
        answerList.forEach((answer: any) => {
          if(answer.answerCorrect != answer.isChoose){
            flag = false;
          }
          if(answer.isChoose){
            notchoose = true;
          }
        });

        if(!notchoose){
          this.totalNotChoose++;
          ques.result = 'notchoose';
        }else if(flag){
          this.totalCorrectAnswer++;
          ques.result = 'correct';
        }else {
          this.totalWrongAnswer++;
          ques.result = 'wrong';
        }
      }
      if(ques.quesType == "09-01"){
        // if(ques.answerCorrect != ques.answerCorrectString){
        //   flag = false;
        // }


        if(ques.answerCorrectString.trim() != "" ){

          // this.totalCorrectAnswer++;
          // ques.result = 'correct';

          let answerCorect = this.convertStringToNumber(ques.answerList[0].answerContent);
          let answer = this.convertStringToNumber(ques.answerCorrectString);
          let answerCorectStr = ques.answerList[0].answerContent.trim().toUpperCase();
          let answerStr = ques.answerCorrectString.trim().toUpperCase();
          let theSame = answerCorectStr.localeCompare(answerStr)
          if(answerCorect &&   answer && answerCorect!='NaN' && answer!='NaN' && answerCorect== answer || answerCorect && answerCorect=='NaN' && theSame==0 ){
                  this.totalCorrectAnswer++;
                  ques.result = 'correct';

          }else{
              this.totalWrongAnswer++;
              ques.result = 'wrong';

          }


        }else{
          this.totalNotChoose++;
          ques.result = 'notchoose';
        }
      }

    });

    var correctMes = this.trans.instant('course0104.quiz.quizDetail.correct');
    var wrongMes = this.trans.instant('course0104.quiz.quizDetail.wrong');
    var notChooseMes = this.trans.instant('course0104.quiz.quizDetail.notChoose');


    var messages = correctMes + this.totalCorrectAnswer + wrongMes +this.totalWrongAnswer+ notChooseMes +this.totalNotChoose

    var submitRequest = this.createSubmitRequest();
    this.iqTestService.submit(submitRequest).subscribe({
      next: (response) =>{
        clearInterval(this.timerInterval);
        this.handleSubmitResponse(response, messages);
      },
      error: () =>{

      }
    })
  }

  convertStringToNumber(number : String){
    let result : Number | undefined;
    if(number.includes('/')){
      let numerator = number.split('/')[0].trim();
      let denominator = number.split('/')[1].trim();
      result = Number(numerator) /Number(denominator);
      return result.toFixed(4) ;
    }else{
      return Number(number).toFixed(4)  ;
    }
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
      key: "resultDialog"
    });
  }

  createSubmitRequest(){
    this.listQues = this.listQues.map((item: any)=>{
      item.quesId = item.key;
      return item;
    })
    var submitRequest = {
      totalCorrectAnswer: this.totalCorrectAnswer,
      totalWrongAnswer: this.totalWrongAnswer,
      totalNotChoose: this.totalNotChoose,
      studentInfoId: null,
      listQuesResult: this.listQues,
      key: this.iqTestId,
      listQues: this.listQues,
      nonUserId: this.nonUserId,

    };

    return submitRequest;
  }


  goQuestion(index: any){

    this.iqItemCurrent = this.listQues[index];
    this.quesIndexCurrent = index;
    this.iqItemCurrent['index'] = index;
    this.quesContents = (this.quesIndexCurrent+1)+': ' + this.iqItemCurrent?.quesContent;
  }

  chageAnswer0801(iqItemCurrent: any){
    var answerList = iqItemCurrent.answerList;
    var flag = true;
    var checked = false;

    answerList.forEach((answer: any) => {
      if(answer.answerCorrect != answer.isChoose){
        flag = false;
      }
      if(answer.isChoose){
        checked = true;
      }
    });
    iqItemCurrent.checked = checked;
  }

  timer(minute : any) {
    let seconds = minute * 60;
    let hours = Math.floor(minute / 60);
    let textSec: any = '0';
    let statSec: number = seconds % 60;
    let textMin: any = '0';
    let showminute: number = (minute - (hours * 60));
    this.timerInterval = setInterval(() => {
      seconds--;
      this.percent = this.percent - this.percent/seconds;
      if (statSec != 0) {
        statSec--;
      }
      else {
        statSec = 59;
        if(showminute > 0){
          showminute -= 1
        }
        if(showminute == 0){
          if(hours > 0){
          hours -= 1;
          showminute = 59
          }
        }

      }

      if (statSec < 10) {
        textSec = '0' + statSec;
      } else textSec = statSec;
      const prefix = showminute >= 0 && showminute < 10 ? '0' : '';
      const hoursFix = hours < 10 ? '0' : '';
      this.display = `${hoursFix}${hours}:${prefix}${showminute}:${textSec}`;
      if (seconds == 0) {
        clearInterval(this.timerInterval);
        this.SubmitOverTime();
      }
    }, 1000);


  }

  // createNewUser(iqTestId:any, userInfoId:any){
  //   const dialogRef = this.dialog.open(CreateUserComponent, {
  //     width: '0px',
  //     height: '0px',
  //     //data: { listChooseSubject : "" },
  //     panelClass: 'custom-modalbox',
  //   });

  //   dialogRef.afterClosed().subscribe((response: any[]) => {
  //     if(response){
  //       this.nonUser = response;
  //       this.nonUserId = this.nonUser.key;
  //       this.getIqTestSubmit(iqTestId, userInfoId);
  //       //this.searchData();
  //     }
  //   });
  // }
}
