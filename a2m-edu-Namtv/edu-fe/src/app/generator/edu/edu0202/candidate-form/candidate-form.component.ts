import { DatePipe } from '@angular/common';
import { HttpHeaders } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { CommonConstant } from 'src/app/constants/common.constant';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { RequestParam } from 'src/app/model/common/request-param';
import { Edu0202Service } from 'src/app/services/edu/edu0202.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { ApiUrlUtil } from 'src/app/utils/api-url.util';
import { HeadersUtil } from 'src/app/utils/headers.util';
import { LangUtil } from 'src/app/utils/lang.util';
import { ParamUtil } from 'src/app/utils/param.util';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-candidate-form',
  templateUrl: './candidate-form.component.html',
  styleUrls: ['./candidate-form.component.css']
})
export class CandidateFormComponent implements OnInit {


  submitted: any = false;
  candidate : any = {};
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private edu0202Service: Edu0202Service,

    public dialogRef: MatDialogRef<CandidateFormComponent>,
    private toastrService: ToastrService,
    private uploadService: FileUploadService,
    public dialog: MatDialog,
  ) {}

  ngOnInit(): void {
    this.candidate.infoFile = new Object();
    this.candidate.infoFile.fleNm = null;
    if (this.data.candidate!=null || this.data.candidate != undefined || this.data.candidate!='') {
      this.edu0202Service.getCandidateById(this.data.candidate).subscribe( (response) =>  {
        if(response.list){
          this.candidate = response.list
          // this.candidate.insDate  = new Date(this.candidate.insDate);

        }
     });
    }
    };

    dowloadFile(file : any){
      const headers: HttpHeaders = HeadersUtil.getHeadersAuth();
      const params: RequestParam[] = ParamUtil.toRequestParams(file);
      const url = ApiUrlUtil.buildQueryString(
        environment.apiURL + '/tcco-files/download',
        params
      );

      let a = document.createElement('a');
      a.href = url;
      a.click();
    }

    onCancel() {

        this.dialogRef.close();

    }
    onSave(invalid:any){
      if(invalid){
        this.toastrService.error('Invalid', 'Check Your Valid !');
        return;
      }

      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});

      this.edu0202Service.save(this.candidate).subscribe( (response) =>  {
        if(response.status=='OK'){
          this.toastrService.success('Success', ' Updated !');
          this.dialogRef.close(this.candidate);
          this.edu0202Service.changeData(this.candidate);
          dialogSpinner.close()
        }
     });
    }

  }






