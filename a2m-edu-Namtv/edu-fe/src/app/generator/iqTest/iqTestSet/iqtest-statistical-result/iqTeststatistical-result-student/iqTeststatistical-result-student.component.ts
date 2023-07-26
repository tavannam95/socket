import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { IqTestService } from 'src/app/services/quesIq/iqTest.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { IqTestStatisticalComponent } from '../../iqTest-Statistical/iqTest-statistical.component';

@Component({
  selector: 'app-iqTeststatistical-result-student',
  templateUrl: './iqTeststatistical-result-student.component.html',
  styleUrls: ['./iqTeststatistical-result-student.component.css']
})
export class IqTestStatisticalResultStudentComponent implements OnInit {
  totalRecords = NaN;
  pageRequest = new searchModel();
  listClassSelected: any[] = [];
  studentSubmitSearch: any = {};
  iqTestId: any = "";
  listClass: any[] = [];
  listStuSubmited: any[] = [];
  listStuSubmitedFilter: any[] = [];

  constructor(
    private route : ActivatedRoute,
    private iqTestService: IqTestService,
    private quizService: QuizService,
    private toastrService: ToastrService,
    public dialog: MatDialog,
    private commonService: CommonService,
    private edu0201Service: Edu0201Service,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(async (params) => {
      this.iqTestId = params['iqTestId']?params['iqTestId']:"";
    });
    this.studentSubmitSearch.iqtestId = this.iqTestId;
    this.initData();
  }

  initData(){
    this.initPaging();
    this.onSearch();
  }

  initPaging(){
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
  }

  onSearch(){
    this.getListStuSubtmited();
  }

  // getListStuSubtmited(){
  //   this.listClassSelected.forEach((item: any, index : number) => {
  //     if(index == 0){
  //       this.studentSubmitSearch.classIds = item.key + ", "
  //     }else if(index > 0){
  //       this.studentSubmitSearch.classIds += item.key + ", "
  //     }
  //     if(index == this.listClassSelected.length-1){
  //       this.studentSubmitSearch.classIds = this.studentSubmitSearch.classIds.substring(0, this.studentSubmitSearch.classIds.length-2)
  //     }
  //   });

  //   if(this.listClassSelected.length == 0){
  //     this.studentSubmitSearch.classIds = "";
  //   }

  //   let param = Object.assign(this.studentSubmitSearch, this.pageRequest)

  //   this.quizService.getListStuSubtmited(param).subscribe((response) => {
  //     this.listStuSubmited = response[CommonConstant.LIST_KEY];
  //     this.listStuSubmitedFilter = this.listStuSubmited;
  //     this.totalRecords = response[CommonConstant.COUNT_ITEMS_KEY];

  //     CommonUtil.addIndexForListReverse(this.listStuSubmited, this.pageRequest.page, this.totalRecords);
  //     if(this.listStuSubmited.length>0){
  //       var initStu = this.listStuSubmited[0];
  //       //this.gotoStudent(initStu, 0);
  //     }

  //   });
  // }

  getListStuSubtmited(){
    let param = Object.assign(this.studentSubmitSearch, this.pageRequest)
    this.iqTestService.getListStuSubtmited(param).subscribe((response) => {
      this.listStuSubmited = response[CommonConstant.LIST_KEY];
      this.listStuSubmitedFilter = this.listStuSubmited;
      this.totalRecords = response[CommonConstant.COUNT_ITEMS_KEY];

      CommonUtil.addIndexForListReverse(this.listStuSubmited, this.pageRequest.page, this.totalRecords);
      if(this.listStuSubmited.length>0){
        var initStu = this.listStuSubmited[0];
        //this.gotoStudent(initStu, 0);
      }
    });
   }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();
  }

  showView(stu: any){
    stu["iqTestId"] = this.iqTestId;
    const dialogRef = this.dialog.open(IqTestStatisticalComponent, {
      data: {
        
        user: stu,
      },
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  };

  onSearchReset() {
    this.studentSubmitSearch.fullName = "";
    this.onSearch();
  }
}
