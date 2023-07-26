import { style } from '@angular/animations';
import { AfterViewInit, Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-ckeditor-preview',
  templateUrl: './ckeditor-preview.component.html',
  styleUrls: ['./ckeditor-preview.component.css']
})
export class CkeditorPreviewComponent implements OnInit,AfterViewInit {
  @Input() staticHtml : any ;
  @Input() class : any ;
  @Input() id : any ;
  @Input() maxHeight : any ;
  @Input() maxWidth : any ;
  @Input() overflow : any ;
  constructor() { }

  ngOnInit(): void {
  this.id += 'ckeditor';
  this.maxHeight+= '!important'
  this.maxWidth+= '!important'
  }

  ngAfterViewInit(): void {

    (document.getElementById(this.id) as any).innerHTML = this.staticHtml;
    (document.getElementById (this.id) as any).style.maxHeight =this.maxHeight;

  }

}
