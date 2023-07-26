import { ToastrService } from 'ngx-toastr';

import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  Inject,
  Input,
} from '@angular/core';

import { DatePipe, formatDate } from '@angular/common';
import { Course } from 'src/app/model/sys/Course';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { Quiz } from 'src/app/model/course/quiz';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-quiz-form',
  templateUrl: './quiz-form.component.html',
  styleUrls: ['./quiz-form.component.css']
})
export class QuizFormComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  quizTypes: any;
  quizItemTypes: any;
  submitted: any = false;
  quiz: any = {};
  courseId: any;
  subjectId: any;
  chapterId: any;
  quizId: any;
  pdfFilePath : any = {};

  listQuizItem: any[] =[];
  listCorrectAnswer: any[]= [
    {
      label: "đáp án A",
      value: "A"
    },
    {
      label: "đáp án B",
      value: "B"
    },
    {
      label: "đáp án C",
      value: "C"
    },
    {
      label: "đáp án D",
      value: "D"
    },
  ]

  constructor(
    private tccoStdService: TccoStdService,
    private quizService: QuizService,
    private toastrService: ToastrService,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private router: Router,
    private fileUploadService :FileUploadService,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
  ) {}

  ngOnInit(): void {

    this.getQuizTypes();
    this.getQuizItemTypes();

    this.route.queryParams.subscribe(async (params) => {
      this.courseId = params['courseId']?params['courseId']:"";
      this.subjectId = params['subjectId']?params['subjectId']:"";
      this.chapterId = params['chapterId']?params['chapterId']:"";
      this.quizId = params['quizId']?params['quizId']:"";
    });

    if (this.quizId) {
      this.initData();
    } else {
      this.quiz = new Quiz();
      this.quiz.status = true;
      this.quiz.chapterId = this.chapterId;
      this.quiz.subjectId = this.subjectId;
      this.quiz.courseId = this.courseId;
      this.quiz.listQuizItem = this.listQuizItem;

      //demodata
      // this.listQuizItem.push({
      //   "answerCorrect": [],
      //   "listAnswer": [
      //       {
      //           "answerContent": "buồn",
      //           "isAnswerCorrect": true
      //       },
      //       {
      //           "answerContent": "không buồn",
      //           "isAnswerCorrect": false
      //       },
      //       {
      //           "answerContent": "vui",
      //           "isAnswerCorrect": true
      //       }
      //   ],
      //   "listAnswerCorrect": [
      //       {
      //           "index": 0,
      //           "label": "Answer 1"
      //       },
      //       {
      //           "index": 2,
      //           "label": "Answer 3"
      //       }
      //   ],
      //   "crudType": "C",
      //   "answerA": "",
      //   "answerB": "",
      //   "answerC": "",
      //   "answerD": "",
      //   "quizItemContent": "hôm nay tôi bùn",
      //   "quizItemType": "08-01"
      // })
      //demodata end
    }
  }

  initData(){
    this.quizService.getQuizById(this.quizId).subscribe((response) => {
      this.quiz = response[CommonConstant.DETAIL_KEY];
      this.listQuizItem = this.quiz.listQuizItem;
      this.migrateAnswerCorrect(this.listQuizItem);
      this.genIndexQuestion();
    });
  }

  genIndexQuestion(){
    let index = 0;
    this.listQuizItem.forEach(((question: any)=>{
      if(question.crudType != 'D'){
        index++;
        question.index = index;
      }
    }))
  }

  migrateAnswerCorrect(listQuiz: any){
    let label = this.trans.instant('course0104.quiz.quizDetail.optionAnswer')
    listQuiz.forEach((quiz: any) => {
      quiz.listAnswerCorrect.forEach((answer: any) => {
            answer.label = label+answer.label;
      });
    });
  }

  importExcel(event: any){
    const fileExcel = event.target.files[0];
    this.quizService.importExcel(fileExcel).subscribe({
      next: (response) =>{
        let listItemResponse = response[CommonConstant.LIST_KEY];
        this.migrateAnswerCorrect(listItemResponse);
        this.listQuizItem.push(...listItemResponse);
        this.toastrService.success('Success', '  Imported !');
      },
      error: ()=>{
        this.toastrService.error('Failed', 'Imported  Failed !');
      }
    })
  }

  saved(quizId: any) {
    this.router.navigate(['/course/course0101/quizform'], {
      queryParams: {
        quizId : quizId,
        chapterId : this.chapterId,
        subjectId: this.subjectId,
        courseId: this.courseId
      },
    });
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    }

    if(this.validQuestion()){
      return;
    }

    if (!this.quiz.key) {
      //create
      this.quizService.save(this.quiz).subscribe({
        next: (response) => {
          this.saved(response[CommonConstant.KEY]);
          this.quiz.key = response[CommonConstant.KEY];
          this.quizId = this.quiz.key
          this.initData();
          //this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.handleAfterSaveSuccess(this.trans.instant('message.success.saveSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    }else{
      //update
      this.quizService.save(this.quiz).subscribe({
        next: (response) => {
          this.initData();
          //this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
          this.handleAfterSaveSuccess(this.trans.instant('message.success.updateSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.success.updateFailed'));
        },
      });
    }
  }

  handleAfterSaveSuccess(mes: any) {
    this.confirmationService.confirm({
      header: mes,
      message: this.trans.instant('message.confirm.goListScreen'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	  rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.goList()
      },
      reject: () => {

        return
      },
    });
  }

  goList(){
    this.router.navigate(['/course/course0101'], {
      queryParams: {
        chapterId: this.chapterId,
        subjectId : this.subjectId,
        courseId: this.courseId,
        tab: 3
      },
    });
  }

  validQuestion(){
    for(let index: number = 0; index < this.quiz.listQuizItem.length; index ++){
      const quizItem = this.quiz.listQuizItem[index];
      // if(
      //   (
      //     quizItem.answerA.trim() == '' ||
      //     quizItem.answerB.trim() == '' ||
      //     quizItem.answerC.trim() == '' ||
      //     quizItem.answerD.trim() == '' ||
      //     quizItem.quizItemContent.trim() == '' ||
      //     quizItem.answerCorrect.length == 0
      //   ) && quizItem.quizItemType == "08-03"

      // ) {
      //   this.toastrService.error(this.trans.instant('course0104.quiz.quizDetail.headerValidQuiz') +(quizItem.index));
      //    let mes = this.trans.instant('course0104.quiz.quizDetail.headerValidQuiz') +(quizItem.index);
       //   this.popupValidQuestion(mes)
      //   return true;
      // }

      if(
        (
          quizItem.quizItemContent.trim() == ''
        ) && quizItem.quizItemType == "08-02"

      ) {
        //this.toastrService.error(this.trans.instant('course0104.quiz.quizDetail.headerValidQuiz') +(quizItem.index));
        let mes = this.trans.instant('course0104.quiz.quizDetail.headerValidQuiz') +(quizItem.index);
        this.popupValidQuestion(mes, index)
        return true;
      }


      if(
        quizItem.quizItemType == "08-01"
      ){

        if(quizItem.quizItemContent.trim() == ''){
          //this.toastrService.error(this.trans.instant('course0104.quiz.quizDetail.headerValidQuiz') +(quizItem.index));
          let mes = this.trans.instant('course0104.quiz.quizDetail.headerValidQuiz') +(quizItem.index);
          this.popupValidQuestion(mes, index)
          return true;
        }

        var flag = false;
        for(let i = 0; i < quizItem.listAnswer.length ; i++){
          if( quizItem.listAnswer[i].answerContent.trim()==''){
            flag = true;
            break;
          }
        }
        if(flag || quizItem.listAnswerCorrect.length == 0){
          //this.toastrService.error(this.trans.instant('course0104.quiz.quizDetail.headerValidQuiz') +(quizItem.index));
          let mes = this.trans.instant('course0104.quiz.quizDetail.headerValidQuiz') +(quizItem.index);
          this.popupValidQuestion(mes, index)
          return true;
        }
      }
    }

    if(this.quiz.listQuizItem.length == 0){
      let mes = this.trans.instant('course0104.quiz.quizDetail.mustHaveOne');
          this.popupValidQuestion2(mes)
      return true;
    }
    return false;
  }

  popupValidQuestion2(mes: any) {
    this.confirmationService.confirm({
      header: mes,
      message: this.trans.instant('button.add.title'),
      acceptLabel: this.trans.instant('message.confirm.title'),
      acceptVisible: true,
      accept: () => {
        this.addNewQuestion(0);
      },
      reject: () => {

      },
    });
  }

  popupValidQuestion(mes: any, questionIndex: any) {
    this.confirmationService.confirm({
      header: mes,
      message: this.trans.instant('course0104.quiz.quizDetail.contentValidQuiz'),
      acceptLabel: this.trans.instant('message.confirm.title'),
      acceptVisible: true,
      accept: () => {

        let targetEle = document.getElementById("quizItem"+questionIndex);
        if(targetEle){
          let pos = targetEle.style.position;
          let top = targetEle.style.top;
          targetEle.style.position = 'relative';
          targetEle.style.top = '-200px';
          targetEle.scrollIntoView({behavior: 'smooth', block: 'start'});
          targetEle.style.top = top;
          targetEle.style.position = pos;
        }

      },
      reject: () => {

      },
    });
  }

  exportPDF(quizId : any){
    this.quizService.exportPDF(quizId).toPromise().then((response)=>{
      const myArray = response.detail.split("!#@");
      this.pdfFilePath.fleNm = myArray[1];
      this.pdfFilePath.fleTp = '.zip';
      this.pdfFilePath.flePath = myArray[0]+this.pdfFilePath.fleTp;
      this.fileUploadService.download(this.pdfFilePath,true);
    })
  }

  getQuizTypes() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.QUIZ_TYPE_COMM_CD)
      .subscribe((response) => {
        this.quizTypes = response;
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  getQuizItemTypes() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.QUIZ_ITEM_TYPE_COMM_CD)
      .subscribe((response) => {
        this.quizItemTypes = response;
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  addNewQuestion(index: any){
    const newQuestion = new QuizItemContent();
    this.listQuizItem.push(newQuestion);
    this.genIndexQuestion();
  }

  removeQuestion(index: any){
    if(this.listQuizItem[index].crudType == "C"){
      this.listQuizItem.splice(index, 1);
    }else{
      this.listQuizItem[index].crudType = "D";
    }

    this.genIndexQuestion();
  }

  addAnswer(listAnswer: any[]){
    let newAnswer = new quizItemAnswer();

    listAnswer.push(newAnswer);
  }

  removeAnswer(listAnswer: any[] , index: any){
    listAnswer.splice(index, 1);
  }

  createMultiSelect(quizItem: any){
    let label = this.trans.instant('course0104.quiz.quizDetail.optionAnswer')
    var result: any[] = [];
    for(let i = 0 ; i< quizItem.listAnswer.length ; i++){
      var obj: any = {};
      obj.index = i;
      obj.label = label + (i+1);
      result.push(obj);
    }
    return result;
  }

  setCorrectAnswer(quizItem: any){
    quizItem.listAnswer.map((item: any)=>{
      item.isAnswerCorrect = false;
      return item;
    })

    for(let i = 0 ; i< quizItem.listAnswerCorrect.length ; i++){
      var indexChoose = quizItem.listAnswerCorrect[i].index;
      quizItem.listAnswer[indexChoose].isAnswerCorrect = true;
    }
  }
}

export class QuizItemContent{
  quizItemContent?: String;
  answerA?:  String;
  answerB?: String;
  answerC?: String;
  answerD?: String;
  answerCorrect: any[] = []; //default
  listAnswer: any[] = []; //multi choise flexible
  listAnswerCorrect: any[] = []; //multi choise flexible
  crudType?: String;
  quizItemType?: String;
  constructor(){
    this.crudType = "C";
    this.answerA = "";
    this.answerB = "";
    this.answerC = "";
    this.answerD = "";
    this.quizItemContent = "";
    this.answerCorrect = [];
    this.listAnswerCorrect = [];
    this.listAnswer = [];

    this.listAnswer.push(new quizItemAnswer())
    this.quizItemType = "08-01"; //multichoi
  }
}

export class quizItemAnswer{
  answerContent?: String;
  isAnswerCorrect?: boolean;
  constructor() {
    this.answerContent = "";
    this.isAnswerCorrect = false;
  }
}

