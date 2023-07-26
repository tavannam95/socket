import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { SubjectFormComponent } from '../../../course0102/component/subject-form/subject-form.component';
import { ListChapterComponent } from '../../../course0103/list-chapter/list-chapter.component';
import { TabStandardComponent } from '../../../standard/tabStandard.component';
import { SubjectMgtFormComponent } from '../subject-mgt-form/subject-mgt-form.component';

@Component({
  selector: 'app-subject-mgt-detail',
  templateUrl: './subject-mgt-detail.component.html',
  styleUrls: ['./subject-mgt-detail.component.css']
})
export class SubjectMgtDetailComponent implements OnInit, AfterViewInit {
  courseId: any;
  subjectId: any;
  oldSubjectId: any;
  isNew: any;
  col: any;
  subject: any = {
    editSubjectForm: null,
    editListChapter: null,
    editStand: null
  }

  @ViewChild('subjectForm')
  subjectForm!: SubjectMgtFormComponent
  @ViewChild('chapterList')
  chapterList!: ListChapterComponent
  @ViewChild('standard')
  standard!: TabStandardComponent

  constructor(
    private route: ActivatedRoute,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    private course0102Service: Course0102Service,
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.oldSubjectId = this.subjectId;
      this.subjectId = params['subjectId'];
      this.getSubjectHistory();
    });
  }

  ngAfterViewInit(): void { //case current in subject screen go to other subject
    this.route.queryParams.subscribe((params) => {
      if (this.oldSubjectId != undefined && this.oldSubjectId != params['subjectId']) {
        this.subjectForm.getSubjectHistory(this.subjectId);
        this.chapterList.init();
      }
      const tab = params['tab'];
      if (tab) {
        this.changeTab(tab);
      }
    });
  }

  getSubjectHistory() {
    this.course0102Service.getSubjectHistory(this.subjectId).toPromise().then((response) => {
      this.subject = response[CommonConstant.DETAIL_KEY];
    });
  }

  changeTab(tabNumber: any) {
    if (this.subjectId == "") {
      let mes = this.trans.instant('subject.info.init.required');
      this.confirmationService.confirm({
        header: mes,
        acceptLabel: this.trans.instant('button.confirm.title'),
        accept: () => {
          // tabNumber=1;
        },

      });
      tabNumber = 1;
    }
    const ele = document.getElementById('tabChapter' + tabNumber);

    ele?.click();
  }

  async getDataByViewType(event: any) {
    if (event == "PUBLIC") {
      await this.subjectForm.getSubject(this.subjectId);
      this.subjectForm.checkView = true;
      await this.chapterList.onSearchMain();
      this.chapterList.checkView = true;
      this.standard.getDataByViewType(event);
    } else {
      await this.subjectForm.getSubjectHistory(this.subjectId);
      this.chapterList.onSearch();
      this.standard.getDataByViewType(event);
    }
  }
}
