import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { ToastrService } from 'ngx-toastr';
import { inputs } from '@syncfusion/ej2-angular-diagrams/src/diagram/diagram.component';
import { environment } from 'src/environments/environment';
import { saveAs } from 'file-saver';
import { ConfirmationService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-multi-upload',
  templateUrl: './multi-upload.component.html',
  styleUrls: ['./multi-upload.component.css']
})

//copy of: https://www.bezkoder.com/angular-12-multiple-file-upload/
export class MultiUploadComponent implements OnInit {

  newFileSelected: any[] = [];
  tccoFileResponse: any[] = [];
  status: any[] = [];

  @Input() isCanDownload = true;
  @Input('typeView') typeView = "";
  @Input() isCanUpLoad = true;
  @Input() isCanDelete = true;
  @Input() listTccoFile: any[] = [];
  @Output() childrenCall = new EventEmitter();
  baseUrl = environment.apiURL

  constructor(
    private uploadService: FileUploadService,
    private toastrService: ToastrService,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,

    ) {}

  ngOnInit(): void {

  }

  callFromParent(){
    if(this.newFileSelected.length==0) {
      this.childrenCall.emit({
        data: [],
        listTccoFile: this.listTccoFile
      });
    }else{
      this.uploadFiles();
    }

  }

  addNewFiles(event: any): void {
    var files = event.target.files;
    this.newFileSelected?.push(...files);

    if(files.length==1){
      let obj: any  = {};
      obj.status = "Pending";
      this.status.push(obj);
    }else if(files.length>1){
      for(let i = 0; i< files.length; i++){
        let obj: any  = {};
        obj.status = "Pending";
        this.status.push(obj);
      }
    }
  }

 uploadFiles(): void {
    (this.uploadService.multiFileUpload(this.newFileSelected)).subscribe(
      {
        next: (response) => {
          this.tccoFileResponse = response;
          this.childrenCall.emit({
            data: this.tccoFileResponse,
            listTccoFile: this.listTccoFile
          });
          this.newFileSelected = [];
          //this.toastrService.success('Success', ' Uploaded !');
        },
        error: () => {
          this.toastrService.error('Failed', ' Uploaded File Failed!');
          //this.status[idx].status = "Upload Failed";
        }
      });
  }

  deleteFileExist(id: any, event: any, index: any){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.listTccoFile[index].crud = "D";
      },
      reject: () => {

      },
    });

  }


   deleteFileNew(id: any, event: any, index: any){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.newFileSelected?.splice(index,1);
      },
      reject: () => {

      },
    });
  }

  downloadFile(tccoFile: any){
    this.uploadService.download(tccoFile)
  }

  // downloadFile(file: any){
  //   this.uploadService.download(file).subscribe({
  //     next: (blob) => {
  //       saveAs(blob, file.fleNm+file.fleTp);
  //       this.toastrService.success('Success', ' Downloaded !');
  //     },
  //     error: () => {
  //       this.toastrService.success('Failed', ' Downloaded  File Failed!');
  //     }
  //   })
  // }
}
