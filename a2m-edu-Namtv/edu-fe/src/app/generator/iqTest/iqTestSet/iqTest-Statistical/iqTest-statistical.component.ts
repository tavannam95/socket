import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SuccessfullComponent } from 'src/app/generator/commons/modal/successfull/successfull.component';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { IqTestService } from 'src/app/services/quesIq/iqTest.service';

@Component({
  selector: 'app-iqTest-Statistical',
  templateUrl: './iqTest-Statistical.component.html',
  styleUrls: ['./iqTest-Statistical.component.css']
})
export class IqTestStatisticalComponent implements OnInit {

  pages: any;
  rows: any;
  totalQues : any;
  iqTestId: any = "";
  user : any;
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

  display: any;
  timerInterval: ReturnType<typeof setTimeout> | undefined;
  iqItemCurrent: any = {};
  quesIndexCurrent : any;
  quesContents: any;
  idx : any = "idx";
  studentSubmitSearch: any = {
    fullName : "",
    iqtestId : null
  }
  constructor(
    private route : ActivatedRoute,
    private iqTestService: IqTestService,
    private toastrService: ToastrService,
    public dialog: MatDialog,
    private edu0201Service: Edu0201Service,
    private trans: TranslateService,
    private commonService: CommonService,
    private confirmationService: ConfirmationService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(){
    this.user = this.data.user;
    this.gotoStudent(this.user, 0);
  }

  getListStuSubtmited(){
    this.iqTestService.getListStuSubtmited(this.studentSubmitSearch).subscribe((response) => {
      this.listStuSubmited = response[CommonConstant.LIST_KEY];
      this.listStuSubmitedFilter = this.listStuSubmited;

      if(this.listStuSubmited.length>0){
        var initStu = this.listStuSubmited[0];
        this.gotoStudent(initStu, 0);
      }
    });
   }

   getIqTestSubmit(iqTestId: any, userInfoId: any){
    var param = {
      iqTestId: iqTestId,
      // userInfoId: userInfoId,
    }
    this.iqTestService.getIqTestSubmit(param).subscribe((response) => {
      this.iqTest = response[CommonConstant.DETAIL_KEY];
      const listUser = this.iqTest.listUser;
      if(this.iqTest.listQuesResult){
        //this.listQues = this.iqTest.listQuesResult;
        this.totalQues = this.listQues.length;
        this.submit = true;
      }
      for(let i = 0; i < listUser.length; i++){
        if(listUser[i].nonUser != null && listUser[i].nonUser.key == userInfoId){
          this.listQues = listUser[i].listQuesResult;
          this.totalCorrectAnswer = listUser[i].totalCorrectAnswer;
          this.totalWrongAnswer = listUser[i].totalWrongAnswer;
          this.totalNotChoose = listUser[i].totalNotChoose;
        }
        if(listUser[i].tsstUserModel != null && listUser[i].tsstUserModel.userUid == userInfoId){
          this.listQues = listUser[i].listQuesResult;
          this.totalCorrectAnswer = listUser[i].totalCorrectAnswer;
          this.totalWrongAnswer = listUser[i].totalWrongAnswer;
          this.totalNotChoose = listUser[i].totalNotChoose;
        }
      }

      // this.iqItemCurrent = this.listQues[0];
      this.goQuestion(0);
    });
  }

  gotoStudent(student: any, stuIdx: any){
    this.stuIdxSelected = stuIdx;
      this.getIqTestSubmit(student.iqTestId, student.userId);
  }

  goQuestion(index: any){
    //next
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
  onCancel(){}
}
