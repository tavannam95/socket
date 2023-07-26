import {COMMA, ENTER, SPACE} from '@angular/cdk/keycodes';
import {Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatChipInputEvent} from '@angular/material/chips';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TagService } from 'src/app/services/tags/tag.service';

/**
 * @title Chips Autocomplete
 */
@Component({
  selector: 'app-mat-chips-autocomplete',
  templateUrl: './mat-chips-autocomplete.component.html',
  styleUrls: ['./mat-chips-autocomplete.component.css']
})
export class MatChipsAutocompleteComponent implements OnInit, OnChanges{
  separatorKeysCodes: number[] = [ENTER, COMMA, SPACE];
  tagCtrl = new FormControl('');
  filteredTags: Observable<any[]>;

  @Input() tagsJsonInput : any = '[]';
  @Output() response = new EventEmitter();
  
  tags: any[] = [];
  allTags: any[] = []

  @ViewChild('tagInput') tagInput: ElementRef<HTMLInputElement> | undefined;

  constructor(
    private tagService: TagService,
  ) {
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null),
      map(
        (tag: any | null) => (
          tag ? this._filter(tag) : this.allTags.slice()
        )
      ),
    );
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.migrateData();
  }
  
  ngOnInit(): void {
    this.migrateData();

    this.tagService.searchTags({}).subscribe({
      next: (response) => {
        this.allTags = response[CommonConstant.LIST_KEY];
        this.genFiltered();
      },
      error: () => {
       
      }
    });
  }

  returnData(){
    var response = this.tags.map((tag: any)=>{
      return tag.tagName;
    })

    this.response.emit({
      data: [],
      arrTag: response,
    })
  }

  migrateData(){
    //valid json
    if(!this.tagsJsonInput) return;

    let tags = JSON.parse( this.tagsJsonInput);
    if(tags){
      this.tags = tags.map( (tagName: any) =>{
        let newTag = new TagsModel();
        newTag.tagName = tagName;
        return newTag;
      });
    }
  }

  genFiltered(){
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
      startWith(null),
      map(
        (tag: any | null) => (
          tag ? this._filter(tag) : this.allTags.slice()
        )
      ),
    );
  }

  add(event: MatChipInputEvent): void {
    let value = (event.value || '').trim().replace(/\s+/g, " ").toLocaleLowerCase();
    
    if(value.length>0){
      let firstChar = value.charAt(0).toUpperCase();

      let endChar = value.substring(1);
      value = firstChar + endChar;
    }
    
    
    
    
    // Add our tag
    if (value) {
      const notExist = this.notExist(value);
      if(notExist){
        let newTag = new TagsModel();
        newTag.tagName = value;
        this.tags.push(newTag);
      }else{
        this.alertNotExist();
      }
    }

    this.returnData();

    // Clear the input value
    event.chipInput!.clear();

    this.tagCtrl.setValue(null);
  }

  notExist(value :string){
    const tagObj = this.tags.find((ele: any) => ele.tagName.toLowerCase().includes(value.toLocaleLowerCase()));
    
    if(tagObj) {
      return false;
    }
    
    return true;
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }

    this.returnData();
  }

  alertNotExist(){
    alert("đã tồn tại")
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const notExist = this.notExist(event.option.value.tagName);
    if(notExist){
      this.tags.push(event.option.value);
    }else{
      this.alertNotExist();
    }

    this.returnData();

    if(!this.tagInput) return;
    this.tagInput.nativeElement.value = '';
    this.tagCtrl.setValue(null);
  }

  private _filter(value: any): any[] {
    const filterValue = typeof(value)=='string'?value.toLowerCase():value.tagName.toLowerCase();

    return this.allTags.filter((ele: any) => ele.tagName.toLowerCase().includes(filterValue));
  }
}

export class TagsModel {
  tagName: any;
  tagCount: any;
  insDt: any;
  insUid: any;
  updDt: any;
  updUid: any;
}


/**  Copyright 2022 Google LLC. All Rights Reserved.
    Use of this source code is governed by an MIT-style license that
    can be found in the LICENSE file at https://angular.io/license */