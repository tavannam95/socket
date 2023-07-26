import { style } from '@angular/animations';
import { AfterViewInit, Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

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
  constructor(
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
  this.id += 'ckeditor';
  this.maxHeight+= '!important'
  this.maxWidth+= '!important'
  this.staticHtml = this.data.content;
  }

  ngAfterViewInit(): void {

    (document.getElementById(this.id) as any).innerHTML = this.staticHtml;
    (document.getElementById (this.id) as any).style.maxHeight =this.maxHeight;
  }
  onCancel(){
      this.dialog.closeAll();
  }
}
