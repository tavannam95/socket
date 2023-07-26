import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GuideRoutingModule } from './guide-routing.module';
import { GuideComponent } from './guide.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { CommonsModule } from 'src/app/commons/commons.module';




@NgModule({
  declarations: [
    GuideComponent
  ],
  imports: [
    CommonModule,
    CommonsModule,
    GuideRoutingModule,
    MatExpansionModule,
  ]
})
export class GuideModule { }
