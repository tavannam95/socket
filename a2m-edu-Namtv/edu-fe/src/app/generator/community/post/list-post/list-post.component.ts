import { DatePipe, DOCUMENT } from '@angular/common';
import { Component, HostListener, Inject, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { PostService } from 'src/app/services/community/post.service';
import { TempDataService } from 'src/app/services/temp-data.service ';
import { WINDOW } from 'src/app/services/window.service ';
import { FormPostFormComponent } from '../post-form/form-post-form/form-post-form.component';


@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.css']
})
export class ListPostComponent implements OnInit {

  startTime : any ;
  endTime : any ;
  listPost: any[] = [];
  totalPost : any = 0 ;
  apiUrl : any = "";
  pageRequest: any = new searchModel();

  listLecture: any[] = [];
  fullLoad = false;
  searchText : any = "";
  pageContent : any = "page-content";
  col : any = "col-xxl-9";

  @Input() classId: any = "";
  @Input() type: any = "POST";
  @Input() referenceType: any = "";
  @Input() referenceId: any = "" ;

  constructor(
    private postService: PostService,
    private tempDataService: TempDataService,
    public datepipe: DatePipe,
    private router: Router,
    public dialog: MatDialog,
    private commonService: CommonService,
    @Inject(WINDOW) private window: Window,
    @Inject(DOCUMENT) private document: Document,
  ) { }

  ngOnInit(): void {
    this.apiUrl = this.postService.getApiURL();
    this.initPageRequest();
    this.initData();
    this.tempDataService.postDiscusBackUrl = this.router.url;
  }

  initPageRequest(){
    this.pageRequest.page = 0;
    this.pageRequest.rows = CommonConstant.PAGE_SIZE;
  }

  initData(){
    this.initParam();
    this.onSearch();
  }

  getPaging(event: any) {

    this.pageRequest.page = event.page;
    this.pageRequest.rows = event.rows;
    this.onSearch();

  }

  initParam(){
    this.pageRequest.classId = this.classId ;
    this.pageRequest.type = this.type ;
    this.pageRequest.referenceType = this.referenceType ;
    this.pageRequest.referenceId = this.referenceId ;
    this.initCss();
  }

  initCss(){
    if(this.type=='CLASS'){
      this.col ="col-xxl-12";
      this.pageContent = "";
    }
    else if(this.type=='CHAPTER'){
      this.col ="col-xxl-9";
      this.pageContent = "";
    }
  }

  onSearchReset(){
    //debugger
    this.pageRequest = {};
    this.startTime = null ;
    this.endTime = null ;
    this.searchText = '';
    this.initPageRequest();
    this.initData();
  }

  onSearch(){
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    if(this.startTime){
      this.pageRequest.startTime =  this.datepipe.transform(this.startTime, 'yyyy/MM/dd');
    }
    if(this.endTime){
      this.pageRequest.endTime =  this.datepipe.transform(this.endTime, 'yyyy/MM/dd');
    }
    this.postService.searchPost(this.pageRequest).subscribe({
      next: (response) => {

        //debugger

        this.listPost = response[CommonConstant.LIST_KEY];
        this.commonService.getAuthorInfo(this.listPost);
        this.totalPost = response[CommonConstant.COUNT_ITEMS_KEY];
        this.genListTag(this.listPost);
        // if(list.length < this.pageRequest.rows){
        //   this.fullLoad = true;
        // }else{
        //   this.fullLoad = false;
        // }
        // this.listPost.push(...list)
        dialogSpinner.close();
      },
      error: () => {
        dialogSpinner.close();
      }
    })
  }

  scrollTop(){
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth'
    });
  }

  badgeTagClick(tagName: any){
    this.listPost = [];
    this.initPageRequest();
    this.pageRequest.searchText = tagName;
    this.onSearch();
  }

  genListTag(list: any[]){
    list.forEach((element: any) => {
      element.listTag = JSON.parse(element.tags);
    });
  }

  search(){
    this.listPost = [];
    this.initPageRequest();
    this.pageRequest.searchText = this.searchText;
    this.onSearch();
  }

  goPostDiscuss(post: any){
    this.router.navigate(['/community/post/'+post.key], {
      queryParams: {

      },
    });
  }

  openPostDiaglog(postId? : any){
    const dialogRef = this.dialog.open(FormPostFormComponent, {
      width: '0px',
      height: '0px',
      data: {
        postId: postId  ,
        type :this.type,
        classId : this.classId,
        referenceType : this.referenceType,
        referenceId : this.referenceId,

      },
      panelClass: 'custom-modalbox',
    });
      dialogRef.afterClosed().subscribe((response: any) => {
        if(response)
        this.initData();
    });
  }

  gotoOtherProfile(author : any){
    //debugger
    this.commonService.gotoOtherProfile(author);
  }

}
