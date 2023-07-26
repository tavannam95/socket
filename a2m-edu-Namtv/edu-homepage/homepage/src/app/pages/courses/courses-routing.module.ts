import { Courses0102Component } from './courses0102/courses0102.component';
import { Courses0101Component } from './courses0101/courses0101.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventCourseComponent } from './eventCourse/eventCourse.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {path: '', component: Courses0101Component},
      {path: 'detail', component: Courses0102Component},
      {path: 'event', component: EventCourseComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoursesRoutingModule { }
