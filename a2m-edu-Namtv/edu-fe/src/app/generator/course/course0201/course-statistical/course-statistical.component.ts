import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { CommonConstant } from 'src/app/constants/common.constant';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { ViewDoc } from 'src/app/model/course/viewDoc';
import { searchModel } from 'src/app/model/search-model';
import { CommonService } from 'src/app/services/common/common.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';


@Component({
  selector: 'app-course-statistical',
  templateUrl: './course-statistical.component.html',
  styleUrls: ['./course-statistical.component.css']
})
export class CourseStatisticalComponent implements OnInit {

  @ViewChild('courseContent')
  courseContent!: ElementRef;
  @ViewChild('row')
  row!: ElementRef;
  panelOpenState = false;

  userInfo:any;
  courseId: any;
  studentId: any;
  tsstUser:any = {};
  courseObj: any = new Object();
  couSubjectLst: any[] = [];
  arrRoles : any[] = [];
  checkView : any = true;
  viewDoc : any = {};
  listDocument: any[] = [];
  percentDoc : any = '';
  listTypeDoc: any[] = [];
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private toastrService: ToastrService,
    private edu0102Service: Edu0102Service,
    private edu0101Service: Edu0101Service,
    private course0103Service: Course0103Service,
    private commonService : CommonService,
    private trans: TranslateService,
    public dialog: MatDialog,
    private tccoStdService: TccoStdService,
  ) { }

  ngOnInit(): void {
    this.getUserInfo();
    let strRoles = AuthenticationUtil.getUserRole();
    this.arrRoles = strRoles.split("_");

    this.route.queryParams.subscribe(async (params) => {
      this.courseId = params['courseId'];
      this.studentId = params['studentId'];
      // this.commonService.changeCourse(this.courseId);
      this.getTsstUserByUserInfoId(this.studentId);
      let requestModel = new searchModel();
      requestModel.key = this.courseId;
      let request: any = {};
      request = Object.assign(requestModel, {});
      request.approvalStatus = CommonConstant.APPROVAL_STATUS_APPROVAL_COMM_CD;
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      if (this.courseId) {
        await this.edu0102Service.getCourseByCondition(requestModel).subscribe((response) => {

          this.courseObj = response[CommonConstant.DETAIL_KEY];
          if(this.courseObj.subjectModels.length>0){
            this.couSubjectLst = this.courseObj.subjectModels.reverse();
            this.couSubjectLst = this.couSubjectLst.filter(element => element.deleteFlag!=true && element.documentStatus==CommonConstant.DOCUMENT_STATUS_APPROVAL);
            this.couSubjectLst.forEach(element => {
              element.documentTotal = 0;
              element.listDocument = [];
              if(element.chapterModels.length>0){
                element.chapterModels = element.chapterModels.filter((ele:any) => ele.deleteFlag!=true && ele.documentStatus==CommonConstant.DOCUMENT_STATUS_APPROVAL);
                element.chapterModels.forEach((ele:any) => {
                  ele.lectureModels = ele.lectureModels.filter((e:any) => e.deleteFlag!=true);
                  let listLecture = ele.lectureModels;
                  let listQuiz = ele.listQuiz;
                  ele.documentTotal = listLecture.length + listQuiz.length;
                  ele.listDocument = [];
                  if(listLecture.length > 0) {
                    listLecture.forEach((e:any) =>{
                      this.getListViewDoc(e);
                      ele.listDocument.push(e);
                      if((e.listViewDoc[0].key != undefined || e.listViewDoc[0].key != null) && e.listViewDoc[0].isComplete){
                        element.listDocument.push(e);
                      }
                    });
                  }
                  if(listQuiz.length > 0){
                    listQuiz.forEach((e:any) =>{
                      e.typeDocument = 'Quiz';
                      this.getListViewDoc(e);
                      ele.listDocument.push(e);
                      if((e.listViewDoc[0].key != undefined || e.listViewDoc[0].key != null) && e.listViewDoc[0].isComplete){
                        element.listDocument.push(e);
                      }
                    });
                  }
                  ele.listDocument.sort((a:any, b:any) => a.ordNo - b.ordNo);
                  element.documentTotal += ele.documentTotal;
                });
              }
              element.percentDoc = Math.round((element.listDocument.length/element.documentTotal)*100);
            });

          }
          this.initCheckView();
          dialogSpinner.close();
          });
      }
    });
    this.getTypeDocumentByCommCode();
  }

  getTsstUserByUserInfoId(studentId:any){
    this.edu0101Service.getTsstUserByUserInfoId(studentId).subscribe((response) => {
      this.tsstUser = response[CommonConstant.DETAIL_KEY];
    });
  }

  getListViewDoc(doc:any){
    if(doc.listViewDoc.length >0){
      doc.listViewDoc.forEach((e:any) => {
        if(e.userUid != this.tsstUser.userUid){
          doc.listViewDoc = doc.listViewDoc.filter((item:any) => item != e);
        }
      })
      if(doc.listViewDoc.length == 0){
        doc.listViewDoc[0] = new ViewDoc();
        doc.listViewDoc[0].isComplete = false;
      }
    }else{
      doc.listViewDoc[0] = new ViewDoc();
      doc.listViewDoc[0].isComplete = false;
    }
    doc.checked = doc.listViewDoc[0].isComplete;
  }

  getUserInfo(){
    this.commonService.getUserInfo().subscribe((response) =>{
        this.userInfo = response;
      });
  }

  async getTypeDocumentByCommCode() {
    await this.tccoStdService
       .getCommCdByUpCommCd(CommonConstant.TYPE_DOCUMENT)
       .toPromise().then((response) => {
           this.listTypeDoc = response;
           this.commonService.selectLangComcode(this.listTypeDoc);

         // response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
       });
   }

  getTypeDocNm(typeDocument : any){

    const commCode = this.listTypeDoc.find(
      (element) => element.commCd == typeDocument
    );
      if(!commCode) return "";
    return commCode.label;

  }

  initCheckView(){
    if(this.commonService.permModify(this.courseObj.insUid, true)){
      this.checkView = false;
    }else{
      this.checkView = true;
    }
  }

  getStatisPer(){
    return this.arrRoles.includes('R000') || this.arrRoles.includes('R001') || this.arrRoles.includes('R002') || this.arrRoles.includes('R013')
  }

  handleLectureDetail(lectureId: any){
    this.router.navigate(['/course/course0201Lecture'], {
      queryParams: {
        lectureId: lectureId,
        courseId : this.courseId
      },
    });
  }

  handleChapterDetail(chapterId: any){
    this.router.navigate(['/course/course0201Chapter'], {
      queryParams: {
        chapterId: chapterId,
        courseId : this.courseId
      },
    });
  }

  handleQuizDetail(quizId: any){
    this.router.navigate(['/course/course0201Quiz'], {
      queryParams: {
        quizId: quizId,
        courseId : this.courseId
      },
    });
  }

  showUpdateCourseForm(courseId: any) {
    this.router.navigate(['/course/course0102'], {
      queryParams: { courseId: courseId },
    });
    // this.commonService.changeCourse(courseId);
  }
}
