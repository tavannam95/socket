import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { TranslateModule } from '@ngx-translate/core';
import { PaginationComponent } from './pagination/pagination.component';
import { MobileSearchComponent } from './mobile-search/mobile-search.component';
import { LatestPostComponent } from './latest-post/latest-post.component';
import { CkeditorPreviewComponent } from './ckeditor-preview/ckeditor-preview.component';
import { SpinnerDialogComponent } from './spinner-dialog/spinner-dialog.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { SafeHtmlPipe } from './safeHtml/safe-html.pipe';

@NgModule({
  declarations: [
    BreadcrumbComponent,
    PaginationComponent,
    MobileSearchComponent,
    LatestPostComponent,
    CkeditorPreviewComponent,
    SpinnerDialogComponent,
    SafeHtmlPipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule,
    MatProgressSpinnerModule
  ],
  exports: [
    BreadcrumbComponent,
    PaginationComponent,
    MobileSearchComponent,
    LatestPostComponent,
    CkeditorPreviewComponent,
    SpinnerDialogComponent,
    SafeHtmlPipe
  ],
})
export class CommonsModule {}
