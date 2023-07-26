import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { CommonsModule } from '../commons/commons.module';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CalendarModule } from 'primeng/calendar';


import { CKEditorModule } from 'ckeditor4-angular';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { MultiUploadComponent } from '../file/multi-upload/multi-upload.component';
import { FileModule } from '../file/file.module';
import {MatExpansionModule} from '@angular/material/expansion';
import { GetpdfService } from 'src/app/services/file/pdf.service'
import { IqTestService } from 'src/app/services/quesIq/iqTest.service';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { Course0101Service } from 'src/app/services/course/course0101.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { IqQuesFormComponent } from './quesIQ/ques-form/ques-form.component';
import { ContentQuesComponent } from './quesIQ/ques-form/content-ques/content-ques.component';
import { IqTestViewComponent } from './iqTestSet/iqTest-view/iqTest-view.component';
import { IqTestSetListComponent } from './iqTestSet/iqTest-list/iqTestSet-list.component';
import { IqTestFormComponent } from './iqTestSet/iqTest-form/iqTest-form.component';
import { IqQuesService } from 'src/app/services/quesIq/iqQues.service';
import { IqTestByQuesListComponent } from './quesIQ/ques-list/iqTestByQuesList-form/iqTestByQuesList-form.component';
import { IqQuesListComponent } from './quesIQ/ques-list/ques-list.component';
import { IqTestStatisticalComponent } from './iqTestSet/iqTest-Statistical/iqTest-statistical.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { ChartModule } from 'primeng/chart';
import { IqtestStatisticalResultComponent } from './iqTestSet/iqtest-statistical-result/iqTest-statistical-result.component';
import { IqTestStatisticalResultStudentComponent } from './iqTestSet/iqtest-statistical-result/iqTeststatistical-result-student/iqTeststatistical-result-student.component';
import { IqTestStatisticalResultQuestionComponent } from './iqTestSet/iqtest-statistical-result/iqTeststatistical-result-question/iqTeststatistical-result-question.component';
import { QuesIqRoutingModule } from './quesIq-routing.module';
import { IqTestPublicService } from 'src/app/services/quesIq/iqTestPublic.service';
import { ClipboardModule } from '@angular/cdk/clipboard';
import { ProgressComponent } from './iqTestSet/iqTest-view/progress/progress.component';

@NgModule({
  declarations: [
    IqQuesFormComponent,
    IqTestFormComponent,
    IqQuesListComponent,
    IqTestViewComponent,
    IqTestSetListComponent,
    ContentQuesComponent,
    IqTestByQuesListComponent,
    IqTestStatisticalComponent,
    IqtestStatisticalResultComponent,
    IqTestStatisticalResultStudentComponent,
    IqTestStatisticalResultQuestionComponent,
    ProgressComponent,
    // CanvasJSChart
    //CarouselHolderComponent
  ],
  imports: [
    QuesIqRoutingModule,
    SharedModule,
    CommonsModule,
    CommonModule,
    CalendarModule,
    CKEditorModule,
    MatExpansionModule,
    FileModule,
    NgApexchartsModule,
    ChartModule,
    ClipboardModule,
    NgMultiSelectDropDownModule.forRoot()
  ],
  providers: [
    // IqTestPublicService,
    IqTestService,
    IqQuesService,
    Edu0102Service,
    Course0101Service,
    Course0102Service,
    Course0103Service,
    QuizService,
    GetpdfService,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
  ]
})
export class QuesIqModule { }
