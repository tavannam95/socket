import { CommonConstant } from 'src/app/constants/common.constant';
import { FormControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { CronOptions } from 'ngx-cron-editor';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Gen0501Service } from './../../../../../services/gen/gen0501.service';
import { Component, Inject, OnInit, Output, EventEmitter } from '@angular/core';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { TccoFileService } from 'src/app/services/tcco-file.service';
import cronstrue from 'cronstrue';
``;
@Component({
  selector: 'app-project-form',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.css'],
})
export class ProjectFormComponent implements OnInit {
  @Output()
  save: EventEmitter<any> = new EventEmitter();
  @Output()
  cancel: EventEmitter<any> = new EventEmitter();

  dataFileType!: any[];
  project: any = {};
  selectedFile: any;
  userUid: any;
  cronString: any;
  submitted: any = false;
  requireField: any = true;
  cronForm: any;

  public cronOptions: CronOptions = {
    defaultTime: '00:00:00',
    hideMinutesTab: false,
    hideHourlyTab: false,
    hideDailyTab: false,
    hideWeeklyTab: false,
    hideMonthlyTab: false,
    hideYearlyTab: false,
    hideAdvancedTab: true,
    hideSpecificWeekDayTab: false,
    hideSpecificMonthWeekTab: false,
    use24HourTime: true,
    hideSeconds: false,
    cronFlavor: 'quartz', //standard or quartz
  };

  constructor(
    private gen0510Service: Gen0501Service,
    private tccoStdService: TccoStdService,
    private toastrService: ToastrService,
    private tccoFileService: TccoFileService,
    private trans: TranslateService,
    public dialogRef: MatDialogRef<ProjectFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    this.cronForm = new FormControl('* * * * *');
    this.userUid = AuthenticationUtil.getUserUid();
  }

  onSave(invalid: any) {
    if (invalid || !this.selectedFile || !this.project.genCycle) {
      this.requireField = false;
      this.submitted = true;
      return;
    }
    this.project.userUid = this.userUid;
    this.tccoFileService
      .save(AuthenticationUtil.getUserInfo(), null, this.selectedFile)
      .subscribe((tccoFile) => {
        this.project.dataFilePath = tccoFile.atchFleSeq;
        this.gen0510Service.addNewProject(this.project).subscribe({
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
      });
  }

  onChangeGenCycle(genCycle: any) {
    if (genCycle !== undefined) {
      this.cronString = cronstrue.toString(genCycle);
      this.project.genCycle = genCycle;
    }
  }

  getCommCdByUpCommCd(upCommCd: any) {
    this.tccoStdService.getCommCdByUpCommCd(upCommCd).subscribe((response) => {
      this.dataFileType = response;
    });
  }

  onCancel() {
    this.dialogRef.close();
  }

  onSelectedFile(event: any) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.selectedFile = fileList.item(0);
    }
  }
}
