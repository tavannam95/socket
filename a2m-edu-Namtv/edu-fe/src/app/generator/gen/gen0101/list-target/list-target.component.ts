import { CommonConstant } from 'src/app/constants/common.constant';
import { Sys0102Service } from './../../../../services/sys/sys0102.service';
import { PaginationConfig } from 'src/app/config/pagination.config';
import { CommonUtil } from './../../../../utils/common-util';
import { TranslateService } from '@ngx-translate/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationService } from 'primeng/api';
import { ToastrService } from 'ngx-toastr';
import { Gen0101Service } from './../../../../services/gen/gen0101.service';
import { Component, OnInit } from '@angular/core';
import { TargetFormComponent } from '../components/target-form/target-form.component';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';

@Component({
  selector: 'app-list-target',
  templateUrl: './list-target.component.html',
  styleUrls: ['./list-target.component.css'],
  providers: [],
})
export class ListTargetComponent implements OnInit {
  constructor(
    private gen0101Service: Gen0101Service,
    private toastrService: ToastrService,
    private sys0102Service: Sys0102Service,
    private trans: TranslateService,
    private confirmationService: ConfirmationService,
    public dialog: MatDialog
  ) {}

  listTarget!: any[];
  target!: any;
  totalTarget!: any;
  rows!: any;
  pagingRequest: any = {};
  spinner!: any;
  targetStatus = [
    {
      label: this.trans.instant('common.all'),
    },
    {
      label: this.trans.instant('common.active'),
      code: '1',
    },
    {
      label: this.trans.instant('common.deactive'),
      code: '0',
    },
  ];

  ngOnInit(): void {
    this.target = {};
    this.listTarget = [];
    this.getUserRoles();
  }

  initData() {
    this.pagingRequest.page = 0;
    this.pagingRequest.rows = PaginationConfig.PAGE_SIZE;
  }

  getPaging(event: any) {
    this.pagingRequest.page = event.page;
    this.pagingRequest.rows = event.rows;
    this.search();
  }

  search() {
    this.gen0101Service.search(this.pagingRequest).subscribe((response) => {
      this.listTarget = response.listTarget;
      this.totalTarget = response.totalItems;
      CommonUtil.addIndexForListReverse(
        this.listTarget,
        this.pagingRequest.page,
        this.totalTarget
      );
    });
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
      this.pagingRequest.userUid = AuthenticationUtil.getUserUid();
    }
    this.initData();
    this.search();
  }

  onSearchReset() {
    this.pagingRequest = {};
    this.initData();
    this.search();
  }

  addNewTarget() {
    const dialogRef = this.dialog.open(TargetFormComponent, {
      width: '0px',
      height: '0px',
      data: { target: {} },
    });

    dialogRef.afterClosed().subscribe((response: any) => {
      if (response) {
        this.search();
      }
    });
  }

  updateTarget(target: any) {
    const dialogRef = this.dialog.open(TargetFormComponent, {
      width: '0px',
      height: '0px',
      data: {
        target: target,
      },
    });

    dialogRef.afterClosed().subscribe((response: any) => {
      if (response) {
        this.search();
      }
    });
  }

  checkConnect(target: any) {
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
  }

  deleteTarget(target: any, event: Event) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      target: event.target || undefined,
      message: this.trans.instant('message.confirm.delete'),
      icon: 'ri-delete-bin-5-line',
      accept: () => {
        this.gen0101Service.deleteTarget(target.targetId).subscribe({
          next: (res) => {
            if (res == CommonConstant.RESPONSE_MESSAGE_SUCCESS) {
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
