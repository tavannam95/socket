import { ToastrService } from 'ngx-toastr';

import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  Inject,
  Input,
  HostListener,
} from '@angular/core';

import { DatePipe, formatDate } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonService } from 'src/app/services/common/common.service';
import { TranslateService } from '@ngx-translate/core';
import { IqTestService } from 'src/app/services/quesIq/iqTest.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { IqTest } from 'src/app/model/iqTest/iqTest';
import { ConfirmationService } from 'primeng/api';
import {Clipboard} from '@angular/cdk/clipboard';
import { environment } from 'src/environments/environment';
import { GojsAngularModule } from 'gojs-angular';

@Component({
  selector: 'app-iqTest-form',
  templateUrl: './iqTest-form.component.html',
  styleUrls: ['./iqTest-form.component.css']
})
export class IqTestFormComponent implements OnInit {

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  quesTypes: any = {};
  quesType : any;
  quesLevels: any = {};
  queslevel : any;
  quesCategorys : any = {};
  quesCategory : any
  configCkeditor: any = {};
  quizTypes: any;
  quizItemTypes: any;
  submitted: any = false;
  iqTest: any = {};
  iqTestId: any;
  pdfFilePath : any = {};
  iqQues: any = {};
  categorys : any[] = [];
  listNumOfCategory: any[] =[];
  listIqTestMap: any[] =[];
  listIqQues: any[] =[];
  TotalQues: any = 0;

  constructor(
    private tccoStdService: TccoStdService,
    private iqTestService: IqTestService,
    private toastrService: ToastrService,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private router: Router,
    private fileUploadService :FileUploadService,
    private trans: TranslateService,
    private commonService: CommonService,
    private confirmationService: ConfirmationService,
    private clipboard: Clipboard
  ) {}

  ngOnInit(): void {
    this.getQuesCategory();
    //this.getQuesTypes();
    //this.getQuesLevel();

    this.route.queryParams.subscribe(async (params) => {
      this.iqTestId = params['iqTestId']?params['iqTestId']:"";
    });

    if (this.iqTestId) {
      this.initData();
    } else {
      this.iqTest = new IqTest();
      this.iqTest.status = true;
      this.iqTest.listNumOfCategory = this.listNumOfCategory;
      //this.iqTest.saveForListQues = false;
      this.getTotalQuestion();
    }
  }

  getTotalQuestion(){
    for(let i = 0; i < this.categorys.length; i++){
      this.iqTest.totalQues = this.categorys[i].num + this.categorys[i + 1].num;
    }
  }

