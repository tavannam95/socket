import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { RowStatus } from 'src/app/constants/rowStatus.constant';
import { Sys0101Service } from 'src/app/services/sys/sys0101.service';
import { Sys0102Service } from 'src/app/services/sys/sys0102.service';

@Component({
  selector: 'app-role-program',
  templateUrl: './role-program.component.html',
  styleUrls: ['./role-program.component.css']
})
export class RoleProgramComponent implements OnInit {

  selectedRole: any;
  roles!: any[];
  programs!: any[];
  menuRoles!: any[];

  selected: any;
  roleRequest: any = {};
  menuRequest: any = {orderForRole: true};

  constructor(
    private toastr: ToastrService,
    private trans: TranslateService,
    private sys0102Service: Sys0102Service,
    private sys0101Service: Sys0101Service,
    private confirmationService: ConfirmationService
  ) {
  }

  ngOnInit() {
    this.initData();
  }

  onSearchReset() {
    this.roleRequest = {};
    this.menuRequest = {orderForRole: true};
    this.initData();
  }
  initData() {
    this.getDataRole();
    this.getDataMenuRole();
    this.getDataMenu();
  }
  getDataRole() {
    this.sys0102Service.search(this.roleRequest).subscribe(
      res => {
        this.roles = res;
      }
    )
  }

  getDataMenuRole() {
    this.sys0102Service.searchMenuRole(this.menuRequest).subscribe(
      res => {
        this.menuRoles = res;
        this.setCheckRole();
      }
    )
  }

  getDataMenu() {
    this.sys0101Service.search(this.menuRequest).subscribe(
      res => {
        this.programs = res;
      }
    )
  }


  onSearch() {
    this.initData()
  }

  setCheckRole() {
    if (!this.selectedRole) {
      return
    }
    this.programs.forEach(ele => {
      ele.state = null;
      this.isChecked(ele);
    })

  }

  onCancel() {
    this.onSearch();
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
    if (!this.selectedRole) {
      this.toastr.error(this.trans.instant('message.error.nothingToSave'));
      return
    }
    let arrRequest: any[] = [];
    let currentDate = new Date();
    this.programs.forEach(ele => {
      if (ele.state) {
        let tem: any = {};
        tem = Object.assign(tem, ele);
        tem.roleId = this.selectedRole.roleId;
        tem.createdDate = currentDate;
        arrRequest.push(tem)
      }
    })

    this.sys0102Service.saveMenuRole(arrRequest).subscribe(
      res => {
        this.toastr.success(this.trans.instant('message.success.updateSuccess'));
        this.getDataMenuRole();
      },
      err => {
        this.toastr.error(this.trans.instant('message.error.saveFailed'));
      }
    )
  }


  onClickCheck(pgm: any, type: number, event: any) {
    pgm.state = RowStatus.UPDATE
    if (event.target.checked) {
      switch (type) {
        case 1: {
          pgm.readYn = 'Y';
          break;
        }
        case 2: {
          pgm.wrtYn = 'Y';
          break;
        }
        case 3: {
          pgm.modYn = 'Y';
          break;
        }
        case 4: {
          pgm.delYn = 'Y';
          break;
        }
        case 5: {
          pgm.pntYn = 'Y';
          break;
        }
        case 6: {
          pgm.excDnYn = 'Y';
          break;
        }
        case 7: {
          pgm.mngYn = 'Y';
          break;
        }

      }

    } else {
      switch (type) {
        case 1: {
          pgm.readYn = 'N';
          break;
        }
        case 2: {
          pgm.wrtYn = 'N';
          break;
        }
        case 3: {
          pgm.modYn = 'N';
          break;
        }
        case 4: {
          pgm.delYn = 'N';
          break;
        }
        case 5: {
          pgm.pntYn = 'N';
          break;
        }
        case 6: {
          pgm.excDnYn = 'N';
          break;
        }
        case 7: {
          pgm.mngYn = 'N';
          break;
        }
      }
    }

  }

  isChecked(item: any) {
    const p = this.menuRoles.find(x => x.menuId === item.menuId && x.roleId == this.selectedRole.roleId);
    if (p !== undefined) {
      item.readYn = p.readYn;
      item.wrtYn = p.wrtYn;
      item.modYn = p.modYn;
      item.delYn = p.delYn;
      item.pntYn = p.pntYn;
      item.excDnYn = p.excDnYn;
      item.mngYn = p.mngYn
    }
    else {
      item.readYn = 'N';
      item.wrtYn = 'N';
      item.modYn = 'N';
      item.delYn = 'N';
      item.pntYn = 'N';
      item.excDnYn = 'N';
      item.mngYn = 'N'
    }
  }

}
