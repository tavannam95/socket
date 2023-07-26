import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService, MenuItem, TreeNode } from 'primeng/api';
import { PaginationConfig } from 'src/app/config/pagination.config';
import { RowStatus } from 'src/app/constants/rowStatus.constant';
import { Sys0101Service } from 'src/app/services/sys/sys0101.service';
import { Sys0101wComponent } from '../components/sys0101w/sys0101w.component';

declare var $: any;

@Component({
  selector: 'app-menu-list',
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.css'],
})
export class MenuListComponent implements OnInit {
  language: any;
  menus!: any[];
  rootMenus!: any[];
  mMenu: any;
  pageRequest: any = {};

  selectedNode!: TreeNode;
  displayDialog!: boolean;
  totalRecords!: number;

  listStatus: any[] = [
    { label: this.trans.instant('sys.sys0101.status.all'), value: "" },
    { label: this.trans.instant('sys.sys0101.status.blocked'), value: "N" },
    { label: this.trans.instant('sys.sys0101.status.active'), value: "Y" }
  ];

  constructor(
    private toastr: ToastrService,
    private confirmationService: ConfirmationService,
    private sys0101Service: Sys0101Service,
    private trans: TranslateService,
    public dialog: MatDialog
  ) {
  }

  ngOnInit() {
    this.pageRequest.useYn = "";
    this.mMenu = {};
    this.displayDialog = false;
    this.pageRequest = {};
    this.pageRequest.size = PaginationConfig.PAGE_SIZE;
    this.getData();
  }

  getData() {
    this.sys0101Service.search(this.pageRequest).subscribe((response: any) => {
      this.rootMenus = response;
      this.pageToList(response);
    });
  }

  onKeydownEvent(event: KeyboardEvent, ele: any) {
    if (event.keyCode === 13) {
      $(ele).blur();
    }
  }

  pageToList(page: any): void {
    this.menus = this.listToTree(page);
  }

  onSearch() {
    // this.pageToList(this.pageRequest);
    this.getData();
  }

  onSearchReset() {
    this.pageRequest = {};
    this.getData();
  }

  save() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('button.save.title'),
      accept: () => {
        let request: any = {};
        let temArr: any = [];
        this.rootMenus.forEach((ele) => {
          if (ele.state) {
            let tem = this.parseData(ele);
            temArr.push(tem);
          }
        });
        request.tsstMenu = temArr;
        this.sys0101Service.save(request).subscribe({
          next: (res: any) => {
            this.toastr.success(
              this.trans.instant('message.success.saveSuccess')
            );
            this.getData();
          },
          error: (err: any) => {
            this.toastr.error(this.trans.instant('message.error.saveFailed'));
          }
        });
      },
    });
  }

  parseData(menu: any) {
    let tem = {
      menuNm: null,
      menuNmEn: null,
      menuNmVi: null,
      url: null,
      useYn: null,
      menuId: null,
      state: null,
      ordNo: null,
      upMenuId: null
    };
    tem.menuNm = menu.menuNm;
    tem.menuNmEn = menu.menuNmEn;
    tem.menuNmVi = menu.menuNmVi;
    tem.url = menu.url;
    tem.useYn = menu.useYn;
    tem.menuId = menu.menuId;
    tem.state = menu.state;
    tem.ordNo = menu.ordNo;
    tem.upMenuId = menu.upMenuId;
    return tem;
  }

  onDelete(data?: any) {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.delete'),
      accept: () => {
        this.sys0101Service.delete(data.menuId).subscribe({
          next: res =>{
            if(res.status){
              this.toastr.success(this.trans.instant('message.success.deleteSuccess'));
              this.getData();
              
            }else{
              this.toastr.error(this.trans.instant('message.error.deleteFailed'));
            }
          },
          error: err =>{
            this.toastr.error(this.trans.instant('message.error.deleteFailed'));
          }
        })
      },
    });
  }

  onActive() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.menu.activeStatus'),
      accept: () => {
        this.updateStatus(this.selectedNode.data.menuId, 'Y');
      },
    });
  }

  onDeactive() {
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.menu.deactiveStatus'),
      accept: () => {
        this.updateStatus(this.selectedNode.data.menuId, 'N');
      },
    });
  }

  updateStatus(menuId: string, status: any) {
    if (!menuId) {
      return;
    }
    let rq = {
      menuId: menuId,
      useYn: status,
    };
    this.sys0101Service.update(rq).subscribe(
      (response: any) => {
        if (response) {
          this.toastr.success(
            this.trans.instant('message.success.updateSuccess')
          );
          this.getData();
          return;
        }
      },
      (error: any) => {
        this.toastr.error(this.trans.instant('message.error.saveFailed'));
      }
    );
  }

  listToTree(list: any) {
    const map: any = {};
    let node: any;
    const roots = [];

    if (list !== undefined) {
      for (let i = 0; i < list.length; i += 1) {
        list[i].label = list[i].menuNm;
        list[i].data = list[i];
        map[list[i].menuId] = i; // initialize the map
        list[i].children = []; // initialize the children
      }

      for (let i = 0; i < list.length; i += 1) {
        node = list[i];
        if (node.upMenuId !== undefined && node.upMenuId !== null) {
          // if you have dangling branches check that map[node.parentId] exists
          node.expanded = true;
          if (map[node.upMenuId] || map[node.upMenuId] === 0) {
            list[map[node.upMenuId]].children.push(node);
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

  openPopup(data?: any) {
    // if (this.selectedNode && this.selectedNode?.data?.lev != 1 ){
    //   this.toastr.error(this.trans.instant('message.error.cannotAddChildMenu'));
    //   return;
    // }
    if (data){
      data = this.parseData(data);
    }
    const dialogRef = this.dialog.open(Sys0101wComponent, {
      width: '0px',
      height: '0px',
      data: {
        menu: data,
        menus: this.menus
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result){
        this.getData();
      }
    });
    
  }

  onClickAction(rowData: any){
    
  }
}
