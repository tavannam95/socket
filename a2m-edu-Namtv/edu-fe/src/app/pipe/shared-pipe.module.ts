import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HasRolePipe } from './has-role.pipe';
import { CheckRolePipe } from './check-role.pipe';
import { RowStatusPipe } from './row-status.pipe';
import { YesNoPipe } from './yesNo.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    HasRolePipe,
    CheckRolePipe,
    RowStatusPipe,
    YesNoPipe,

  ],
  exports: [
    HasRolePipe,
    CheckRolePipe,
    RowStatusPipe,
    YesNoPipe,
  ]
})
export class SharedPipeModule { }
