import { TranslateModule } from '@ngx-translate/core';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastrModule } from 'ngx-toastr';
import { TableModule } from 'primeng/table';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { PaginatorModule } from 'primeng/paginator';
import { SharedPipeModule } from '../pipe/shared-pipe.module';
import { MatDialogModule } from '@angular/material/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { PanelModule } from 'primeng/panel';
import { InputTextModule } from 'primeng/inputtext';
import { CronEditorModule } from 'ngx-cron-editor';
import { ReactiveFormsModule } from '@angular/forms';
import { TooltipModule } from 'primeng/tooltip';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputMaskModule } from 'primeng/inputmask';
import { CalendarModule } from 'primeng/calendar';
@NgModule({
  declarations: [],
  imports: [CommonModule, FormsModule],
  exports: [
    TranslateModule,
    ToastrModule,
    FormsModule,
    TableModule,
    ConfirmDialogModule,
    PaginatorModule,
    SharedPipeModule,
    MatDialogModule,
    DropdownModule,
    PanelModule,
    InputTextModule,
    ReactiveFormsModule,
    CronEditorModule,
    TooltipModule,
    MultiSelectModule,
    InputMaskModule,
    CalendarModule
  ],
  providers: [],
})
export class SharedModule {}
