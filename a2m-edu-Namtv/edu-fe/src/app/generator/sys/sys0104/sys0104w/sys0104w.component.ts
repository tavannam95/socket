import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { Sys0104Service } from 'src/app/services/sys/sys0104.service';

@Component({
  selector: 'app-sys0104w',
  templateUrl: './sys0104w.component.html',
  styleUrls: ['./sys0104w.component.css']
})
export class Sys0104wComponent implements OnInit {
  tccoStd: any = {};
  tccoStds: any = [];
  isUpdate?: boolean;
  request: any = {};
  parents: any[] = [];
  parentTccoStd: any ={};
  dateValue: any;

  constructor(
    private toastr: ToastrService,
    private sys0104Service: Sys0104Service,
    private trans: TranslateService,
    public dialogRef: MatDialogRef<Sys0104wComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    this.isUpdate = false;
    this.request.upCommCd = "";
    if (this.data && this.data.tccoStd) {
      this.tccoStd = this.data.tccoStd
    }
    if (this.data && this.data.tccoStds) {
      this.tccoStds = this.data.tccoStds
    }
    if (this.data && this.data.parentTccoStd) {
      this.parentTccoStd = this.data.parentTccoStd
    }
    this.setDataParent();
  }

  setDataParent() {
    this.parents = [];
    this.request = this.tccoStd;
    this.request.upCommCd = this.parentTccoStd.commCd;
    this.tccoStds.forEach((ele: any) => {
      this.parents.push(ele);
    });
    this.parents.map(ele => {
      ele.label = ele.commCd + " - " + ele.commNm;
      ele.value = ele.commCd
    })
    this.parents.unshift({
      label: "None",
      value: ""
    })
  }

  onSave(invalid: any) {
    if (invalid) {
      this.toastr.error(this.trans.instant('message.requiredMessage'));
      return;
    }
    if (!this.tccoStd.commCd) {
      this.sys0104Service.create(this.request).subscribe({
        next: (response) => {
          this.toastr.success(this.trans.instant('message.success.updateSuccess'));
          this.dialogRef.close(response);
        }, 
        error: error => {
          this.toastr.error(this.trans.instant('message.error.saveFailed'));
        }
      }
        
      );

    } else {
      // update case
      this.sys0104Service.update(this.request).subscribe({
        next: response => {
          this.toastr.success(this.trans.instant('message.success.updateSuccess'));
          this.dialogRef.close(response);
        }, 
        error: error => {
          this.toastr.error(this.trans.instant('message.error.saveFailed'));
        }
      });
    }

  }

  onCancel() {
    this.dialogRef.close();
  }

  changeSelect(event: any) {
    this.request.upCommCd = event;
  }

}
