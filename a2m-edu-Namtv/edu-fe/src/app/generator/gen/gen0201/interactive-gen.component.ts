import {
  Component,
  ViewEncapsulation,
  ViewChild,
  OnDestroy,
  OnInit,
  AfterViewInit,
  ChangeDetectorRef,
  Renderer2,
  ElementRef,
  ApplicationRef,
  ViewContainerRef,
  ComponentFactoryResolver,
  EventEmitter,
  Output,
} from '@angular/core';

import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import cronstrue from 'cronstrue';
import { CronOptions } from 'ngx-cron-editor';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { lastValueFrom, forkJoin } from 'rxjs';
import { I18nConfig } from 'src/app/config/i18n.config';
import { CommonConstant } from 'src/app/constants/common.constant';
import { Project } from 'src/app/model/gen/project';
import { Gen0501Service } from 'src/app/services/gen/gen0501.service';
import { I18nService } from 'src/app/services/i18n.service';
import { Sys0102Service } from 'src/app/services/sys/sys0102.service';
import { TccoFileService } from 'src/app/services/tcco-file.service';
import { TccoStdService } from 'src/app/services/tcco-std.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';
import { CommonUtil } from 'src/app/utils/common-util';
import { ProjectFormComponent } from '../gen0501/components/project-form/project-form.component';
import { jsPlumb } from 'jsplumb';
import { TargetFormComponent } from '../gen0101/components/target-form/target-form.component';

/**
 * Sample for Fishbone publics
 */
declare const PlainDraggable: any;

@Component({
  selector: 'app-interactive-gen',
  templateUrl: './interactive-gen.component.html',
  styleUrls: ['./interactive-gen.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class InteractiveGenComponent
  implements OnInit, OnDestroy, AfterViewInit
{
  jsPlumbInstance: any;
  public done = false;
  public isVisible: boolean = true;
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
  isSelected: any = false;
  language: any;
  listShapes: any = [];
  selectedRow: any = null;
  isSource: any;

  @ViewChild('container')
  d1: any;

  @Output() public found = new EventEmitter<any>();

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
    public viewContainerRef: ViewContainerRef,
    private componentFactoryResolver: ComponentFactoryResolver,
    private renderer: Renderer2,
    private gen0501Service: Gen0501Service,
    private el: ElementRef,
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
  ngAfterViewInit(): void {
    this.jsPlumbInstance = jsPlumb.getInstance();
  }
  ngOnDestroy(): void {}

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

  addSource(env?: any) {
    this.isSource = true;
    this.jsPlumbInstance = jsPlumb.getInstance();
    let possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
    const lengthOfCode = 10;
    var result = this.makeRandom(lengthOfCode, possible);
    this.listShapes.forEach((item: any) => {
      if (item.source == true) {
        this.isSource = true;
      }
    });
    if (env != null) {
      this.listShapes.push({
        id: env.projectId,
        source: true,
        count: 0,
        isNew: false,
      });
    } else {
      this.listShapes.push({ id: result, source: true, count: 0, isNew: true });
    }
  }

  edit(event:any){

  }

  addTarget() {
    let possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
    const lengthOfCode = 10;
    var result = this.makeRandom(lengthOfCode, possible);
    this.listShapes.push({ id: result, source: false, count: 0, isNew: true });
    // this.jsPlumbInstance.draggable(result);
    // this.jsPlumbInstance.addEndpoint(
    //   result,
    //   {
    //     anchor: 'Top',
    //     endpoint: ['Dot', { radius: 4 }],
    //   },
    //   {
    //     isSource: true,
    //     isTarget: true,
    //     endpoint: ['Dot', { radius: 4 }],
    //     connector: ['Straight'],
    //   }
    // );
  }

  addDrag(env: any, index: any) {
    if (env.source && env.count == 0) {
      console.log(this.listShapes);
      this.listShapes[index].count += 1;
      this.jsPlumbInstance.draggable(env.id);
      this.jsPlumbInstance.addEndpoint(
        env.id,
        {
          uuid: env.id,
          anchor: 'Bottom',
          endpoint: ['Dot', { radius: 4 }],
          maxConnections: -1,
        },
        {
          uuid: env.id,
          isSource: true,
          isTarget: true,
          endpoint: ['Dot', { radius: 4 }],
          connector: ['Straight'],
          maxConnections: -1,
        }
      );
      this.listTarget.forEach((item: any) => {
        this.jsPlumbInstance.connect({
          uuids: [this.selectedProject.projectId, item.targetId],
          connector: ['Straight'],
        });
      });
    } else if (env.source != true && env.count == 0) {
      console.log(this.listShapes);
      this.listShapes[index].count += 1;
      this.jsPlumbInstance.draggable(env.id);
      this.jsPlumbInstance.addEndpoint(
        env.id,
        {
          uuid: env.id,
          anchor: 'Top',
          endpoint: ['Dot', { radius: 4 }],
          maxConnections: -1,
        },
        {
          uuid: env.id,
          isSource: false,
          isTarget: true,
          maxConnections: -1,
          endpoint: ['Dot', { radius: 4 }],
          connector: ['Straight'],
        }
      );

      this.listTarget.forEach((item: any) => {
        this.jsPlumbInstance.connect({
          uuids: [this.selectedProject.projectId, item.targetId],
          connector: ['Straight'],
        });
      });
    }
  }

  removeComponent(id: any, source: any) {
    const elem: HTMLElement | null = document.getElementById(id);
    if (elem) {
      if (source == true) {
        this.isSource = false;
      }
      elem.parentElement && elem.parentElement.removeChild(elem);
      this.jsPlumbInstance.remove(id);
    }
  }
  checkId(elem: any) {
    
  }

  makeRandom(lengthOfCode: number, possible: string) {
    let text = '';
    for (let i = 0; i < lengthOfCode; i++) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return text;
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
    this.isSelected = true;

    this.selectedProject = selectedProject;
    this.tccoFileService
      .getFileDetails(this.selectedProject.dataFilePath)
      .subscribe((response) => {});
    this.listTarget = this.selectedProject.targets;
    this.cronString = cronstrue.toString(this.selectedProject.genCycle);
    var connected = this.jsPlumbInstance.getConnections();
    this.listShapes.forEach((item: any) => {
      this.removeComponent(item.id, item.source);
    });
    var elementExists = document.getElementById(selectedProject.projectId);
    if (!elementExists) {
      this.addSource(selectedProject);
      this.isSource = true;
      this.listTarget.forEach((item: any) => {
        this.listShapes.push({ id: item.targetId, source: false, count: 0, isNew: false });
      });
    }
    // var connected =this.jsPlumbInstance.getConnections();
  }

  onChangeGenCycle(genCycle: any) {
    if (genCycle !== undefined) {
      this.cronString = cronstrue.toString(genCycle);
      this.selectedProject.genCycle = genCycle;
    }
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
              if (response == 'OK') {
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
      next: () => {
        this.toastrService.success(
          this.trans.instant('message.success.updateSuccess')
        );
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
    var conn = this.jsPlumbInstance.getConnections();
    console.log(conn);
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

  addNewTarget(Id: any) {
    var conn = this.jsPlumbInstance.getConnections();
    var result: any;
    conn.forEach((item: any) => {
      if (item.targetId == Id) {
        result = item.sourceId;
      }
    });
    const dialogRef = this.dialog.open(TargetFormComponent, {
      width: '0px',
      height: '0px',
      data: { target: {}, projectId: result },
    });

    dialogRef.afterClosed().subscribe((response: any) => {
      if (response) {
        this.search();
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
