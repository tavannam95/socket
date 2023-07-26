import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService, TreeNode } from 'primeng/api';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { Sys0102Service } from 'src/app/services/sys/sys0102.service';

@Component({
  selector: 'app-role-list',
  templateUrl: './role-list.component.html',
  styleUrls: ['./role-list.component.css']
})
export class RoleListComponent implements OnInit {

  constructor(
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private sys0102Service: Sys0102Service,
    private trans: TranslateService,
    public dialog: MatDialog,
  ) { }

  prgId = "sys0102";
  datas!: any[];
  datasDeleted: any[] = [];
  pageRequest: any = {};

  selectedNode!: TreeNode;
  displayDialog!: boolean;
  selectedRow: any = {};
  flagAccept = ""

  ngOnInit(): void {

    this.getData();
  }

  getData() {
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.sys0102Service.search(this.pageRequest).subscribe(
      (response) => {
        this.datas = response;
        this.convertData();
        dialogSpinner.close();
      }
    );
  }

  add() {
    this.datas.unshift({
      roleNm: '',
      roleId: '',
      useYn: 'N',
      checked: false
    });
  }

  edit() {

  }

  onDelete() {
    if (!this.selectedRow.roleId) {
      this.toastr.error(this.trans.instant('apv.error.needSelectParent'))
      return
    }

    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      icon: 'fa fa-trash',
      accept: () => {
        this.delete();
      }
    })

  }

  delete() {
    for (var i = 0; i < this.datas.length; i++) {
      if (this.datas[i].roleId == this.selectedRow.roleId) {
        this.datas.splice(i, 1);
        this.datasDeleted.push(this.selectedRow.roleId);
      }
    }
    this.datas.splice
  }

  onCheckBox(event: any, data: any) {
  }


  validate() {
    this.datas.forEach(ele => {
    })
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
    let request: any = {}
    request.tsstRole = this.datas;
    request.idsDel = this.datasDeleted.join(",");
    this.sys0102Service.save(request).subscribe(
      (res: any) => {
        this.toastr.success(this.trans.instant('message.success.saveSuccess'));
        this.getData();
      },
      err => {
        this.toastr.error(this.trans.instant('message.error.saveFailed'));
      }
    )
  }

  changedRow(rowIndex: any) {
    this.datas[rowIndex].state = 'U';
  }
  onSearch() {
    this.getData();
  }

  onSearchReset() {
    this.pageRequest.roleNm = '';
    this.datasDeleted = [];
    this.getData();
  }

  updateActive(rowIndex: any) {
    this.datas[rowIndex].state = 'U';
    this.datas[rowIndex].useYn = this.datas[rowIndex].useYn == 'Y' ? 'N' : 'Y';
    this.datas[rowIndex].checked = !this.datas[rowIndex].checked;
  }

  convertData(){
    this.datas.forEach((ele: any) =>{
      ele.checked = ele.useYn == 'Y' ? true : false;
    })
  }

}
