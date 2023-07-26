import { FormPostFormComponent } from './../../../community/post/post-form/form-post-form/form-post-form.component';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu020103Service } from 'src/app/services/edu/edu020103.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { ClassAnnouncementComponent } from '../class-announcement/class-announcement.component';
import { PostService } from 'src/app/services/community/post.service';

@Component({
  selector: 'app-list-class-post',
  templateUrl: './list-class-post.component.html',
  styleUrls: ['./list-class-post.component.css']
})
export class ListClassPostComponent implements OnInit {

  classId : String = '';
  classModel : any = {};
  post : any = {};
  listPost : any[] = [];
  totalPost : Number = 0 ;
  totalClassAnnoun : Number = 0 ;
  pagingRequest: any = {};
  isEdit : Boolean = true ;
  apiUrl : any = "";


  constructor(
    private edu020103Service : Edu020103Service,
    private edu0201Service : Edu0201Service,
    private route : ActivatedRoute,
    public dialog: MatDialog,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    private toastr: ToastrService,
    private commonService: CommonService,
    private postService: PostService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.apiUrl = this.postService.getApiURL();
    this.initData();

  }

  initData() {
    this.initPageRequest();
    // this.isEdit = !this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT ) ;

    this.route.queryParams.subscribe((response : any)=>{
      this.classId = response.id;
      this.searchData();
    })
  }

  initPageRequest(){
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
  }

  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();
  }

  searchData(){
    this.pagingRequest.classId = this.classId;
    this.postService.searchPost(this.pagingRequest).subscribe((respo:any)=>{

      this.listPost = respo.list;
      this.totalPost = respo.totalItems;
      CommonUtil.addIndexForListReverse(this.listPost, this.pagingRequest.page, this.totalPost);
    })
  }

  viewDetail(post : any ,typeView : String){

      const dialogRef = this.dialog.open(FormPostFormComponent, {
      width: '0px',
      height: '0px',
      data: { post: post,
        typeView :typeView, },
      panelClass: 'custom-modalbox',
    });
      dialogRef.afterClosed().subscribe((response: any) => {
        this.initData();
    });
  }

  delete(classAnnouncement : any){

    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.edu020103Service.delete(classAnnouncement.key).subscribe({
          next: (response) => {
            this.toastr.success(
              this.trans.instant('message.success.deleteSuccess')
            );
            this.initData();
          },
          error:(response) =>{
            this.toastr.error(
              this.trans.instant('message.error.deleteFailed')
            );
          },
        })
      },
      reject: () => {},
    });

  }

  goPostDiscuss(post: any){
    this.router.navigate(['/community/post/'+post.key], {
      queryParams: {

      },
    });
  }

  badgeTagClick(tagName: any){
    this.listPost = [];
    this.initPageRequest();
    this.pagingRequest.searchText = tagName;
    this.searchData();
  }




}
