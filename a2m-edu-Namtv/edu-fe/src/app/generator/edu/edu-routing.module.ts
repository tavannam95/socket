import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/guard/role.guard';
import { PageProfileComponent } from '../gen/gen0301/page-profile/page-profile.component';
import { ListStudentComponent } from './edu0101/list-student/list-student.component';
import { ListCourseComponent } from './edu0102/list-course/list-course.component';
import { RoomListComponent } from './edu0103/room-list/room-list.component';
import { ListEquipmentComponent } from './edu0104/list-equipment/list-equipment.component';
import { ListTeacherComponent } from './edu0105/list-teacher/list-teacher.component';
import { ClassFormComponent } from './edu0201/class-form/class-form.component';
import { ListClassComponent } from './edu0201/list-class/list-class.component';
import { TabClassComponent } from './edu0201/tab-class/tab-class.component';
import { ListCandidateComponent } from './edu0202/list-candidate/list-candidate.component';
import { ListEventComponent } from './edu0203/list-event/list-event.component';
import { ScheduleFormComponent } from './edu0204/component/schedule-form/schedule-form.component';
import { ListScheduleComponent } from './edu0204/list-schudle/list-schedule.component';
import { TabScheduleComponent } from './edu0204/tabSchedule/tabSchedule.component';
import { MyClassComponent } from './my-class/my-class.component';


const routes: Routes = [
  {
    path: '',
    children: [
      { path: '', redirectTo: 'edu0101', pathMatch: 'full' },

      { path: 'edu0101', component: ListStudentComponent, canActivate: [RoleGuard] },
      { path: 'edu0102', component: ListCourseComponent, canActivate: [RoleGuard] },
      { path: 'edu0103', component: RoomListComponent, canActivate: [RoleGuard]},
      { path: 'edu0104', component: ListEquipmentComponent, canActivate: [RoleGuard]},

      // { path: 'sys0104', component: Sys0104Component, canActivate: [RoleGuard]},
      { path: 'edu0105', component: ListTeacherComponent, canActivate: [RoleGuard]},
      { path: 'edu0201', component: ListClassComponent, canActivate: [RoleGuard]},
      { path: 'edu0202', component: ListCandidateComponent, canActivate: [RoleGuard]},
      { path: 'edu0203', component: ListEventComponent, canActivate: [RoleGuard]},
      { path: 'edu0201Form', component: TabClassComponent, canActivate: [RoleGuard]},
      { path: 'edu0204', component: TabScheduleComponent, canActivate: [RoleGuard]},
      { path: 'edu0204Form', component: ScheduleFormComponent, canActivate: [RoleGuard]},
      { path: 'myclass', component: MyClassComponent, canActivate: [RoleGuard]},
    ]

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EduRoutingModule { }
