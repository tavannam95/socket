import { CommonsModule } from './../../commons/commons.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BlogRoutingModule } from './blog-routing.module';
import { Blog0101Component } from './blog0101/blog0101.component';
import { Blog0102Component } from './blog0102/blog0102.component';
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    Blog0101Component,
    Blog0102Component
  ],
  imports: [
    CommonModule,
    BlogRoutingModule,
    CommonsModule,
    TranslateModule
  ]
})
export class BlogModule { }
