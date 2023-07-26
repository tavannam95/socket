import { CommonUtil } from './../../../../utils/common-util';
import { Sys0102Service } from './../../../../services/sys/sys0102.service';
import { I18nConfig } from './../../../../config/i18n.config';
import { I18nService } from './../../../../services/i18n.service';
import { TranslateService } from '@ngx-translate/core';
import { forkJoin, lastValueFrom } from 'rxjs';
import { TccoFileService } from 'src/app/services/tcco-file.service';
import { Project } from './../../../../model/gen/project';
import { ProjectFormComponent } from './../components/project-form/project-form.component';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationService } from 'primeng/api';
import { ToastrService } from 'ngx-toastr';
import { Gen0501Service } from './../../../../services/gen/gen0501.service';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { CronOptions } from 'ngx-cron-editor';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { CommonConstant } from 'src/app/constants/common.constant';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import cronstrue from 'cronstrue';

@Component({
  selector: 'app-list-project',
  templateUrl: './list-project.component.html',
  styleUrls: ['./list-project.component.css'],
})
export class ListProjectComponent implements OnInit {
  request: any = {};
  listProject!: any[];
  project: any = new Project();
  selectedProject: any = {};
  listTarget!: any[];
  cronForm: any;
  dataFileType: any;
  selectedFile: any;
  totalRecords: any;
  projectStatus: any;
  connectType: any;
  spinner: any;
  file: any;
  cronString: any;
  language: any;

  public cronOptions: CronOptions = {
    defaultTime: '00:00:00',
    hideMinutesTab: false,
    hideHourlyTab: false,
    hideDailyTab: false,
    hideWeeklyTab: false,
    hideMonthlyTab: false,
    hideYearlyTab: false,
    hideAdvancedTab: true,
    hideSpecificWeekDayTab: true,
    hideSpecificMonthWeekTab: true,
    use24HourTime: true,
    hideSeconds: false,
    cronFlavor: 'standard', //standard or quartz
  };

  constructor(
    private gen0501Service: Gen0501Service,
    private toastrService: ToastrService,
    private confirmationService: ConfirmationService,
    public dialog: MatDialog,
    private trans: TranslateService,
    private tccoStdService: TccoStdService,
    private tccoFileService: TccoFileService,
    private i18nService: I18nService,
    private sys0102Service: Sys0102Service
  ) {
    this.language = localStorage.getItem(I18nConfig.STORAGE_KEY);
    if (!this.language) {
      this.language = I18nConfig.DEFAULT_LANGUAGE;
    }
    this.i18nService.language.subscribe((language: string) => {
      this.language = language;
    });
  }

  ngOnInit(): void {
    this.cronForm = new FormControl('0 0 1/1 * *');
    this.listProject = [];
    this.listTarget = [];
    this.getConnectTypeByUpCommCd(CommonConstant.CONNECT_TYPE_UP_COMM_CD);
    this.getProjectStatusByUpCommCd(CommonConstant.PROJECT_STATUS_UP_COMM_CD);
    this.getDataFileTypeByUpCommCd(CommonConstant.FILE_TYPE_COMM_CD);
    this.getUserRoles();
  }

  getUserRoles() {
    this.sys0102Service
      .searchUserRole({ userUid: AuthenticationUtil.getUserUid() })
      .subscribe((response) => {
        this.checkRoleSysAdmin(response);
      });
  }

  checkRoleSysAdmin(roles: any) {
    let checkRoleSysAdmin = roles.find(function (role: any) {
      return role.roleId == CommonConstant.ROLE_SYS_ADMIN;
    });
    if (!checkRoleSysAdmin) {
      this.request.createBy = AuthenticationUtil.getUserUid();
    }
    this.initData();
    this.search();
  }

  getDataFileTypeByUpCommCd(upCommCd: any) {
    this.tccoStdService.getCommCdByUpCommCd(upCommCd).subscribe((response) => {
      this.dataFileType = response;
    });
  }

  getProjectStatusByUpCommCd(upCommCd: any) {
    this.tccoStdService.getCommCdByUpCommCd(upCommCd).subscribe((response) => {
      this.projectStatus = response;
      response.push({ commNm: '전체', commNmEn: 'All', commNmVi: 'Tất cả' });
    });
  }

  getConnectTypeByUpCommCd(upCommCd: any) {
    this.tccoStdService.getCommCdByUpCommCd(upCommCd).subscribe((response) => {
      this.connectType = response;
    });
  }

  onRowSelect(selectedProject: any) {
    this.selectedProject = selectedProject;
    this.tccoFileService
      .getFileDetails(this.selectedProject.dataFilePath)
      .subscribe((response) => {
        this.setFileInput(response);
      });
    this.listTarget = this.selectedProject.targets;
    this.cronString = cronstrue.toString(this.selectedProject.genCycle);
  }

  onChangeGenCycle(genCycle: any) {
    if (genCycle !== undefined) {
      this.cronString = cronstrue.toString(genCycle);
      this.selectedProject.genCycle = genCycle;
    }
  }

  setFileInput(dataFile: any) {
    const fileData = <HTMLInputElement>document.getElementById('formFile');
    const dataTransfer = new DataTransfer();
    this.file = new File([], dataFile.fleNm, {});
    dataTransfer.items.add(this.file);
    fileData.files = dataTransfer.files;
    this.selectedFile = this.file;
  }

