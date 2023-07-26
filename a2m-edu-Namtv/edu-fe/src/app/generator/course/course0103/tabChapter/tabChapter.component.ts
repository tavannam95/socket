import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { searchModel } from 'src/app/model/search-model';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { SubjectFormComponent } from '../../course0102/component/subject-form/subject-form.component';
import { TabStandardComponent } from '../../standard/tabStandard.component';
import { ListChapterComponent } from '../list-chapter/list-chapter.component';

@Component({
  selector: 'app-tabChapter',
  templateUrl: './tabChapter.component.html',
  styleUrls: ['./tabChapter.component.css']
})
export class TabChapterComponent implements OnInit,AfterViewInit {
  courseId:any;
  subjectId:any;
  oldSubjectId:any ;
  isNew :any;
  col : any;
  @ViewChild('subjectForm')
  subjectForm!: SubjectFormComponent
  @ViewChild('chapterList')
  chapterList!: ListChapterComponent
  @ViewChild('standard')
  standard!: TabStandardComponent
  courseObj: any;

  constructor(
    private route : ActivatedRoute,
    private edu0102Service: Edu0102Service,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private course0102Service: Course0102Service,
  ) { }


  ngAfterViewInit(): void {
      this.route.queryParams.subscribe( (params) => {
        if(this.oldSubjectId!=undefined && this.oldSubjectId!=params['subjectId']){
          this.subjectForm.initData(this.subjectId);
          this.chapterList.init();
        }
        const tab = params['tab'];
        if(tab){
          this.changeTab(tab);
        }
    });
    // console.log(this.subjectForm.subject.editSubjectForm)
  }

   ngOnInit() {
    // this.col='col-xxl-12';
      this.route.queryParams.subscribe( (params) => {
      this.oldSubjectId = this.subjectId;
      this.courseId = params['courseId'];
      this.subjectId = params['subjectId'];
      this.getData();
      this.getSubjectHistory();
      // this.commonService.changeCourse(this.courseId);
    });
  }

  getData(){
    let requestModel = new searchModel();
    requestModel.key = this.courseId;
    this.edu0102Service.getCourseByCondition(requestModel).subscribe( (response:any) => {
    this.courseObj =  response[CommonConstant.DETAIL_KEY];
    });
  }

  subject : any ={
    editSubjectForm: null,
    editListChapter: null,
    editStand: null
  }
  getSubjectHistory() {
     this.course0102Service.getSubjectHistory(this.subjectId).toPromise().then((response) => {

        this.subject = response[CommonConstant.DETAIL_KEY];
      });
  }

  changeTab(tabNumber: any){
    if(this.subjectId==""){
      let mes = this.trans.instant('subject.info.init.required');
      this.confirmationService.confirm({
        header: mes,
        acceptLabel: this.trans.instant('button.confirm.title'),
        accept: () => {
          // tabNumber=1;
        },

      });
      tabNumber=1;
    }
    const ele = document.getElementById('tabChapter'+tabNumber);

      ele?.click();
  }

  async getDataByViewType(event : any){
    if(event=="PUBLIC"){

    await  this.subjectForm.getSubject(this.subjectId);
      this.subjectForm.checkView = true ;
    await  this.chapterList.onSearchMain();
      this.chapterList.checkView = true ;
      this.standard.getDataByViewType(event);
    }else{
    await  this.subjectForm.getSubjectHistory(this.subjectId);
      this.chapterList.onSearch();
      this.standard.getDataByViewType(event);
    }
  }
}

