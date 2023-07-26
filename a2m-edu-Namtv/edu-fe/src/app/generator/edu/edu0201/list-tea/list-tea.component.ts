import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Edu020102Service } from 'src/app/services/edu/edu020102.service';
import { I18nService } from 'src/app/services/i18n.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { ListStuComponent } from '../list-stu/list-stu.component';

@Component({
  selector: 'app-list-tea',
  templateUrl: './list-tea.component.html',
  styleUrls: ['./list-tea.component.css']
})
export class ListTeaComponent implements OnInit {
  constructor(
    private edu020102Service: Edu020102Service,
    private i18nService: I18nService,
    private trans: TranslateService,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<ListStuComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) { this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });}

  request: any = {};
  listTeacher!: any[];
  pagingRequest: any = {};
  totalTeacher!: any;
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
    this.listTeacher = [];
    this.listChecked = [];
    this.listFilter = [];
    this.initData();
    this.searchData();


  }


  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;

    if (this.data.listTeacher) {
      this.listFilter = this.data.listTeacher;
    }

  }

  filterFunc() {
    this.listFilter.forEach( (e) => {
      this.listTeacher = this.listTeacher.filter((element) => {
        return element.key!=e.key;
      });
    });

  //   this.listChecked.push.apply(this.listChecked,this.listFilter);
  //   var list = this.listChecked.filter(function(elem, index, self) {
  //     return index === self.indexOf(elem);
  // })

    this.listTeacher.forEach(e => {
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
    this.edu020102Service.searchTeacher(this.pagingRequest).subscribe((response) => {
      
      this.listTeacher = response.list;
      this.filterFunc();
      this.totalTeacher = response.totalItems;
      CommonUtil.addIndexForListReverse(this.listTeacher, this.pagingRequest.page, this.totalTeacher);
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
