import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { count } from 'rxjs';
import { CommonConstant } from 'src/app/constants/common.constant';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';

@Component({
  selector: 'app-subject-schedule-table',
  templateUrl: './subject-schedule-table.component.html',
  styleUrls: ['./subject-schedule-table.component.css']
})
export class SubjectScheduleTableComponent implements OnInit {

  @Input() subjectId: string = "";
  @Input() courseId: string = "";
  listChapterSchedule : any [] = [];
  organizationFormalStds : any [] = [];
  constructor(
    private course0102Service: Course0102Service,
    private tccoStdService: TccoStdService,
    private commonService: CommonService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.getOrganizationFormals();
    this.initData();
  }

  initData(){
    if(!this.subjectId) return;
    this.course0102Service.getLectureScheduleBySubjectId(this.subjectId).subscribe((response) => {
      this.listChapterSchedule = response[CommonConstant.LIST_KEY];
    });
  }

  getOrganizationFormals() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.ORGANIZATION_FORMAL)
      .subscribe((response) => {
        this.organizationFormalStds = response;
        this.commonService.selectLangComcode(this.organizationFormalStds);
        //
        // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
      });
  }

  countRow(chapter: any){
    let organizationFormals = chapter.organizationFormals;
    let count = 0;
    this.organizationFormalStds.forEach( (std:any) => {
      const commCd = std.commCd;
      count += organizationFormals[commCd];
    });
    return count;
  }

  countCol(commCd: any){
    let count = 0;
    this.listChapterSchedule.forEach( (chapter: any) => {
      const  organizationFormals = chapter.organizationFormals;
      count += organizationFormals[commCd];
    });
    return count;
  }

  countAll(){
    let countAll = 0;
    this.organizationFormalStds.forEach( (std:any) => {
      const commCd = std.commCd;
      countAll += this.countCol(commCd);
    });
    return countAll;
  }

  showUpdateForm(chapterId: any) {

    if(this.subjectId){
      this.router.navigate(['/course/course0101'], {
        queryParams: {
          chapterId: chapterId,
          subjectId : this.subjectId,
          courseId: this.courseId
        },
      });
      // this.commonService.changeCourse(this.courseId);
    }
  }
}