  getQuesCategory() {
    this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_CATEGORY_COMM_CD).subscribe((response) => {
        this.categorys = response;
        for(let j = 0; j < this.categorys.length;j++){
          this.categorys[j].num = 0;
        }
      });
  }

  initData(){
    this.iqTestService.getIqTestById(this.iqTestId).subscribe((response) => {
      this.iqTest = response[CommonConstant.DETAIL_KEY];
      this.listIqQues = this.iqTest.listQues;
      for(let i = 0; i < this.listIqQues.length;i++){
        this.iqQues = this.listIqQues[i];
        this.quesCategory = this.iqQues.quesCategory;
        for(let j = 0; j < this.categorys.length;j++){
          if(this.categorys[j].commCd == this.quesCategory){
            this.categorys[j].num = this.categorys[j].num + 1;
          }
        }
      }
      //this.setCommNm();
      this.iqTest.totalQues = this.listIqQues.length;
    });

  }

  getListNumOfCategory(){
    this.TotalQues = 0;
    for(let i = 0; i < this.categorys.length;i++){
      var obj: any = {};
      obj.num = this.categorys[i].num;
      obj.category = this.categorys[i].commCd;
      this.listNumOfCategory.push(obj);

      this.TotalQues = this.categorys[i].num + this.TotalQues;
    }
    // if(this.TotalQues > this.iqTest.totalQues){
    //   alert("Total Number Of Questions Did Not Match!");
    //   this.submitted = true;
    //   return;
    // }else if(this.TotalQues < this.iqTest.totalQues){
    //   this.categorys[this.categorys.length - 1].num = this.categorys[this.listNumOfCategory.length - 1].num + (this.iqTest.totalQues - this.TotalQues);
    //   this.listNumOfCategory[this.listNumOfCategory.length - 1].num = this.categorys[this.categorys.length - 1].num;
    // }
  }

  saved(iqTestId: any) {
    //this.router.navigate(['/ques/ques0102/iqTestView'], {
    this.router.navigate(['/ques/ques0102'], {
      // queryParams: {
      //   iqTestId : iqTestId,
      // },
    });
  }

  copyHeroName() {
    this.clipboard.copy(environment.apiURL + '/ques/ques0102/iqTestView');
  }
  Submit(){
    this.confirmationService.confirm({
      header: this.trans.instant('button.submit.title'),
      message: this.trans.instant('button.submit.title'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	    rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
      },
      reject: () => {

        return
      },
    });
  }

  copyLinkIq(){
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      return;
    }
    this.getListNumOfCategory();
    if(this.TotalQues > this.iqTest.totalQues){
      alert("Total Number Of Questions Did Not Match!");
      return;
    }
    if(this.TotalQues < this.iqTest.totalQues){
      this.categorys[this.categorys.length - 1].num = this.categorys[this.listNumOfCategory.length - 1].num + Math.floor((this.iqTest.totalQues - this.TotalQues)/2);
      this.categorys[this.categorys.length - 2].num = this.categorys[this.listNumOfCategory.length - 2].num + (this.iqTest.totalQues - this.TotalQues -
         this.categorys[this.categorys.length - 1].num);
      this.listNumOfCategory[this.listNumOfCategory.length - 1].num = this.categorys[this.categorys.length - 1].num;
      this.listNumOfCategory[this.listNumOfCategory.length - 2].num = this.categorys[this.categorys.length - 2].num;
    }
    this.iqTest.saveForListQues = false;

    if (!this.iqTest.key) {
      //create
      this.iqTestService.save(this.iqTest).subscribe({
        next: (response) => {
          this.copyHeroName();
          this.saved(response[CommonConstant.KEY]);
          this.iqTest.key = response[CommonConstant.KEY];
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    }else{
      //update
      this.iqTestService.save(this.iqTest).subscribe({
        next: (response) => {
          this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.success.updateFailed'));
        },
      });
    }
  }

  initConfigCk(){
    this.configCkeditor = this.commonService.getCkConfigDefault();
  }

  private onFileBrowserFileClick(event: { data: { ckEditorFuncNum: any; fileUrl: any; }; }) {
    (window as { [key: string]: any })['CKEDITOR'].tools.callFunction(event.data.ckEditorFuncNum, event.data.fileUrl);
  }


  // setCommNm(){
  //   for(let i = 0; i < this.listIqQues.length; i++){
  //     this.quesCategory = this.listIqQues[i].quesCategory;
  //     this.quesType = this.listIqQues[i].quesType;
  //     this.queslevel = this.listIqQues[i].lev;
  //     for(let j = 0; j < this.categorys.length; j++){
  //       if(this.categorys[j].commCd == this.quesCategory){
  //         const categoryNm = this.categorys[j].commNm;
  //         this.listIqQues[i].quesCategory = categoryNm;
  //       }
  //     }
  //     for(let g = 0; g < this.quesTypes.length; g++){
  //       if(this.quesTypes[g].commCd == this.quesType){
  //         this.listIqQues[i].quesType = this.quesTypes[g].commNm;
  //       }
  //     }
  //     for(let h = 0; h < this.quesLevels.length; h++){
  //       if(this.quesLevels[h].commCd == this.queslevel){
  //         this.listIqQues[i].lev = this.quesLevels[h].commNm;
  //       }
  //     }
  //   }
  // }
  // getQuesCategory() {
  //   this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_CATEGORY_COMM_CD).subscribe((response) => {
  //       this.categorys = response;
  //     });
  // }

  // getQuesTypes() {
  //   this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_TYPE_COMM_CD).subscribe((response) => {
  //       this.quesTypes = response;
  //     });
  // }

  // getQuesLevel() {
  //   this.tccoStdService.getCommCdByUpCommCd(CommonConstant.IQ_QUES_LEVEL_COMM_CD).subscribe((response) => {
  //       this.quesLevels = response;
  //     });
  // }

}

