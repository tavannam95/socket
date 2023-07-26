import { ToastrService } from 'ngx-toastr';

import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  Inject,
  Input,
} from '@angular/core';

import { DatePipe, formatDate } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonConstant } from 'src/app/constants/common.constant';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonService } from 'src/app/services/common/common.service';
import { TranslateService } from '@ngx-translate/core';
import { IqTestService } from 'src/app/services/quesIq/iqTest.service';
import { FileUploadService } from 'src/app/services/file/file-upload.service';
import { IqTest } from 'src/app/model/iqTest/iqTest';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-iqTestByQuesList-form',
  templateUrl: './iqTestByQuesList-form.component.html',
  styleUrls: ['./iqTestByQuesList-form.component.css']
})
export class IqTestByQuesListComponent implements OnInit {

  public get data(): any {
    return this._data;
  }
  public set data(value: any) {
    this._data = value;
  }

  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  categorys : any = {};

  submitted: any = false;
  iqTest: any = {};
  iqTestId: any;
  pdfFilePath : any = {};
  iqQues: any = {};
  listIqTestMap: any[] =[];
  listIqQues: any[] =[];
  listChecked!: any[];
  constructor(
    private tccoStdService: TccoStdService,
    private iqTestService: IqTestService,
    private toastrService: ToastrService,
    public datepipe: DatePipe,
    private route : ActivatedRoute,
    private router: Router,
    private fileUploadService :FileUploadService,
    private trans: TranslateService,
    private commonService: CommonService,
    private confirmationService: ConfirmationService,
    public dialogRef: MatDialogRef<IqTestByQuesListComponent>,
    @Inject(MAT_DIALOG_DATA) private _data: any,
  ) {}

  ngOnInit(): void {
    this.listChecked = this._data.listChecked;
    this.initData();
  }
  initData(){
    this.iqTest = new IqTest();
    this.iqTest.listQues = this.listChecked;
    this.iqTest.status = true;
    this.iqTest.saveForListQues = true;
  }

  saved(iqTestId: any) {
    this.router.navigate(['/ques/ques0102/iqTestView'], {
      queryParams: {
        iqTestId : iqTestId,
      },
    });
  }

  onSave(invalid: any) {
    if (invalid) {
      return;
    }
    if (!this.iqTest.key) {
      //create
      this.iqTestService.save(this.iqTest).subscribe({
        next: (response) => {
          this.iqTest.key = response[CommonConstant.KEY];
          this.toastrService.success(this.trans.instant('message.success.saveSuccess'));
          this.dialogRef.close(this.iqTest);
          //this.saved(response[CommonConstant.KEY]);
          this.Submit(response[CommonConstant.KEY]);
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.error.saveFailed'));
        },
      });
    }else{
      //update
      this.iqTestService.save(this.iqTest).subscribe({
        next: (response) => {
          this.toastrService.success(this.trans.instant('message.success.updateSuccess'));
        },
        error: () => {
          this.toastrService.error(this.trans.instant('message.success.updateFailed'));
        },
      });
    }
  }

  onCancel(){}

  Submit(iqTestId: any){
    this.confirmationService.confirm({
      header: this.trans.instant('iqTest.ques.submit.header'),
      message: this.trans.instant('iqTest.ques.submit.message'),
      acceptLabel: this.trans.instant('button.confirm.title'),
	    rejectLabel: this.trans.instant('button.cancel.title'),
      accept: () => {
        this.saved(iqTestId);
      },
      reject: () => {
        this.moveIqTestList();
        return
      },
    });
  }
  moveIqTestList(){
    this.router.navigate(['/ques/ques0102'], {});
  }
}

