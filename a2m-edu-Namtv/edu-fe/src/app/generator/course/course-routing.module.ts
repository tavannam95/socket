import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/guard/role.guard';
import { TabLectureComponent } from './course0101/tabLecture/tabLecture.component';
import { TabSubjectComponent } from './course0102/tabSubject/tabSubject.component';
import { Course0201Component } from './course0201/course0201.component';
import { TabChapterComponent } from './course0103/tabChapter/tabChapter.component';
import { CoursePreviewComponent } from './course0201/course-preview/course-preview.component';
import { LecturePreviewComponent } from './course0201/lecture-preview/lecture-preview.component';
import { TabLectureDetailComponent } from './course0101/tab-lecture-detail/tab-lecture-detail.component';
import { QuizFormComponent } from './course0104/quiz/quiz-form/quiz-form.component';
import { QuizStatisticalResultComponent } from './course0104/quiz/quiz-statistical-result/quiz-statistical-result.component';
import { ChapterPreviewComponent } from './course0201/chapter-preview/chapter-preview.component';
import { SubjectMgtComponent } from './course0105/subject-mgt/subject-mgt.component';
import { SubjectMgtFormComponent } from './course0105/subject-mgt/subject-mgt-form/subject-mgt-form.component';
import { SubjectMgtDetailComponent } from './course0105/subject-mgt/subject-mgt-detail/subject-mgt-detail.component';
import { CourseStatisticalComponent } from './course0201/course-statistical/course-statistical.component';
import { HistoryQuizStudentComponent } from './course0104/quiz/quiz-statistical-result/history_quiz_stu/history_quiz_stu.component';
import { QuizViewComponent } from './course0104/quiz-view/quiz-view.component';
import { QuizViewSubmittedComponent } from './course0104/quiz/quiz-view-submitted/quiz-view-submitted.component';


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'course0101',
        component: TabLectureComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0101/lecture/:id',
        component: TabLectureDetailComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0101/lecture',
        component: TabLectureDetailComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0101/quizform',
        component: QuizFormComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0102',
        component: TabSubjectComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0103',
        component: TabChapterComponent,
        canActivate: [RoleGuard],
      },

      {
        path: 'course0201',
        component: Course0201Component,
        canActivate: [RoleGuard],
      },

      {
        path: 'course0201Preview',
        component: CoursePreviewComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0201StatisticalCourse',
        component: CourseStatisticalComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0201Lecture',
        component: LecturePreviewComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0201Chapter',
        component: ChapterPreviewComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0201Quiz',
        component: QuizViewComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0201QuizView',
        component: QuizViewSubmittedComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0201QuizHis',
        component: HistoryQuizStudentComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0104/quiz-statistical-result',
        component: QuizStatisticalResultComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0105',
        component: SubjectMgtComponent,
        canActivate: [RoleGuard],
      },
      {
        path: 'course0105/form',
        component: SubjectMgtDetailComponent,
        canActivate: [RoleGuard],
      }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CourseRoutingModule { }
