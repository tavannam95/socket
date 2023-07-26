import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.css']
})
export class ProgressComponent implements OnInit {

  progressType : any;

  listProgress = [
    { label: this.trans.instant('edu.edu0202.Pending'), value: 'Chưa giải quyết' },
    { label: this.trans.instant('edu.edu0202.InProgress'), value: 'Trong quá trình' },
    { label: this.trans.instant('edu.edu0202.Confirm'), value: 'Đã xác nhận' },
    { label: this.trans.instant('edu.edu0202.Close'), value: 'Đóng' },
  ];

  constructor(
    public dialogRef: MatDialogRef<ProgressComponent>,
    private trans: TranslateService,
    @Inject(MAT_DIALOG_DATA) public data: any

  ) { }


  ngOnInit(): void {
    // this.progressType == 'Close';

  }

  close(type : string){
    if(type!=''){
      this.dialogRef.close(this.progressType);
    }else{
      this.dialogRef.close('');
    }
  }

}
