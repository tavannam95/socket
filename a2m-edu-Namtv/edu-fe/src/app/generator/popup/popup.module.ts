import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/shared/shared.module';
import { VerifyEmailPopupComponent } from './verify-email-popup/verify-email-popup.component';
import { TwoFaPopupComponent } from './two-fa-popup/two-fa-popup.component';
import { ModifyEmailPopupComponent } from './modify-email-popup/modify-email-popup.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { NgxQRCodeModule } from '@techiediaries/ngx-qrcode';
import { ChangePasswordComponent } from './change-password/change-password.component';



@NgModule({
  declarations: [
    VerifyEmailPopupComponent,
    TwoFaPopupComponent,
    ModifyEmailPopupComponent,
    ChangePasswordComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ConfirmDialogModule,
    SharedModule,
    NgxQRCodeModule
  ],
  providers: [ConfirmationService],
})
export class PopupModule {}
