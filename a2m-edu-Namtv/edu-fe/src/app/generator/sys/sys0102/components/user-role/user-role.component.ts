import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { Sys0102Service } from 'src/app/services/sys/sys0102.service';

@Component({
  selector: 'app-user-role',
  templateUrl: './user-role.component.html',
  styleUrls: ['./user-role.component.css']
})
export class UserRoleComponent implements OnInit {

  selectedUser: any = {};
  username!: string;
  roles!: any[];
  users!: any[];
  usersRole!: any[];
  rolesChecked!: any[];

  selectedRole: any;
  totalUsers!: number;
  totalRoles!: number;
  userRequest: any = {};
  roleRequest: any = {};

  constructor(
    private toastr: ToastrService,
    private trans: TranslateService,
    private sys0102Service: Sys0102Service,
    // private sys0103Service: Sys0103Service,
    private confirmationService: ConfirmationService
  ) {
  }

  ngOnInit() {
    this.initData();
  }

  initData(){
    this.roles = [];
    this.rolesChecked = [];
    this.getDataUsers();
    this.getDataUserRole();
    this.getDataRole();
  }

  getDataUsers() {
    this.sys0102Service.getAllUser(this.userRequest).subscribe(
      res => {
        this.users = res;
      }
    )
  }

  getDataUserRole() {
    this.sys0102Service.searchUserRole({}).subscribe(
      res => {
        this.usersRole = res;
      }
    )
  }

  getDataRole() {
    this.roleRequest.TAB = "USER_ROLE"
    this.sys0102Service.search(this.roleRequest).subscribe(
      res => {
        this.roles = res;
      }
    )
  }

  onSave() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.title'),
      icon: 'fa fa-edit',
      accept: () => {
        this.save();
      }
    })
  }


  save() {
    if (this.selectedUser.userUid) {
      let request: any = [];
      this.roles.forEach(ele => {
        if (ele.touch == true) {
          request.push({
            userUid: this.selectedUser.userUid,
            roleId: ele.roleId,
            checked: ele.checked,
            touch:ele.touch
          })
        }
      })

      this.sys0102Service.saveUserRole(request).subscribe(
        res => {
          this.getDataUserRole();
          this.toastr.success(this.trans.instant('message.success.updateSuccess'));
        },
        err => {
          this.toastr.error(this.trans.instant('message.error.saveFailed'));
        }
      )
    }
    else {
      this.toastr.error(this.trans.instant('message.error.nothingToSave'));
    }

  }


  onClickCheck(rowIndex: any, event: any) {
    this.roles[rowIndex].touch = true;
    if (event) {
      this.roles[rowIndex].checked = false;
    }
    else {
      this.roles[rowIndex].checked = true;
    }

  }

  setCheckRole() {
    this.roles.forEach(ele => {
      this.isChecked(ele);
    })
  }

  isChecked(item: any) {
    if (this.usersRole) {
      let u = this.usersRole.find(x => (x.roleId === item.roleId && x.userUid == this.selectedUser.userUid));
      if (!u) {
        item.checked = false;
      } else {
        item.checked = true;
      }
    }

  }

  onCancel() {

  }

  onSearch() {
    this.getDataUsers();
    this.getDataRole();
    this.getDataUserRole();
  }

  onSearchReset() {
    this.selectedUser = {};
    this.selectedRole = {};
    this.userRequest = {}
    this.getDataUsers();
    this.getDataUserRole();
    this.getDataRole();
  }

}
