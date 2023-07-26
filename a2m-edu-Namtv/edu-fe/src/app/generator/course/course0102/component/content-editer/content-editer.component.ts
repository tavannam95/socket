import { Component, OnInit, ViewChild } from '@angular/core';
@Component({
  selector: 'app-content-editer',
  templateUrl: './content-editer.component.html',
  styleUrls: ['./content-editer.component.css']
})
export class ContentEditerComponent implements OnInit {

  name = 'ng2-ckeditor';
  // ckeConfig: CKEDITOR.config | undefined;
  mycontent: string | undefined;
  log: string = '';
  // @ViewChild("myckeditor") ckeditor: CKEditorComponent | undefined;

  ckeditorContent: string = '<p>Some html</p>';
  constructor() {
    this.mycontent = `<p>My html content</p>`;
  }

  ngOnInit(): void {
    // this.ckeConfig = {
    //   allowedContent: false,
    //   extraPlugins: 'divarea',
    //   forcePasteAsPlainText: true,
    //   removePlugins: 'exportpdf'
    // };
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
