import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { inputs } from '@syncfusion/ej2-angular-diagrams/src/diagram/diagram.component';
import { CommonService } from 'src/app/services/common/common.service';

@Component({
  selector: 'app-card-view-tag',
  templateUrl: './card-view-tag.component.html',
  styleUrls: ['./card-view-tag.component.css']
})
export class CardViewTagComponent implements OnInit, OnChanges {

  @Input() jsonTags: string = "";
  listTag : any[] = [];
  constructor(
    private router: Router,
    private commonService : CommonService
  ) { }
  ngOnChanges(changes: SimpleChanges): void {
    if(this.jsonTags){
      this.listTag = JSON.parse(this.jsonTags);
    }
  }

  ngOnInit(): void {

  }

  onTagClick(tagName: any){

    this.commonService.changeTagName(tagName)

    this.router.navigate(['/searchtag'], {});

    // alert("comming soon")
  }

}
