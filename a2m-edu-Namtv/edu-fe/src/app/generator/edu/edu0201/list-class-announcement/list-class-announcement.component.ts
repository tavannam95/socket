import { paramBase } from './../../../../model/param-base';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { dE } from '@fullcalendar/core/internal-common';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonService } from 'src/app/services/common/common.service';
import { Edu0201Service } from 'src/app/services/edu/edu0201.service';
import { Edu020103Service } from 'src/app/services/edu/edu020103.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { ClassAnnouncementComponent } from '../class-announcement/class-announcement.component';

@Component({
  selector: 'app-list-class-announcement',
  templateUrl: './list-class-announcement.component.html',
  styleUrls: ['./list-class-announcement.component.css']
})
export class ListClassAnnouncementComponent implements OnInit {
  classId : String = '';
  classModel : any = {};
  classAnnouncement : any = {};
  classAnnouncements : any[] = [];
  totalClassAnnoun : Number = 0 ;
  pagingRequest: any = {};
  isEdit : Boolean = false ;

  constructor(
    private edu020103Service : Edu020103Service,
    private edu0201Service : Edu0201Service,
    private route : ActivatedRoute,
    public dialog: MatDialog,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    private toastr: ToastrService,
    private commonService: CommonService,
  ) { }

  ngOnInit(): void {
    this.initData();

  }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
    this.isEdit = !this.commonService.userHasRole(CommonConstant.ROLE_SYS_STUDENT ) ;

    this.route.queryParams.subscribe((response : any)=>{
      this.classId = response.id;
      if(this.classId){
        this.edu0201Service.getClassById(this.classId).subscribe((res:any)=>{

          this.classModel =  res.list;
          this.classAnnouncement = {};
          this.classAnnouncement.classModel = this.classModel;
          this.searchData();
        })
      }else{
        this.classModel = {};
      }

    })
  }

  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();
  }

  searchData(){
    this.pagingRequest.classModel = this.classModel;
    this.edu020103Service.search(this.pagingRequest).subscribe((respo:any)=>{
      //debugger
      this.classAnnouncements = respo.list;
      this.commonService.getAuthorInfo(this.classAnnouncements);
      this.totalClassAnnoun = respo.totalItems;
      CommonUtil.addIndexForListReverse(this.classAnnouncements, this.pagingRequest.page, this.totalClassAnnoun);
    })
  }





  viewDetail(classAnnouncement : any ,typeView : String){
      const dialogRef = this.dialog.open(ClassAnnouncementComponent, {
      width: '0px',
      height: '0px',
      data: { classAnnouncement: classAnnouncement,
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

  gotoOtherProfile(author : any){
    this.commonService.gotoOtherProfile(author);
  }

}



