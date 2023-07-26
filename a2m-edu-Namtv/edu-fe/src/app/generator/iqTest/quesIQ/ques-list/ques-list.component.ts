import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { IqTest } from 'src/app/model/iqTest/iqTest';
import { IqQuesService } from 'src/app/services/quesIq/iqQues.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { IqTestByQuesListComponent } from './iqTestByQuesList-form/iqTestByQuesList-form.component';


@Component({
  selector: 'app-iqTest-list',
  templateUrl: './ques-list.component.html',
  styleUrls: ['./ques-list.component.css'],
})
export class IqQuesListComponent implements OnInit {
  quesTypes: any = {};
  quesType: any;
  quesLevels: any = {};
  queslevel: any;
  quesCategorys: any = {};
  quesCategory: any;

  pageRequest: any = {};

  listChecked!: any[];

  listQues: any[] = [];
  totalQues: any;

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all') },
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: '0' },
    { label: this.trans.instant('sys.sys0101.status.active'), value: '1' },
  ];

  constructor(
    private tccoStdService: TccoStdService,
    private iqQuesService: IqQuesService,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.listChecked = [];
    this.getQuesTypes();
    this.getQuesCategory();
    this.initData();
    this.onSearch();
  }

  getPaging(event: any) {
    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();
  }
  onSearch() {
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.iqQuesService.search(this.pageRequest).subscribe((response) => {
      this.listQues = response.list;
      this.totalQues = response.totalItems;
      this.compareWithCheckedList();
      CommonUtil.addIndexForListReverse(
        this.listQues,
        this.pageRequest.page,
        this.totalQues
      );
      this.setCommNm();
      dialogSpinner.close();
    });
  }

  compareWithCheckedList(){
    //add checked = true for list question
    this.listChecked.forEach(questionChecked => {
      let checkedKey = questionChecked.key;
      const tempQues = this.listQues.find( ques => ques.key == checkedKey )
      if(tempQues){
        tempQues.checked = true;
      }
    });
  }

  delete(ques: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.iqQuesService.delete(ques.key).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
              this.toastr.success(
                this.trans.instant('message.success.deleteSuccess')
              );
              this.onSearch();
            } else {
              this.toastr.error(
                this.trans.instant('message.error.deleteFailed')
              );
            }
          },
          error: () => {
            this.toastr.error(this.trans.instant('message.error.deleteFailed'));
          },
        });
      },
      reject: () => {},
    });
  }

  showAddForm() {
    this.router.navigate(['/ques/ques0101/iqQuesForm'], {
      queryParams: {
        iqTestId: '',
      },
    });
  }

  showUpdateForm(id: any, event:any) {
    let targetName = event.target.tagName;
    if(targetName != 'INPUT' && targetName != 'A'  && targetName != 'I')
    this.router.navigate(['/ques/ques0101/iqQuesForm'], {
      queryParams: {
        iqTestId: id,
      },
    });

  }

  onSearchReset() {
    //  this.pageRequest = new IqQues();
    this.initData();
    this.onSearch();
  }

  initData() {
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
  }

  //   handleSelectQues(param : any ){
  //     if(this.listChecked.length!=0){
  //     let flag = false;
  //     this.listChecked.forEach( (element,index) => {
  //       if(element.key==param.key){
  //         param.checked = false ;
  //          this.listChecked.splice(index,1);
  //          flag = true;
  //         return;
  //       }
  //     });
  //     if(flag ==false){
  //       param.checked = false ;
  //       this.listChecked.push(param);
  //     }
  //   }else{
  //
  //     param.checked = true ;
  //     this.listChecked.push(param);
  //   }
  // }

  handleSelectQues(ques: any) {
    let tempQuestion = Object.assign({}, ques);
    if (ques.checked) {
      let exist = false;
      this.listChecked.forEach((e) => {
        if (e.key == ques.key) {
          exist = true;
          return;
        }
      });
      if(!exist){
        this.listChecked.push(tempQuestion);
      }
    }else{
      let exist = false;
      let indexExist = NaN;
      this.listChecked.forEach((e, index) => {
        if (e.key == ques.key) {
          exist = true;
          indexExist = index;
          return;
        }
      });
      if(exist){
        this.listChecked.splice(indexExist,1);
      }
    }

  }

  addIqTest() {
    if (this.listChecked.length != 0) {
      const dialogRef = this.dialog.open(IqTestByQuesListComponent, {
        width: '0px',
        height: '0px',
        data: { listChecked: this.listChecked },
        panelClass: 'custom-modalbox',
      });
      dialogRef.afterClosed().subscribe((response: any[]) => {
        if (response) {
          this.onSearch();
        }
      });
    } else {
      let label = this.trans.instant('iqTest.IqTest.alertQues')
      alert(label);
    }
  }

  setCommNm() {
    for (let i = 0; i < this.listQues.length; i++) {
      this.quesCategory = this.listQues[i].quesCategory;
      this.quesType = this.listQues[i].quesType;
      for (let j = 0; j < this.quesCategorys.length; j++) {
        if (this.quesCategorys[j].commCd == this.quesCategory) {
          const categoryNm = this.quesCategorys[j].commNm;
          this.listQues[i].quesCategory = categoryNm;
        }
      }
      for (let g = 0; g < this.quesTypes.length; g++) {
        if (this.quesTypes[g].commCd == this.quesType) {
          this.listQues[i].quesType = this.quesTypes[g].commNm;
        }
      }
    }
  }
  getQuesCategory() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.IQ_QUES_CATEGORY_COMM_CD)
      .subscribe((response) => {
        this.quesCategorys = response;
      });
  }

  getQuesTypes() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.IQ_QUES_TYPE_COMM_CD)
      .subscribe((response) => {
        this.quesTypes = response;
      });
  }
}
