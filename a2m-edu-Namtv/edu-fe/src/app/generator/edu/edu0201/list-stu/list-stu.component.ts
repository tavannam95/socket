import { AfterViewInit, Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Edu020101Service } from 'src/app/services/edu/edu020101.service';
import { I18nService } from 'src/app/services/i18n.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { StudentFormComponent } from '../../edu0101/component/student-form/student-form.component';

@Component({
  selector: 'app-list-stu',
  templateUrl: './list-stu.component.html',
  styleUrls: ['./list-stu.component.css']
})
export class ListStuComponent implements OnInit {

  constructor(
    private edu020101Service: Edu020101Service,
    private i18nService: I18nService,
    private trans: TranslateService,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<ListStuComponent>,
    @Inject(MAT_DIALOG_DATA) public   data: any,
  ) { this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  request: any = {};
  listStudent!: any[];
  pagingRequest: any = {};
  totalStudent!: any;
  language: any;
  listChecked!: any[];
  listFilter !: any[];
  listSeach!:any[];

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  ngOnInit(): void {
    // this.getPositionUpCommCd(CommonConstant.POSITION_COMM_CD);
    this.listStudent = [];
    this.listChecked = [];
    this.listFilter = [];
    this.initData();
    this.searchData();


  }


  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;

    if (this.data.listStudent) {
      this.listFilter = this.data.listStudent;
    }

  }

  filterFunc() {
    this.listFilter.forEach( (e) => {
      this.listStudent = this.listStudent.filter((element) => {
        return element.key!=e.key;
      });
    });

    this.listStudent.forEach(e => {
      this.listChecked.forEach(element => {
        if(e.key==element.key){
          e.checked = true ;
        }
      });
    });

  }

  getPaging(event: any) {

    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();
  }


  searchData() {
    this.edu020101Service.searchStudent(this.pagingRequest).subscribe((response) => {

      this.listStudent = response.list;
      this.filterFunc();
      this.totalStudent = response.totalItems;
      CommonUtil.addIndexForListReverse(this.listStudent, this.pagingRequest.page, this.totalStudent);
      // console.log(response);
    });
  }

  onSearchReset(){
    this.pagingRequest = {};
    this.initData();
    this.searchData();
  }

  onCancel() {
      this.dialogRef.close();
  }

  onSave() {

    this.dialogRef.close(this.listChecked);
  }

  handleSelectStudent(param : any ){
    if(this.listChecked.length!=0){
    let flag = false;
    this.listChecked.forEach( (element,index) => {
      if(element.key==param.key){
        param.checked = false ;
         this.listChecked.splice(index,1);
         flag = true;
        return;
      }
    });
    if(flag ==false){
      param.checked = false ;
      this.listChecked.push(param);
    }
  }else{
    param.checked = true ;
    this.listChecked.push(param);
  }
}

}
