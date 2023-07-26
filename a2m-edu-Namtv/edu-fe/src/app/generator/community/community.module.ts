import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CommunityRoutingModule } from './community-routing.module';
import { ListPostComponent } from './post/list-post/list-post.component';
import { PostFormComponent } from './post/post-form/post-form.component';
import { PostService } from 'src/app/services/community/post.service';
import { CommonsModule } from '../commons/commons.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { CKEditorModule } from 'ckeditor4-angular';
import { PostDiscussComponent } from './post/post-discuss/post-discuss.component';
import { ReplyFormComponent } from './post/reply-form/reply-form.component';
import { AnswerFormComponent } from './post/answer-form/answer-form.component';
import { ListPostInCourseComponent } from './post/list-post-in-course/list-post-in-course.component';
import { WINDOW_PROVIDERS } from 'src/app/services/window.service ';
import { TagsModule } from '../tags/tags.module';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FormPostFormComponent } from './post/post-form/form-post-form/form-post-form.component';


@NgModule({
  declarations: [
    ListPostComponent,
    PostFormComponent,
    PostDiscussComponent,
    ReplyFormComponent,
    AnswerFormComponent,
    ListPostInCourseComponent,
    FormPostFormComponent
  ],
  imports: [
    CommonModule,
    CommunityRoutingModule,
    CommonsModule,
    SharedModule,
    CKEditorModule,
    TagsModule,
    InfiniteScrollModule
  ],
  providers:[
    PostService,
    WINDOW_PROVIDERS
  ],
  exports: [
    ListPostInCourseComponent,
    ListPostComponent,
    FormPostFormComponent
  ]
})
export class CommunityModule { }
