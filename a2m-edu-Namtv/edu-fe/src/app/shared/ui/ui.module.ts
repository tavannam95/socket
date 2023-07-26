import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoaderComponent } from './loader/loader.component';

@NgModule({
  declarations: [LoaderComponent],
  imports: [
    CommonModule,
    FormsModule
    // NgbProgressbarModule
  ],
  exports: [LoaderComponent]
})
export class UIModule { }
