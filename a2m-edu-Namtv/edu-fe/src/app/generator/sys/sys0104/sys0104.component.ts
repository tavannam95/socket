import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService, TreeNode } from 'primeng/api';
import { PaginationConfig } from 'src/app/config/pagination.config';
import { RowStatus } from 'src/app/constants/rowStatus.constant';
import { Sys0104Service } from 'src/app/services/sys/sys0104.service';
import { SpinnerComponent } from '../../commons/spinner/spinner.component';
import { Sys0104wComponent } from './sys0104w/sys0104w.component';

declare var $: any;
@Component({
  selector: 'app-sys0104',
  templateUrl: './sys0104.component.html',
  styleUrls: ['./sys0104.component.css']
})
export class Sys0104Component implements OnInit {

  tccoStds!: any[];
  rootTccoStds!: any[];
  mTccoStd: any;
  pageRequest: any = {};

  selectedNode!: TreeNode;
  displayDialog!: boolean;
  totalRecords!: number;

  listStatus: any[] = [
    // { label: this.trans.instant('sys.sys0101.status.all'), value: "" },
    // { label: this.trans.instant('sys.sys0101.status.blocked'), value: "N" },
    // { label: this.trans.instant('sys.sys0101.status.active'), value: "Y" }
    { label: "All", value: "" },
    { label: "Blocked", value: "N" },
    { label: "Active", value: "Y" }
  ];

  constructor(
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private sys0104Service: Sys0104Service,
    private trans: TranslateService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.pageRequest.useYn = "";
    this.mTccoStd = {};
    this.displayDialog = false;
    this.pageRequest = {};
    this.pageRequest.size = PaginationConfig.PAGE_SIZE;
    this.getData();
  }

  getData() {
    this.sys0104Service.search(this.pageRequest).subscribe((response: any) => {
      let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
      this.rootTccoStds = response;
      this.pageToList(response);
      dialogSpinner.close();
    });
  }

  onKeydownEvent(event: KeyboardEvent, ele: any) {
    if (event.keyCode === 13) {
      $(ele).blur();
    }
  }

  pageToList(page: any): void {
    this.tccoStds = this.listToTree(page);
  }

  preEdit() {
    this.displayDialog = true;
    this.mTccoStd = Object.assign({}, this.selectedNode.data);
  }

  preAdd() {
    this.displayDialog = true;
  }

  handleCancel(displayDialog: boolean) {
    this.displayDialog = displayDialog;
  }

  onSearch() {
    // this.pageToList(this.pageRequest);
    this.getData();
  }

  onSearchReset() {
    this.pageRequest = {};
    this.getData();
  }

  onSave(response: any) {
    if (response) {
      this.toastr.success(this.trans.instant('message.success.saveSuccess'));
      this.getData();
      this.displayDialog = false;
    }
  }

  save() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('button.save.title'),
      accept: () => {
        let request: any = {};
        let temArr: any = [];
        this.rootTccoStds.forEach((ele) => {
          if (ele.state) {
            let tem = this.parseData(ele);
            temArr.push(tem);
          }
        });
        request.tssoStdDtos = temArr;
        this.sys0104Service.save(request).subscribe({
          next: (res: any) => {
            if (res.status){
              this.toastr.success(this.trans.instant('message.success.saveSuccess'));
              this.getData();
            }else{
              this.toastr.error(this.trans.instant('message.error.saveFailed'));
            }

          },
          error: (err: any) => {
            this.toastr.error(this.trans.instant('message.error.saveFailed'));
          }
        });
      },
    });
  }
  accepted() {}

  parseData(tccoStd: any) {
    let tem = {
      commNm: null,
      commNmEn: null,
      commNmVi: null,
      useYn: null,
      commCd: null,
      state: null,
      upCommCd: null,
      lev: null
    };
    tem.commNm = tccoStd.commNm;
    tem.commNmEn = tccoStd.commNmEn;
    tem.commNmVi = tccoStd.commNmVi;
    tem.useYn = tccoStd.useYn;
    tem.commCd = tccoStd.commCd;
    tem.upCommCd = tccoStd.upCommCd;
    tem.lev = tccoStd.lev;
    tem.state = tccoStd.state;
    return tem;
  }

  onDelete() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      accept: () => {
        this.selectedNode.data.state = RowStatus.DELETE;
      },
    });
  }

  listToTree(list: any) {
    const map: any = {};
    let node: any;
    const roots = [];

    if (list !== undefined) {
      for (let i = 0; i < list.length; i += 1) {
        list[i].label = list[i].commNm;
        list[i].data = list[i];
        map[list[i].commCd] = i; // initialize the map
        list[i].children = []; // initialize the children
      }

      for (let i = 0; i < list.length; i += 1) {
        node = list[i];
        if (node.upCommCd !== undefined && node.upCommCd !== null) {
          // if you have dangling branches check that map[node.parentId] exists
          node.expanded = true;
          if (map[node.upCommCd] || map[node.upCommCd] === 0) {
            list[map[node.upCommCd]].children.push(node);
          } else {
            node.expanded = true;
            roots.push(node);
          }
        } else {
          node.expanded = true;
          roots.push(node);
        }
      }
    }
    return roots;
  }

  changedRow(rowData: any) {
    if (rowData) {
      rowData.state = RowStatus.UPDATE;
    }
  }

  openPopup() {
    const dialogRef = this.dialog.open(Sys0104wComponent, {
      width: '0px',
      height: '0px',
      data: {
        tccoStd: this.mTccoStd,
        tccoStds: this.rootTccoStds,
        parentTccoStd: this.selectedNode?.data
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result){
        this.getData();
      }
    });

  }

}
