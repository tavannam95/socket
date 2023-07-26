import { NgModule } from '@angular/core';

import { SearchTagRoutingModule } from './search-tag-routing.module';
import { SearchTagComponent } from './search-tag.component';
import { TranslateModule } from '@ngx-translate/core';
import { CommonModule, DatePipe } from '@angular/common';
import { CommonsModule } from "../../commons/commons.module";
import { PostService } from 'src/app/services/community/post.service';
import { Course0101Service } from 'src/app/services/course/course0101.service';

@NgModule({
    declarations: [
        SearchTagComponent
    ],
    imports: [
        SearchTagRoutingModule,
        TranslateModule,
        CommonModule,
        CommonsModule
    ],
    providers: [
      PostService,
      Course0101Service
    ]
})
export class SearchTagModule { }
