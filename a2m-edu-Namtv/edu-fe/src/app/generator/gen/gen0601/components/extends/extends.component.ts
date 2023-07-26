import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { Gen0501Service } from 'src/app/services/gen/gen0501.service';
import { Gen0601Service } from 'src/app/services/gen/gen0601.service';
import { AuthenticationUtil } from 'src/app/utils/authentication.util';

@Component({
  selector: 'app-extends',
  templateUrl: './extends.component.html',
  styleUrls: ['./extends.component.css']
})
export class ExtendsComponent implements OnInit {

  request: any = {};
  projects: any [] = [];
  submitted: boolean = false;
  activeIndex: number = 1;
  extendedTimes: any[] = [
    {name: "1 Month", value: 1},
    {name: "3 Month", value: 3},
    {name: "6 Month", value: 6},
    {name: "12 Month", value: 12}
  ]

  countSave: number = 0;

  constructor(
    private gen0501Service: Gen0501Service,
    private gen0601Service: Gen0601Service,
    private confirmationService: ConfirmationService,
    private trans: TranslateService,
    public dialogRef: MatDialogRef<ExtendsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    
    this.getProjectList();
  }

  onCancel(){
    this.dialogRef.close();
  }

  onSaveLicense(invalid: any){
    this.submitted = true;
    if (invalid ){
      return;
    }

    if (this.countSave == 0 ){
      this.gen0601Service.saveLicense(this.request).subscribe({
        next: res => {
          if (res.status){
            this.onNext();
            this.countSave = 1;
          }
        },
        error: err => {
  
        }
      })
    }else{
      this.onNext();
    }
    
  }

  getProjectList(){
    this.gen0501Service.getListProject(AuthenticationUtil.getUserUid()).subscribe(res=>{
      this.projects = res;
    })
  }

  onNext(){
    if (this.activeIndex < 4){
      this.activeIndex +=1;
    }
  }

  onBack(){
    if (this.activeIndex > 1){
      this.activeIndex -=1;
    }
    
  }

  onPayment(){
    this.confirmationService.confirm({
      header: this.trans.instant('message.confirm.title'),
      message: this.trans.instant('message.confirm.payment'),
      accept: () => {
        this.onNext();
      },
    });
  }
}
