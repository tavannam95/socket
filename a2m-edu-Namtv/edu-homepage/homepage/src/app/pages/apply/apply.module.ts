import { TranslateModule } from '@ngx-translate/core';
import { CommonsModule } from './../../commons/commons.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplyRoutingModule } from './apply-routing.module';
import { ApplyComponent } from './apply.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
@NgModule({
  declarations: [ApplyComponent],
  imports: [
    CommonModule,
    FormsModule,
    ApplyRoutingModule,
    CommonsModule,
    ReactiveFormsModule,
    TranslateModule,
    DropdownModule,
  ],
})
export class ApplyModule {}
