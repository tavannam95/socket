import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SignUpRoutingModule } from './sign-up-routing.module';
import { SignUpComponent } from './sign-up.component';
import { FormsModule } from '@angular/forms';
import { SharedPipeModule } from 'src/app/pipe/shared-pipe.module';
import { TranslateModule } from '@ngx-translate/core';
import { CalendarModule } from 'primeng/calendar';


@NgModule({
  declarations: [
    SignUpComponent
  ],
  imports: [
    CommonModule,
    SignUpRoutingModule,
    FormsModule,
    TranslateModule,
    CalendarModule
  ]
})
export class SignUpModule { }
