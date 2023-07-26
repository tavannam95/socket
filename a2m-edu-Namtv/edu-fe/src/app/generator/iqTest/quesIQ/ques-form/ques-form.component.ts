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
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonService } from 'src/app/services/common/common.service';
import { TranslateService } from '@ngx-translate/core';
import { IqQuesService } from 'src/app/services/quesIq/iqQues.service';
import { IqQues } from 'src/app/model/iqTest/iqQues';

@Component({
  selector: 'app-ques-form',
  templateUrl: './ques-form.component.html',
  styleUrls: ['./ques-form.component.css']
})
export class IqQuesFormComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  configCkeditor: any = {};
  quesTypes: any = {};
  quesType : any;
  quesLevels: any = {};
  queslevel : any;
  quesCategorys : any = {};
  quesCategory : any

  submitted: any = false;
  ques: any = {};
  iq: any = {};
  courseId: any;
  subjectId: any;
  chapterId: any;
  iqTestId: any;
  index : any;
  listAnswerCorrect: any[] =[];
  answerList: any[] =[];
  AnswerDel: any;

  constructor(
    private tccoStdService: TccoStdService,
    private iqQuesService: IqQuesService,
    private toastrService: ToastrService,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private router: Router,
    private commonService: CommonService,
    private trans: TranslateService,
  ) {}

  ngOnInit(): void {
    this.getQuesCategory();
    this.getQuesLevel();
    this.getQuesTypes();

    this.initConfigCk();
    this.route.queryParams.subscribe(async (params) => {
      this.iqTestId = params['iqTestId']?params['iqTestId']:"";
    });

    if (this.iqTestId) {
      this.initData();
    } else {
      this.ques = new IqQues();
      this.ques.quesType = "09-01";
      this.ques.status = true;
      this.ques.answerList.push(new iqQuesAnswer());
    }
    this.initConfigCk();
  }

  initData(){
    this.iqQuesService.getQuesById(this.iqTestId).subscribe((response) => {
      this.ques = response[CommonConstant.DETAIL_KEY];
      this.answerList = this.ques.answerList;
      this.migrateAnswerCorrect(this.ques);
    });
  }

  migrateAnswerCorrect(ques: any){
    let label = this.trans.instant('iqTest.iqQues.iqQuesDetail.answerContent')
    ques.listAnswerCorrect.forEach((answer: any) => {
          answer.label = label+answer.label;
    });
  }


  saved(iqTestId: any) {
    this.router.navigate(['/ques/ques0101'], {
      // queryParams: {
      //   iqTestId : iqTestId,
      // },
    });
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    }
    if (!this.ques.key) {
      //create
      this.iqQuesService.save(this.ques).subscribe({
        next: (response) => {
          this.saved(response[CommonConstant.KEY]);
          this.ques.key = response[CommonConstant.KEY];
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    }else{
      //update
      this.iqQuesService.save(this.ques).subscribe({
        next: (response) => {
          this.toastrService.success('Success', ' Updated !');
        },
        error: () => {
          this.toastrService.error('Failed', 'Update  Failed !');
        },
      });
    }
  }

  getQuesCategory() {
    this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_CATEGORY_COMM_CD).subscribe((response) => {
        this.quesCategorys = response;
      });
  }

  getQuesTypes() {
    this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_TYPE_COMM_CD).subscribe((response) => {
        this.quesTypes = response;
      });
  }

  getQuesLevel() {
    this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_LEVEL_COMM_CD).subscribe((response) => {
        this.quesLevels = response;
      });
  }

  addAnswer(answerList: any[]){
    let newAnswer = new iqQuesAnswer();
    answerList.push(newAnswer);
  }

  removeAnswer(answerList: any[] , index: any, envent : any){
    answerList[index].deleteFlag = true;
    const AnswerDel = answerList.splice(index,1)
    this.ques.answerDelList.push(AnswerDel[0]);
  }

  createMultiSelect(iqAnswer: any){
    let label = this.trans.instant('iqTest.iqQues.iqQuesDetail.answerContent')
    var result: any[] = [];
    for(let i = 0 ; i< iqAnswer.answerList.length ; i++){
      var obj: any = {};
      obj.index = i;
      obj.label = label + (i+1);
      result.push(obj);
    }
    return result;
  }

  setCorrectAnswer(iqAnswer: any){
    // iqAnswer.answerList.map((item: any)=>{
    //   item.isAnswerCorrect = false;
    //   return item;
    // })

    for(let i = 0 ; i< iqAnswer.listAnswerCorrect.length ; i++){
      var indexChoose = iqAnswer.listAnswerCorrect[i].index;
      iqAnswer.answerList[indexChoose].answerCorrect = true;
    }
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  private onFileBrowserFileClick(event: { data: { ckEditorFuncNum: any; fileUrl: any; }; }) {
    (window as { [key: string]: any })['CKEDITOR'].tools.callFunction(event.data.ckEditorFuncNum, event.data.fileUrl);
  }

}

export class iqQuesAnswer{
  answerContent?: String;
  answerCorrect?: boolean;
  constructor() {
    this.answerContent = "";
    this.answerCorrect = false;
  }
}


