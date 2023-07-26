import { CommonsModule } from './../../commons/commons.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoursesRoutingModule } from './courses-routing.module';
import { Courses0101Component } from './courses0101/courses0101.component';
import {TranslateModule} from "@ngx-translate/core";
import { Courses0102Component } from './courses0102/courses0102.component';
import { EventCourseComponent } from './eventCourse/eventCourse.component';




@NgModule({
  declarations: [
    Courses0101Component,
    EventCourseComponent,
    Courses0102Component
  ],
    imports: [
        CommonModule,
        CommonsModule,
        CoursesRoutingModule,
        TranslateModule
    ]
})
export class CoursesModule { }
