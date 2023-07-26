import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TagsRoutingModule } from './tags-routing.module';
import { MatChipsAutocompleteComponent } from './mat-chips-autocomplete/mat-chips-autocomplete.component';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatChipsModule } from '@angular/material/chips';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { TagService } from 'src/app/services/tags/tag.service';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { PaginatorModule } from 'primeng/paginator';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonsModule } from '../commons/commons.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { CardViewTagComponent } from './card-view-tag/card-view-tag.component';
import { MatChipsAutocompletePersonComponent } from './mat-chips-autocomplete-person/mat-chips-autocomplete-person.component';

@NgModule({
  declarations: [
    MatChipsAutocompleteComponent,
    CardViewTagComponent,
    MatChipsAutocompletePersonComponent
  ],
  imports: [
    CommonModule,
    CommonsModule,
    TagsRoutingModule,
    ProgressSpinnerModule,
    PaginatorModule,
    SharedModule,
    MatAutocompleteModule,
    MatChipsModule,
    MatInputModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    MatChipsAutocompleteComponent,
    MatChipsAutocompletePersonComponent,
    CardViewTagComponent
  ],
  providers: [
    TagService
  ]
})
export class TagsModule { }
