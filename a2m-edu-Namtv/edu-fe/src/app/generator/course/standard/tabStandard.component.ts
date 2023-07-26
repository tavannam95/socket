import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { StandardSummaryTableComponent } from './standard-summary-table/standard-summary-table.component';
import { KnowledgeFormComponent } from './standKnowledge/knowledge-form.component';
import { QualityFormComponent } from './standQuality/quality-form.component';
import { SkillFormComponent } from './standSkill/skill-form.component';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { StandardService } from 'src/app/services/course/standard.service';

@Component({
  selector: 'app-standard',
  templateUrl: './tabStandard.component.html',
  styleUrls: ['./tabStandard.component.css'],
})
export class TabStandardComponent implements OnInit {
  @Input() listStandard = [];
  @ViewChild('standKnowledge')
  standKnowledge!: KnowledgeFormComponent;
  @ViewChild('standSkill')
  standSkill!: QualityFormComponent;
  @ViewChild('standQuality')
  standQuality!: SkillFormComponent;
  @ViewChild('standSummary')
  standSummary!: StandardSummaryTableComponent;

  courseId: any;
  subjectId: any;

  listKnows: any[] = [];
  listQuas: any[] = [];
  listSkills: any[] = [];
  listStandards: any[] = [];
  listStandardHis: any[] = [];

  couSubjectLst: any[] = [];
  courseObj: any = new Object();
  col: string = 'col-lg-12';
  constructor(
    private route: ActivatedRoute,
    private standardService: StandardService,
    private edu0102Service: Edu0102Service,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    private tccoStdService: TccoStdService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.subjectId =params['subjectId'];
    })
    this.getListStandard();
    this.getListStandardHis();
  }

  ngAfterViewInit() {
    this.route.queryParams.subscribe((params) => {
      const tab = params['tab'];
      if (tab) {
        this.changeTab(tab);
      }
    });
  }

  getListStandard(){
    this.standardService.getListStandBySubjectId(this.subjectId).subscribe((response) => {
      this.listStandard = response[CommonConstant.DETAIL_KEY];
    });
  }

  getListStandardHis(){
    this.standardService.getListStandHistoryBySubjectId(this.subjectId).subscribe((response) => {
      this.listStandardHis = response[CommonConstant.DETAIL_KEY];
    });
  }

  afterSaveStand(event:any){
    this.listStandard = event.listStandard;
    this.listStandardHis = event.listStandardHis;
    // console.log(this.listStandard);
  }

  changeTab(tabNumber: any) {
    if(tabNumber == 4){
      if(this.listStandard.length!=0){
        if(this.listStandard.length < this.listStandardHis.length){
          alert(this.trans.instant('message.alertUnapproverStandards'));
        }else{
          this.listKnows = [];
          this.listQuas = [];
          this.listSkills = [];
          this.listStandards = this.listStandard;
          for(let i = 0; i < this.listStandard.length; i++){
            this.listStandards[i]['index'] = i + 1;
            const standType = this.listStandards[i].standType;
            if(standType == '18-01'){
              this.listKnows.push(this.listStandards[i]);
            }
            if(standType == '18-02'){
              this.listSkills.push(this.listStandards[i]);
            }
            if(standType == '18-03'){
              this.listQuas.push(this.listStandards[i]);
            }
          }
          if(this.listKnows.length == 0){
            alert(this.trans.instant('message.alertNoneKnowledge'));
            tabNumber = 1;
          }
          if(this.listQuas.length == 0){
            alert(this.trans.instant('message.alertNoneQuality'));
            tabNumber = 3;
          }
          if(this.listSkills.length == 0){
            alert(this.trans.instant('message.alertNoneSkill'));
            tabNumber = 2;
          }
          const ele = document.getElementById('tabStandard'+tabNumber);
          ele?.click();
        }
      }else{
        if(this.listStandardHis.length != 0){
          alert(this.trans.instant('message.alertUnapproverStandards'));
        }else{
          alert(this.trans.instant('message.alertNoneStandard'));
        }
        tabNumber = 1;
        const ele = document.getElementById('tabStandard'+tabNumber);
        ele?.click();
      }
    }
  }

  checkInvalidForm() {
    const invalidKnow = this.standKnowledge.checkInvalidForm();
    const invalidSkill = this.standSkill.checkInvalidForm();
    const invalidQuality = this.standQuality.checkInvalidForm();
    return invalidKnow && invalidSkill && invalidQuality;
  }

  onSaveDraft(invalid: any) {
    this.standKnowledge.onSave(invalid);
    this.standSkill.onSave(invalid);
    this.standQuality.onSave(invalid);
  }

  getDataByViewType(viewType : String){
    if(viewType=="PUBLIC"){
      this.standKnowledge.onSearchMain(this.subjectId);
      this.standSkill.onSearchMain(this.subjectId);
      this.standQuality.onSearchMain(this.subjectId);


      this.standSummary.viewMode = "PUBLIC";
      this.standSummary.initData();

    }else{
      this.standKnowledge.onSearchHistory();
      this.standSkill.onSearchHistory();
      this.standQuality.onSearchHistory();

      this.standSummary.viewMode = "PRIVATE";
      this.standSummary.initData();
    }
  }

}
