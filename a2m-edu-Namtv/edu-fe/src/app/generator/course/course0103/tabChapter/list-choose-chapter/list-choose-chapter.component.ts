import { Component, EventEmitter, Inject, OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { CommonUtil } from 'src/app/utils/common-util';

@Component({
  selector: 'app-list-choose-chapter',
  templateUrl: './list-choose-chapter.component.html',
  styleUrls: ['./list-choose-chapter.component.css']
})
export class ListChooseChapterComponent implements OnInit {

  public get data(): any {
    return this._data;
  }
  public set data(value: any) {
    this._data = value;
  }

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();


  pagingRequest = new searchModel();
  listChapter: any[] = [];
  totalChapter = 0;
  course : any ={}
  courseId : any;
  courseObj: any = new Object();
  couChapterLst: any[] = [];
  checkList : any =[];

  listChecked!: any[];
  listFilter !: any[];

  listStatus = [
    { label: this.trans.instant('sys.sys0101.status.all')},
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "0" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "1" }
  ];

  constructor(
    private commonService: CommonService,
    private course0103Service: Course0103Service,
    //private edu0102Service: Edu0102Service,
    private toastrService: ToastrService,
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private router: Router,
    private route : ActivatedRoute,
    public dialogRef: MatDialogRef<ListChooseChapterComponent>,
    @Inject(MAT_DIALOG_DATA)
    private _data: any,
  ){ }

  ngOnInit(): void {
    this.route.queryParams.subscribe(async (params) => {
      this.courseId = params['courseId'];
    });
    this.listChapter = [];
    this.listChecked = [];
    this.listFilter = [];
    this.initData();
    this.onSearch();
  }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
    if (this.data.listChooseChapter) {
      this.listFilter = this.data.listChooseChapter;
    }
  }

  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.onSearch();
  }

  onCancel(){}

  filterFunc() {
    this.listFilter.forEach( (e) => {
      this.listChapter = this.listChapter.filter((element) => {
        return element.key!=e.key;
      });
    });

    this.listChapter.forEach(e => {
      this.listChecked.forEach(element => {
        if(e.key==element.key){
          e.checked = true ;
        }
      });

    });

  }


  onSearch() {
    this.course0103Service.search(this.pagingRequest).subscribe((response) => {
          this.listChapter = response[CommonConstant.LIST_KEY];
          this.filterFunc();
          this.totalChapter = response[CommonConstant.COUNT_ITEMS_KEY];
          CommonUtil.addIndexForListReverse(this.listChapter, this.pagingRequest.page, this.totalChapter);
        });
  }

  onSearchReset(){
    this.pagingRequest = new searchModel();
    this.initData();
    this.onSearch();
  }

  initSave(){
    let checkListId: any=[];
    this.listChecked.forEach( (element: any) => {
      checkListId.push(element.key);
    });
    this.course.listChapterId = checkListId;
    this.course.courseId = this.courseId;
  }

  onSave() {
    
    this.initSave();
    this.course0103Service.saveCSM(this.course).subscribe({
      next: (response) => {
        this.toastrService.success('Success', ' Added !');
        this.save.emit(response);
        this.onSearch();
        this.dialogRef.close(this.course);
      },
      error: () => {
        this.toastrService.error('Failed', 'Add  Failed !');
      },
    });
  }


  handleSelectChapter(param : any ){
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
