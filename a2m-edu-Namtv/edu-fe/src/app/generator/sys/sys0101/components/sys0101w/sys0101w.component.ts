import { Component, Inject, OnInit, SimpleChanges } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { SpinnerComponent } from 'src/app/generator/commons/spinner/spinner.component';
import { Menu } from 'src/app/model/sys/menu';
import { Sys0101Service } from 'src/app/services/sys/sys0101.service';

@Component({
  selector: 'app-sys0101w',
  templateUrl: './sys0101w.component.html',
  styleUrls: ['./sys0101w.component.css']
})
export class Sys0101wComponent implements OnInit {

  menu: any = {};
  menus: any = [];
  submitted : any = false;

  parentMenu: any = {};
  isUpdate?: boolean;
  request: any = {};
  parents: any[] = [];

  constructor(
    private toastr: ToastrService,
    private sys0101Service: Sys0101Service,
    private trans: TranslateService,
    public dialogRef: MatDialogRef<Sys0101wComponent>,
    public dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    let dialogSpinner = this.dialog.open(SpinnerComponent, {panelClass: 'transparent',disableClose: true});
    this.isUpdate = false;
    this.request.upMenuId = "";
    if (this.data && this.data.menu) {
      this.menu = this.data.menu
      // if (this.menu.menuId){
      //   this.isUpdate =true;
      // }
    }
    if (this.data && this.data.menus) {
      this.menus = this.data.menus
    }
    if (this.data && this.data.parentMenu) {
      this.parentMenu = this.data.parentMenu;
    }
    this.setDataParent();
    dialogSpinner.close();
  }

  setDataParent() {
    this.parents = [];
    this.request = this.menu;

    this.menus.forEach((ele: any) => {
      this.parents.push(ele);
    });
    this.parents.map(ele => {
      ele.label = ele.menuId + " - " + ele.menuNm;
      ele.value = ele.menuId
    })
    this.parents.unshift({
      label: "None",
      value: ""
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.setDataParent();
  }


  onSave(invalid: any) {
    if (invalid) {
      // this.toastr.error(this.trans.instant('message.requiredMessage'));
      this.submitted = true;
      return;
    }
    if (!this.menu.menuId) {
      this.sys0101Service.create(this.request).subscribe({
        next: (response) => {
          this.toastr.success(this.trans.instant('message.success.saveSuccess'));
          this.dialogRef.close(response);
        },
        error: error => {
          this.toastr.error(this.trans.instant('message.error.saveFailed'));
        }
      }

      );

    } else {
      // update case
      this.sys0101Service.update(this.request).subscribe({
        next: response => {
          if (response.status){
            this.toastr.success(this.trans.instant('message.success.updateSuccess'));
            this.dialogRef.close(response);
          }else{
            this.toastr.error(this.trans.instant('message.error.updateSuccess'));
          }
        },
        error: error => {
          this.toastr.error(this.trans.instant('message.error.updateFailed'));
        }
      });
    }

  }

  onCancel() {
    this.dialogRef.close();
  }

  changeSelect(event: any) {
    this.request.upMenuId = event;
  }


}
