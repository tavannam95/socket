import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PageProfileRoutingModule } from './page-profile-routing.module';
import { CalendarModule } from 'primeng/calendar';
import { PageProfileComponent } from './page-profile.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { FileModule } from '../file/file.module';
import { QuizService } from 'src/app/services/course/quiz.service';
import { CKEditorModule } from 'ckeditor4-angular';
import { CommonsModule } from '../commons/commons.module';


@NgModule({
  declarations: [
    PageProfileComponent
  ],
  imports: [
    CommonModule,
    PageProfileRoutingModule,
    CalendarModule,
    SharedModule,
    FileModule,
    CKEditorModule,
    CommonsModule,

  ],
  providers:[
    QuizService
  ]
})
export class PageProfileModule { }
