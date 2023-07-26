import { NgModule } from '@angular/core';
import { CourseRoutingModule } from './course-routing.module';
import { ListSubjectComponent } from './course0102/list-subject/list-subject.component';
import { SubjectFormComponent } from './course0102/component/subject-form/subject-form.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { CommonsModule } from '../commons/commons.module';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CalendarModule } from 'primeng/calendar';
import { ContentEditerComponent } from './course0102/component/content-editer/content-editer.component';
import { ContentChapterComponent } from './course0103/component/content-chapter/content-chapter.component';
import { ListChapterComponent } from './course0103/list-chapter/list-chapter.component';
import { ChapterFormComponent } from './course0103/component/chapter-form/chapter-form.component';

import { CKEditorModule } from 'ckeditor4-angular';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';
import { ListLectureComponent } from './course0101/list-lecture/list-lecture.component';
import { LectureFormComponent } from './course0101/component/lecture-form/lecture-form.component';
import { Course0101Service } from 'src/app/services/course/course0101.service';

import { CourseFormComponent } from '../edu/edu0102/component/course-form/course-form.component';
import { TabSubjectComponent } from './course0102/tabSubject/tabSubject.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { FileModule } from '../file/file.module';
import { Course0201Component } from './course0201/course0201.component';
import { TabChapterComponent } from './course0103/tabChapter/tabChapter.component';
import { TabLectureComponent } from './course0101/tabLecture/tabLecture.component';
import { CoursePreviewComponent } from './course0201/course-preview/course-preview.component';
import { LecturePreviewComponent } from './course0201/lecture-preview/lecture-preview.component';
import { ListChooseSubjectComponent } from './course0102/tabSubject/list-choose-subject/list-choose-subject.component';
import { TabLectureDetailComponent } from './course0101/tab-lecture-detail/tab-lecture-detail.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { GetpdfService } from 'src/app/services/file/pdf.service';
import { ListChooseChapterComponent } from './course0103/tabChapter/list-choose-chapter/list-choose-chapter.component';
import { QuizListComponent } from './course0104/quiz/quiz-list/quiz-list.component';
import { QuizFormComponent } from './course0104/quiz/quiz-form/quiz-form.component';
import { QuizService } from 'src/app/services/course/quiz.service';
import { ScrollService } from 'src/app/services/common/scroll.service';
import { CommunityModule } from '../community/community.module';
import { QuizStatisticalResultComponent } from './course0104/quiz/quiz-statistical-result/quiz-statistical-result.component';
import { CourseInfoComponent } from './course0102/tabSubject/course-info/course-info.component';
import { StatisticalResultStudentComponent } from './course0104/quiz/quiz-statistical-result/statistical-result-student/statistical-result-student.component';
import { StatisticalResultQuestionComponent } from './course0104/quiz/quiz-statistical-result/statistical-result-question/statistical-result-question.component';
import { CourseInfoService } from 'src/app/services/course/course-info.service';
import { CourseProgramService } from 'src/app/services/course/course-program.service';
import { TagsModule } from '../tags/tags.module';
import { NgApexchartsModule } from "ng-apexcharts";
import * as CanvasJSAngularChart from '../../../assets/canvasjs.angular.component';
import { ChartModule } from 'primeng/chart';
import { SubjectScheduleComponent } from './course0103/subject-schedule/subject-schedule.component';
import { SubjectScheduleTableComponent } from './course0103/subject-schedule/component/subject-schedule-table/subject-schedule-table.component';
import { TabStandardComponent } from './standard/tabStandard.component';
import { KnowledgeFormComponent } from './standard/standKnowledge/knowledge-form.component';
import { QualityFormComponent } from './standard/standQuality/quality-form.component';
import { SkillFormComponent } from './standard/standSkill/skill-form.component';
import { StandardService } from 'src/app/services/course/standard.service';
import { StandardSummaryTableComponent } from './standard/standard-summary-table/standard-summary-table.component';
import { Edu0105Service } from 'src/app/services/edu/edu0105.service';
import { ApproveService } from 'src/app/services/common/approve.service';
import { ChapterPreviewComponent } from './course0201/chapter-preview/chapter-preview.component';
import { SubjectMgtComponent } from './course0105/subject-mgt/subject-mgt.component';
import { SubjectMgtFormComponent } from './course0105/subject-mgt/subject-mgt-form/subject-mgt-form.component';
import { SubjectMgtDetailComponent } from './course0105/subject-mgt/subject-mgt-detail/subject-mgt-detail.component';
import { ListCloneSubjectComponent } from './course0105/list-clone-subject/list-clone-subject.component';
import { CourseStatisticalComponent } from './course0201/course-statistical/course-statistical.component';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { HistoryQuizStudentComponent } from './course0104/quiz/quiz-statistical-result/history_quiz_stu/history_quiz_stu.component';
import { QuizViewSubmittedComponent } from './course0104/quiz/quiz-view-submitted/quiz-view-submitted.component';
import { QuizViewComponent } from './course0104/quiz-view/quiz-view.component';
import { QuizViewSubmittedStuComponent } from './course0104/quiz/quiz-view-submittedStu/quiz-view-submittedStu.component';
var CanvasJSChart = CanvasJSAngularChart.CanvasJSChart;

@NgModule({
  declarations: [
    ListSubjectComponent,
    SubjectFormComponent,
    ContentEditerComponent,
    ListLectureComponent,
    LectureFormComponent,
    ListChapterComponent,
    ChapterFormComponent,
    CourseFormComponent,
    TabSubjectComponent,
    TabChapterComponent,
    TabLectureComponent,
    ContentChapterComponent,
    Course0201Component,
    CoursePreviewComponent,
    LecturePreviewComponent,
    ListChooseSubjectComponent,
    ListChooseChapterComponent,
    CourseStatisticalComponent,
    TabLectureDetailComponent,
    QuizListComponent,
    QuizFormComponent,
    QuizViewComponent,
    QuizViewSubmittedComponent,
    QuizViewSubmittedStuComponent,
    QuizStatisticalResultComponent,
    CourseInfoComponent,
    StatisticalResultStudentComponent,
    HistoryQuizStudentComponent,
    StatisticalResultQuestionComponent,
    CanvasJSChart,
    SubjectScheduleComponent,
    SubjectScheduleTableComponent,
    TabStandardComponent,
    KnowledgeFormComponent,
    QualityFormComponent,
    SkillFormComponent,
    StandardSummaryTableComponent,
    ChapterPreviewComponent,
    SubjectMgtComponent,
    SubjectMgtFormComponent,
    SubjectMgtDetailComponent,
    ListCloneSubjectComponent
  ],
  imports: [
    CourseRoutingModule,
    SharedModule,
    CommonsModule,
    CommonModule,
    CalendarModule,
    CKEditorModule,
    MatExpansionModule,
    FileModule,
    CommunityModule,
    TagsModule,
    NgApexchartsModule,
    ChartModule,
    NgMultiSelectDropDownModule.forRoot()
  ],
  providers: [
    Course0101Service,
    Course0102Service,
    Edu0102Service,
    Edu0101Service,
    Course0103Service,
    QuizService,
    GetpdfService,
    ScrollService,
    CourseInfoService,
    CourseProgramService,
    StandardService,
    Edu0105Service,
    ApproveService,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
  ]
})
export class CourseModule { }
