import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FileRoutingModule } from './file-routing.module';
import { MultiUploadComponent } from './multi-upload/multi-upload.component';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { TranslateModule } from '@ngx-translate/core';


@NgModule({
  declarations: [
    MultiUploadComponent
  ],
  imports: [
    CommonModule,
    FileRoutingModule,
    TranslateModule,
  ],
  exports:[
    MultiUploadComponent
  ],
  providers: [
    FileUploadService
  ]
})
export class FileModule { }
