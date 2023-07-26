import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {MatExpansionModule} from '@angular/material/expansion';
import { IqTestPublicService } from 'src/app/services/quesIq/iqTestPublic.service';
import { CommonsModule } from 'src/app/generator/commons/commons.module';
import { iqTestPublicRoutingModule } from './iqTest-public-routing.module';
import { IqTestPublicComponent } from './iqTest-public.component';
import { CreateUserComponent } from './createUser/createUser.component';
import { ConfirmationService } from 'primeng/api';

@NgModule({
  declarations: [
    IqTestPublicComponent,
    CreateUserComponent,
  ],
  imports: [
    iqTestPublicRoutingModule,
    SharedModule,
    CommonsModule,
    CommonModule,
    MatExpansionModule,
  ],
  providers: [
    IqTestPublicService,
    ConfirmationService,
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
  ]
})
export class IQTestPublicModule { }