  addNewProject() {
    const dialogRef = this.dialog.open(ProjectFormComponent, {
      width: '0px',
      height: '0px',
      data: {},
    });

    dialogRef.afterClosed().subscribe((response: any) => {
      if (response) {
        this.search();
      }
    });
  }

  search() {
    this.gen0501Service.search(this.request).subscribe((res) => {
      this.listProject = res.data;
      this.totalRecords = res.count;
      CommonUtil.addIndexForListReverse(
        this.listProject,
        this.request.offset / CommonConstant.PAGE_SIZE,
        this.totalRecords
      );
      this.onSearching();
    });
  }

  onSearchReset() {
    this.request = {};
    this.onSearching();
    this.initData();
    this.search();
  }

  onSearching() {
    this.selectedProject = {};
    this.setFileInput({ fleNm: 'No file chosen' });
    this.listTarget = [];
    this.cronString = '';
  }

  deleteProject() {
    if (!this.selectedProject.projectId) {
      this.toastrService.warning(
        this.trans.instant('message.requiredSelectedProject')
      );
    } else {
      this.confirmationService.confirm({
        header: this.trans.instant('message.confirm.title'),
        message: this.trans.instant('message.confirm.delete'),
        icon: 'ri-delete-bin-5-line',
        accept: () => {
          this.gen0501Service.deleteProject(this.selectedProject).subscribe({
            next: (response) => {
              if (response == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
                this.toastrService.success(
                  this.trans.instant('message.success.deleteSuccess')
                );
                this.search();
              } else {
                this.toastrService.error(
                  this.trans.instant('message.error.deleteFailed')
                );
              }
            },
            error: () => {
              this.toastrService.error(
                this.trans.instant('message.error.deleteFailed')
              );
            },
          });
        },
        reject: () => {},
      });
    }
  }

  async updateProject() {
    await this.updateTccoFile();
    this.gen0501Service.updateProject(this.selectedProject).subscribe({
      next: (response) => {
        if (response == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
          this.toastrService.success(
            this.trans.instant('message.success.updateSuccess')
          );
        } else {
          this.trans.instant('message.error.updateFailed');
        }
      },
      error: () => {
        this.toastrService.error(
          this.trans.instant('message.error.updateFailed')
        );
      },
    });
  }

  async updateTccoFile() {
    await lastValueFrom(
      this.tccoFileService.update(
        AuthenticationUtil.getUserInfo(),
        this.selectedProject.dataFilePath,
        this.selectedFile
      )
    )
      .then(() => {})
      .catch(() => {
        this.toastrService.error(
          this.trans.instant('message.error.updateFailed')
        );
      });
  }

  onStart() {
    this.gen0501Service
      .checkTargetExists(this.selectedProject.projectId)
      .subscribe((response) => {
        if (response == true) {
          this.startProject();
        } else {
          this.toastrService.error(
            this.trans.instant('message.requiredHaveTarget')
          );
        }
      });
  }

  async startProject() {
    await this.getFileDetails();
    this.spinner = true;
    this.gen0501Service.pushQueue(this.selectedProject).subscribe({
      next: () => {
        setTimeout(() => {
          this.spinner = false;
          this.toastrService.success(
            this.trans.instant('message.success.startProject')
          );
          this.updateProjectStatus(CommonConstant.PROJECT_STATUS.RUNNING);
        }, 1000);
      },
      error: () => {
        this.spinner = false;
        this.toastrService.error(
          this.trans.instant('message.error.startProject')
        );
      },
    });
  }

  onStop() {
    this.gen0501Service
      .checkTargetExists(this.selectedProject.projectId)
      .subscribe((response) => {
        if (response === true) {
          this.stopProject();
        } else {
          this.toastrService.error(
            this.trans.instant('message.requiredHaveTarget')
          );
        }
      });
  }

  async stopProject() {
    this.spinner = true;
    forkJoin([
      this.gen0501Service.stopInstant(this.selectedProject.projectId),
      this.gen0501Service.killContainer(this.selectedProject.projectId),
    ]).subscribe({
      next: () => {
        setTimeout(() => {
          this.spinner = false;
          this.toastrService.success(
            this.trans.instant('message.success.stopProject')
          );
          this.updateProjectStatus(CommonConstant.PROJECT_STATUS.STOP);
        }, 1000);
      },
      error: () => {
        this.spinner = false;
        this.toastrService.error(
          this.trans.instant('message.error.stopProject')
        );
      },
    });
  }

  stopInstant() {
    this.gen0501Service
      .stopInstant(this.selectedProject.projectId)
      .subscribe(() => {});
  }

  killContainer() {
    this.gen0501Service
      .killContainer(this.selectedProject.projectId)
      .subscribe(() => {});
  }

  updateProjectStatus(status: any) {
    this.selectedProject.status = status;
    this.gen0501Service.updateProject(this.selectedProject).subscribe(() => {});
  }

  async getFileDetails() {
    await lastValueFrom(
      this.tccoFileService.getFileDetails(this.selectedProject.dataFilePath)
    ).then((response) => {
      this.selectedProject.newFleNm = response.newFleNm;
    });
  }

  onSelectedFile(event: any) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.selectedFile = fileList.item(0);
    }
  }

  initData() {
    this.request.offset = 0;
    this.request.size = CommonConstant.PAGE_SIZE;
  }

  onChangePagi(event: any) {
    this.request.offset = CommonConstant.PAGE_SIZE * event.page;
    this.request.size = CommonConstant.PAGE_SIZE;
    this.search();
  }
}
