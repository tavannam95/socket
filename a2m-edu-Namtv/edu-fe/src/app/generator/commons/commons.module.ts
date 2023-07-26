import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {ProgressSpinnerModule} from 'primeng/progressspinner';

import { SpinnerComponent } from './spinner/spinner.component';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { PaginatorComponent } from './paginator/paginator.component';
import { PaginatorModule } from 'primeng/paginator';
import { ProgressBarComponent } from './progress-bar/progress-bar.component';
import { DocumentViewComponent } from './document-view/document-view.component';
import { BarMyCourseComponent } from './progress-bar/bar-my-course/bar-my-course.component';
import { SuccessfullComponent } from './modal/successfull/successfull.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CkeditorPreviewComponent } from './ckeditor-preview/ckeditor-preview.component';
import { SharedModule } from 'src/app/shared/shared.module';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { SafeHtmlPipe } from './safeHtml/safe-html.pipe';
import { QuesLstAreaComponent } from './ques-lst-area/ques-lst-area.component';
import { ApprovalHistoryComponent } from './approval-history/approval-history.component';
import { InpVcComponent } from './inp-vc/inp-vc.component';
import { FeedbaackComponent } from './approval-history/feedbaack/feedbaack.component';


@NgModule({
  declarations: [
    SpinnerComponent,
    BreadcrumbComponent,
    ProgressBarComponent,
    BarMyCourseComponent,
    PaginatorComponent,
    DocumentViewComponent,
    SuccessfullComponent,
    CkeditorPreviewComponent,
    SafeHtmlPipe,
    QuesLstAreaComponent,
    ApprovalHistoryComponent,
    InpVcComponent,
    FeedbaackComponent,
  ],
  imports: [
    CommonModule,
    ProgressSpinnerModule,
    PaginatorModule,
    SharedModule,
    MatProgressSpinnerModule,

  ],
  exports: [
    SpinnerComponent,
    BreadcrumbComponent,
    ProgressBarComponent,
    BarMyCourseComponent,
    PaginatorComponent,
    DocumentViewComponent,
    CkeditorPreviewComponent,
    SafeHtmlPipe,
    QuesLstAreaComponent,
    ApprovalHistoryComponent,
    InpVcComponent
  ],
  providers: [
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
  ]
})
export class CommonsModule { }
