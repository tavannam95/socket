import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentsRoutingModule } from './students-routing.module';
import { Students0101Component } from './students0101/students0101.component';
import { Students0102Component } from './students0102/students0102.component';
import { CommonsModule } from 'src/app/commons/commons.module';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [
    Students0101Component,
    Students0102Component
  ],
  imports: [
    CommonModule,
    StudentsRoutingModule,
    CommonsModule,
    TranslateModule
  ]
})
export class StudentsModule { }
