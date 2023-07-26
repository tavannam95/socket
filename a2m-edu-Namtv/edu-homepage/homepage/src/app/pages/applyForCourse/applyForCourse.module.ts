import { TranslateModule } from '@ngx-translate/core';
import { CommonsModule } from '../../commons/commons.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplyRoutingModule } from './applyForCourse-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ApplyForCourseComponent } from './applyForCourse.component';

@NgModule({
  declarations: [ApplyForCourseComponent],
  imports: [
    CommonModule,
    FormsModule,
    ApplyRoutingModule,
    CommonsModule,
    ReactiveFormsModule,
    TranslateModule,
    DropdownModule,
  ],
})
export class ApplyForCourseModule {}
