import {COMMA, ENTER, SPACE} from '@angular/cdk/keycodes';
import {Component, ElementRef, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatChipInputEvent} from '@angular/material/chips';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TagService } from 'src/app/services/tags/tag.service';

@Component({
  selector: 'app-mat-chips-autocomplete-person',
  templateUrl: './mat-chips-autocomplete-person.component.html',
  styleUrls: ['./mat-chips-autocomplete-person.component.css']
})
export class MatChipsAutocompletePersonComponent implements OnInit {
  separatorKeysCodes: number[] = [];
  tagCtrl = new FormControl('');
  filteredTags: Observable<any[]>;
  apiUrl: any = "";
  @Input() input : any = '[]';
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
    this.apiUrl = this.tagService.getApiURL();

    this.tagService.searchUser({}).subscribe({
      next: (response) => {
        this.allTags = response[CommonConstant.LIST_KEY];
        this.genFiltered();
        this.migrateData();
      },
      error: () => {
       
      }
    });
  }

  returnData(){


    this.response.emit({
      data: [],
      arrTag: this.tags,
    })
  }

  migrateData(){
    //valid
    if(!this.input) return;

    let tags = this.input;
    if(tags){
      this.tags = tags.map( (noti: any) =>{

        const obj = this.allTags.find(
          (chip) => chip.userUid == noti.userUid
        );
        let objClone = Object.assign({}, obj);
        objClone.key = noti.key;
        return objClone;
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
    // let value = (event.value || '').trim().replace(/\s+/g, " ").toLocaleLowerCase();
    
    // if(value.length>0){
    //   let firstChar = value.charAt(0).toUpperCase();

    //   let endChar = value.substring(1);
    //   value = firstChar + endChar;
    // }
    
    
    
    
    // // Add our tag
    // if (value) {
    //   const notExist = this.notExistCheck(value);
    //   if(notExist){
    //     let newTag = new PersonModel();
    //     newTag.fullName = value;
    //     this.tags.push(newTag);
    //   }else{
    //     this.alertExist();
    //   }
    // }

    // this.returnData();

    // // Clear the input value
    // event.chipInput!.clear();

    // this.tagCtrl.setValue(null);
  }

  notExistCheck(value :any){
    const tagObj = this.tags.find((chip: any) => chip.userUid == value.userUid);
    
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

  alertExist(){
    alert("đã tồn tại")
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const notExist = this.notExistCheck(event.option.value);
    if(notExist){
      this.tags.push(event.option.value);
    }else{
      this.alertExist();
    }

    this.returnData();

    if(!this.tagInput) return;
    this.tagInput.nativeElement.value = '';
    this.tagCtrl.setValue(null);
  }

  private _filter(value: any): any[] {
    const filterValue = typeof(value)=='string'?value.toLowerCase():value.fullName.toLowerCase();

    return this.allTags.filter((ele: any) => ele.fullName.toLowerCase().includes(filterValue));
  }

  selectPosition(noti: any) {
    if(noti.userType == "TEA") return "giảng viên";
    if(noti.userType == "STU") return "học viên";
    if(noti.userType == "EMP"){
      if(noti.roles.includes(CommonConstant.ROLE_SYS_TEA_ASSIS) ) return "trợ giảng";
      if(noti.roles.includes(CommonConstant.ROLE_SYS_ADMIN) ) return "admin";
    }
    return "";
  }
}

export class PersonModel {
  key: any;
  postId: any;
  fullName: any;
  userUid: any;
  userType: any;
  imgPath: any;
  roles: any;
}