import { CommonsModule } from './../../commons/commons.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InstructorsRoutingModule } from './instructors-routing.module';
import { Instructors0101Component } from './instructors0101/instructors0101.component';
import { Instructors0102Component } from './instructors0102/instructors0102.component';
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    Instructors0101Component,
    Instructors0102Component
  ],
    imports: [
        CommonModule,
        InstructorsRoutingModule,
        CommonsModule,
        TranslateModule
    ]
})
export class InstructorsModule { }
