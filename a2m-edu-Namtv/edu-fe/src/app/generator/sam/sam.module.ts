import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SamRoutingModule } from './sam-routing.module';
import { Sam0101Component } from './sam0101/sam0101.component';
import { Sam0102Component } from './sam0102/sam0102.component';
import { FormsModule } from '@angular/forms';
import { CalendarModule } from 'primeng/calendar';
import { TranslateModule } from '@ngx-translate/core';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { PaginatorModule } from 'primeng/paginator';
import { CommonsModule } from '../commons/commons.module';
import { TooltipModule } from 'primeng/tooltip';
import {InputMaskModule} from 'primeng/inputmask';

@NgModule({
  declarations: [
    Sam0101Component,
    Sam0102Component
  ],
  imports: [
    CommonModule,
    SamRoutingModule,
    FormsModule,
    CalendarModule,
    TranslateModule,
    DropdownModule,
    MultiSelectModule,
    PaginatorModule,
    CommonsModule,
    TooltipModule,
    InputMaskModule
  ]
})
export class SamModule { }
