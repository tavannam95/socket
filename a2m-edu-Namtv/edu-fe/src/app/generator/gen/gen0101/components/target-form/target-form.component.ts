import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Gen0101Service } from 'src/app/services/gen/gen0101.service';
import { Component, OnInit, Output, EventEmitter, Inject } from '@angular/core';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Gen0501Service } from 'src/app/services/gen/gen0501.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';

@Component({
  selector: 'app-target-form',
  templateUrl: './target-form.component.html',
  styleUrls: ['./target-form.component.css'],
})
export class TargetFormComponent implements OnInit {
  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  target: any = {};
  connectTypes: any[] = [];
  tableInfoList: any[] = [];
  projects: any[] = [];
  spinner: any = false;
  typeFiles: any[] = [];
  ipPattern = PatternValidate.IP_PATTERN;
  portPattern = PatternValidate.PORT_PATTERN;
  submitted: any = false;

  statusList: any[] = [
    { label: this.trans.instant('common.active'), value: true },
    { label: this.trans.instant('common.deactive'), value: false },
  ];

  constructor(

    private gen0101Service: Gen0101Service,
    private toastrService: ToastrService,
    private tccoStdService: TccoStdService,
    private gen0501Service: Gen0501Service,
    private trans: TranslateService,
    private router: Router,
    public dialogRef: MatDialogRef<TargetFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    console.log(this.data);
    this.target = this.data.target;
    console.log(this.target);
   
    this.initData();
    this.target.project.projectId=this.data.projectId;
  }

  getConnectType() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.CONNECT_TYPE_UP_COMM_CD)
      .subscribe((response) => {
        this.connectTypes = response;
      });
  }

  onSave(invalid: any) {
    if (invalid) {
      this.submitted = true;
      this.toastrService.error(this.trans.instant('message.requiredMessage'));
      return;
    }
    this.spinner = true;
    this.gen0101Service.checkConnection(this.target).subscribe({
      next: (response) => {
        if (response == true) {
          setTimeout(() => {
            this.spinner = false;
          }, 1000);
          if (!this.target.targetId) {
            this.target.tableInfos = this.tableInfoList;
            this.gen0101Service.addNewTarget(this.target).subscribe({
              next: (response) => {
                if (response == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
                  this.toastrService.success(
                    this.trans.instant('message.success.saveSuccess')
                  );
                  this.dialogRef.close(response);
                } else {
                  this.toastrService.error(
                    this.trans.instant('message.error.saveFailed')
                  );
                }
              },
              error: () => {
                this.toastrService.error(
                  this.trans.instant('message.error.saveFailed')
                );
              },
            });
          } else {
            if (
              this.target.connectType.commCd == CommonConstant.CONNECT_TYPE.FTP
            ) {
              this.target.databaseName = null;
            } else {
              this.target.fileSavePath = null;
              this.target.fileType = null;
            }
            this.gen0101Service.updateTarget(this.target).subscribe({
              next: (response) => {
                if (response == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
                  this.toastrService.success(
                    this.trans.instant('message.success.updateSuccess')
                  );
                  this.dialogRef.close(response);
                } else {
                  this.toastrService.error(
                    this.trans.instant('message.error.updateFailed')
                  );
                }
              },
              error: () => {
                this.toastrService.error(
                  this.trans.instant('message.error.updateFailed')
                );
              },
            });
          }
        } else {
          setTimeout(() => {
            this.spinner = false;
            this.toastrService.error(
              this.trans.instant('message.error.checkConnect')
            );
          }, 1000);
        }
      },
      error: () => {
        setTimeout(() => {
          this.spinner = false;
          this.toastrService.error(
            this.trans.instant('message.error.checkConnect')
          );
        }, 1000);
      },
    });
  }

  onCancel() {
    if (this.target.targetId) {
      this.dialogRef.close(this.target);
    } else {
      this.dialogRef.close();
    }
  }

  addTableInfo() {
    let tableInfo: any = {};
    this.target.tableInfos.push(tableInfo);
  }

  deleteTableInfo(index: any) {
    this.target.tableInfos.splice(index, 1);
  }

  initData() {
    if (this.target.tableInfos?.length == 0 || !this.target.tableInfos) {
      let tableInfo: any = {};
      this.tableInfoList.push(tableInfo);
      this.target.tableInfos = this.tableInfoList;
    }
    if (!this.target.targetId) {
      this.target.connectType = {};
      this.target.project = {};
    }
    this.getConnectType();
    this.getProjects();
    this.getTypeFiles();
  }

  getProjects() {
    this.gen0501Service
      .getListProject(AuthenticationUtil.getUserUid())
      .subscribe((res) => {
        this.projects = res;
      });
  }

  async checkConnect(target: any) {
    this.spinner = true;
    this.gen0101Service.checkConnection(target).subscribe({
      next: (response) => {
        if (response == true) {
          setTimeout(() => {
            this.spinner = false;
            this.toastrService.success(
              this.trans.instant('message.success.checkConnect')
            );
          }, 1000);
        } else {
          setTimeout(() => {
            this.spinner = false;
            this.toastrService.error(
              this.trans.instant('message.error.checkConnect')
            );
          }, 1000);
        }
      },
      error: () => {
        setTimeout(() => {
          this.spinner = false;
          this.toastrService.error(
            this.trans.instant('message.error.checkConnect')
          );
        }, 1000);
      },
    });
    return true;
  }

  getTypeFiles() {
    this.tccoStdService
      .getCommCdByUpCommCd(CommonConstant.FILE_TYPE_COMM_CD)
      .subscribe((response) => {
        this.typeFiles = this.convertCommCode(response);
      });
  }

  convertCommCode(data: any) {
    // let temps: any[] = [{ label: 'All', value: '' }];
    let temps: any[] = [];
    data.forEach((ele: any) => {
      let object = { label: ele.commNm, value: ele.commCd };
      temps.push(object);
    });
    return temps;
  }

  addProject() {
    this.router.navigate(['/gen/gen0501']);
    this.dialogRef.close();
  }
}
