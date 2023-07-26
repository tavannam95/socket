import { CommunityModule } from './../community/community.module';
import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { EduRoutingModule } from './edu-routing.module';
import { EduComponent } from './edu.component';
import { RoomListComponent } from './edu0103/room-list/room-list.component';
import { Edu0103Service } from 'src/app/services/edu/edu0103.service';

import { ConfirmationService } from 'primeng/api';
import { CalendarModule } from 'primeng/calendar';
import { CheckboxModule } from 'primeng/checkbox';
import { DropdownModule } from 'primeng/dropdown';
import { MenuModule } from 'primeng/menu';
import { TabViewModule } from 'primeng/tabview';
import { SharedPipeModule } from 'src/app/pipe/shared-pipe.module';
import { CommonsModule } from '../commons/commons.module';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SharedModule } from 'src/app/shared/shared.module';
import { ListStudentComponent } from './edu0101/list-student/list-student.component';
import { StudentFormComponent } from './edu0101/component/student-form/student-form.component';
import { TreeTableModule } from 'primeng/treetable';
import { EamStudentInfo } from 'src/app/model/gen/eam-student-info';
import { Edu0101Service } from 'src/app/services/edu/edu0101.service';
import { EquipmentFormComponent } from './edu0104/components/equipment-form/equipment-form.component';
import { ListEquipmentComponent } from './edu0104/list-equipment/list-equipment.component';
import { Edu0104Service } from 'src/app/services/edu/edu0104.service';
import { ListTeacherComponent } from './edu0105/list-teacher/list-teacher.component';
import { TeacherFormComponent } from './edu0105/teacher-form/teacher-form.component';
import { Edu0105Service } from 'src/app/services/edu/edu0105.service';

import { CourseFormComponent } from './edu0102/component/course-form/course-form.component';
import { ListCourseComponent } from './edu0102/list-course/list-course.component';
import { Edu0102Service } from 'src/app/services/edu/edu0102.service';


import { CKEditorModule } from 'ckeditor4-angular';
import { ListTeaComponent } from './edu0201/list-tea/list-tea.component';
import { ListClassComponent } from './edu0201/list-class/list-class.component';
import { ListStuComponent } from './edu0201/list-stu/list-stu.component';
import { ClassFormComponent } from './edu0201/class-form/class-form.component';
import { ListCandidateComponent } from './edu0202/list-candidate/list-candidate.component';
import { CandidateFormComponent } from './edu0202/candidate-form/candidate-form.component';
import { FileModule } from '../file/file.module';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { Course0101Service } from 'src/app/services/course/course0101.service';
import { Course0102Service } from 'src/app/services/course/course0102.service';
import { Course0103Service } from 'src/app/services/course/course0103.service';
import { QuizService } from 'src/app/services/course/quiz.service';
import { EventFormComponent } from './edu0203/event-form/event-form.component';
import { ListEventComponent } from './edu0203/list-event/list-event.component';
import { Edu0203Service } from 'src/app/services/edu/edu0203.service';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { TabClassComponent } from './edu0201/tab-class/tab-class.component';
import { ClassAnnouncementComponent } from './edu0201/class-announcement/class-announcement.component';
import { ListClassAnnouncementComponent } from './edu0201/list-class-announcement/list-class-announcement.component';
import { StudentFormExcelComponent } from './edu0101/component/student-form-excel/student-form-excel.component';
import { ScheduleFormComponent } from './edu0204/component/schedule-form/schedule-form.component';
import { ListScheduleComponent } from './edu0204/list-schudle/list-schedule.component';
import { Edu0204Service } from 'src/app/services/edu/edu0204.service';

import { ListClassPostComponent } from './edu0201/list-class-post/list-class-post.component';

import { TabScheduleComponent } from './edu0204/tabSchedule/tabSchedule.component';
import { ScheduleCalendarComponent } from './edu0204/tabSchedule/ScheduleCalendar/ScheduleCalendar.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { ProgressComponent } from './edu0202/progress/progress.component';
import { MyClassComponent } from './my-class/my-class.component';
import { ClassNotificationComponent } from './class-notification/class-notification.component';
import { ScheduleFormExcelComponent } from './edu0204/component/schedule-form-excel/schedule-form-excel.component';


@NgModule({
  declarations: [
    EduComponent,
    RoomListComponent,
    ListStudentComponent,
    StudentFormComponent,
    EquipmentFormComponent,
    ListEquipmentComponent,
    ListTeacherComponent,
    TeacherFormComponent,
    ListCourseComponent,
    //CourseFormComponent,
    ListClassComponent,
    ClassFormComponent,
    StudentFormExcelComponent,
    ScheduleFormExcelComponent,
    ScheduleFormComponent,
    ListScheduleComponent,
    ListStuComponent,
    ListTeaComponent,
    ListCandidateComponent,
    CandidateFormComponent,
    EventFormComponent,
    ListEventComponent,
    TabClassComponent,
    ClassAnnouncementComponent,
    ListClassAnnouncementComponent,

    ListClassPostComponent,

    TabScheduleComponent,
    ScheduleCalendarComponent,
    ProgressComponent,
    MyClassComponent,
    ClassNotificationComponent
  ],
  imports: [
    CommunityModule,
    CommonModule,
    EduRoutingModule,
    TreeTableModule,
    DropdownModule,
    TabViewModule,
    CheckboxModule,
    SharedModule,
    CommonsModule,
    SharedPipeModule,
    CalendarModule,
    MenuModule,
    CKEditorModule,
    FileModule,
    MatCheckboxModule,
    FullCalendarModule,
  ],
  providers: [
    ConfirmationService,
    Edu0103Service,
    Edu0101Service,
    Edu0104Service,
    Edu0105Service,
    EamStudentInfo,
    Edu0104Service,
    Edu0102Service,
    Course0101Service,
    Edu0203Service,
    Edu0204Service,
    Course0102Service,
    Course0103Service,
    QuizService,
    FileUploadService,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
  ],
  exports: [
    ClassNotificationComponent
  ]

})
export class EduModule { }
