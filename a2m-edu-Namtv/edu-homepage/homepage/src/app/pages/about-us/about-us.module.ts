import { CommonsModule } from './../../commons/commons.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AboutUsRoutingModule } from './about-us-routing.module';
import { AboutUsComponent } from './about-us.component';
import {TranslateModule} from "@ngx-translate/core";


@NgModule({
  declarations: [
    AboutUsComponent
  ],
    imports: [
        CommonModule,
        AboutUsRoutingModule,
        CommonsModule,
        TranslateModule
    ]
})
export class AboutUsModule { }
