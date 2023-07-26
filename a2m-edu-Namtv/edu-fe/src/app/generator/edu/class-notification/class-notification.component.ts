import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Edu020103Service } from 'src/app/services/edu/edu020103.service';
import { CommonUtil } from 'src/app/utils/common-util';
import { ClassAnnouncementComponent } from '../../edu/edu0201/class-announcement/class-announcement.component';

@Component({
  selector: 'app-class-notification',
  templateUrl: './class-notification.component.html',
  styleUrls: ['./class-notification.component.css']
})
export class ClassNotificationComponent implements OnInit {

  pagingRequest: any = {};
  classId : String = '';
  classAnnouncements : any[] = [];
  classAnnouncement : any = {};
  totalClassAnnoun : Number = 0 ;
  @Input() classModel : any = {};
  countNoti: number = 0;

  constructor(
    private edu020103Service : Edu020103Service,
    public dialog: MatDialog,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.initPaging();
    this.searchData();
  }


  initPaging() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = CommonConstant.PAGE_SIZE;
    this.pagingRequest.getAll = true;
  }

  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.searchData();
  }

  searchData(){
    this.pagingRequest.classModel = this.classModel;
    this.edu020103Service.search(this.pagingRequest).subscribe((respo:any)=>{
      this.classAnnouncements = respo.list;
      this.setCountNoti();
      CommonUtil.addIndexForList(this.classAnnouncements, this.pagingRequest.page, this.totalClassAnnoun);
    })
  }

  setCountNoti(){
    this.countNoti = 0;
    this.classAnnouncements.forEach(classAnnouncement => {
      let announcementLog = classAnnouncement.announcementLog;
      if(!announcementLog.key) this.countNoti++;
    });
  }

  auditLog(classAnnouncement : any){
    this.edu020103Service.log(classAnnouncement.announcementLog).subscribe((respo:any)=>{
      this.searchData();
    })
  }

  viewDetail(classAnnouncement : any ,typeView : String){
      this.auditLog(classAnnouncement);
      const dialogRef = this.dialog.open(ClassAnnouncementComponent, {
      width: '0px',
      height: '0px',
      data: { classAnnouncement: classAnnouncement,
        typeView :typeView, },
      panelClass: 'custom-modalbox',
    });
      dialogRef.afterClosed().subscribe((response: any) => {

    });
  }

  viewAll(){
    //debugger
    this.router.navigate(['/edu/edu0201Form'], {
      queryParams: {
        id: this.classModel.key,
        tab: 2
      },
    });

  }
}
