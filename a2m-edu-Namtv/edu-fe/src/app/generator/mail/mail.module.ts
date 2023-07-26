
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { ToastrModule } from 'ngx-toastr';
import { TranslateModule } from '@ngx-translate/core';
import { Mail0106Component } from './mail0106/mail0106.component';
import { ReviewMailSalaryManageComponent } from './mail0106/review-mail-salary-manage/review-mail-salary-manage.component';
import { Mail0105Component } from './mail0105/mail0105.component';
import { OverlayModule } from '@angular/cdk/overlay';
import { SharedModule } from 'src/app/shared/shared.module';
import { CKEditorModule } from 'ckeditor4-angular';
import { RoleGuard } from 'src/app/guard/role.guard';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import {DataViewModule} from 'primeng/dataview';
import { TableModule } from 'primeng/table';
import { TreeTableModule } from 'primeng/treetable';
import { MailVNRoutingModule } from './mail-routing.module';
import { Mail0101Component } from './mail0101/mail0101.component';
import { Mail0102Component } from './mail0102/mail0102.component';
import { Mail0103Component } from './mail0103/mail0103.component';
import { Mail0104Component } from './mail0104/mail0104.component';
import { UIModule } from 'src/app/shared/ui/ui.module';
import { MailComponent } from './mail.component';
import { PopupMailComponent } from './popup-mail/popup-mail.component';

@NgModule({
    declarations: [
        MailComponent,
        Mail0101Component,
        Mail0102Component,
        Mail0103Component,
        Mail0104Component,
        Mail0106Component,
        ReviewMailSalaryManageComponent,
        Mail0105Component,
        PopupMailComponent
    ],
    providers: [
        RoleGuard
    ],
    imports: [
        CommonModule,
        MailVNRoutingModule,
        SharedModule,
        ConfirmDialogModule,
        FormsModule,
        ReactiveFormsModule,
        MatDialogModule,
        ToastrModule,
        SharedModule,
        DataViewModule,
        TableModule,
        TreeTableModule,
        TranslateModule,
        // NgbPaginationModule,
        CKEditorModule,
        // NgbDropdownModule,
        OverlayModule,
        UIModule
    ]
})
export class MailModule {}
