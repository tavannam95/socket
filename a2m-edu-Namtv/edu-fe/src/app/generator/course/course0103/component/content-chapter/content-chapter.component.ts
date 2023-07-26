import { Component, OnInit, ViewChild } from '@angular/core';
@Component({
  selector: 'app-content-chapter',
  templateUrl: './content-chapter.component.html',
  styleUrls: ['./content-chapter.component.css']
})
export class ContentChapterComponent implements OnInit {

  name = 'ng2-ckeditor';
  mycontent: string | undefined;
  log: string = '';

  ckeditorContent: string = '<p>Some html</p>';
  constructor() {
    this.mycontent = `<p>My html content</p>`;
  }

  ngOnInit(): void {
  }

  onChange(event: any){

  }

  onEditorChange(event: any){

  }

  onReady(event: any){

  }

  onFocus(event: any){

  }

  onBlur(event: any){

  }

  onContentDom(event: any){

  }

  onFileUploadRequest(event: any){

  }

  onFileUploadResponse(event: any){

  }

  onPaste(event: any){

  }

  onDrop(event: any){

  }
}
