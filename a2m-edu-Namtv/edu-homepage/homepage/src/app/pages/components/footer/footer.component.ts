import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SpinnerDialogComponent } from 'src/app/commons/spinner-dialog/spinner-dialog.component';
import { PatternValidate } from 'src/app/constants/patternValidate.constatnt';
import { ApplyService } from 'src/app/services/apply.service';
import { NotifyComponent } from '../../notify/notify.component';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
    //Star Valid Form
    emailPattern = PatternValidate.EMAIL_PATTERN;
    phonePattern = PatternValidate.PHONE_PATTERN;
    phonePatternBasic = PatternValidate.PHONE_PATTERN_BASIC;
    submitted: any = false;
    //End Valid Form

  candidate:any = {};
  request : any={};
  constructor(
    private applyService: ApplyService,
    public dialog :MatDialog
  ) { }

  ngOnInit(): void {
  }

  onSave(invalid: any,form : any){
    if (invalid   ) {
      return;
    }
    this.candidate.insDate = new Date();
    this.candidate.updDate = new Date();
    // this.request.file = this.file;
    this.request.candidate = this.candidate

    let dialogRef = this.dialog.open(SpinnerDialogComponent, {panelClass: 'transparent',disableClose: true});

    this.applyService.save(this.request).subscribe(
      (response: any)=>{
        dialogRef.close();

        form.resetForm()
        this.openDialog()
      },(error: any) => {
        dialogRef.close();
      }
    )
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(NotifyComponent, {
      width: '0px',
      height: '0px',
      data: {
        messages: 'Success'
      },
      panelClass: 'custom-modalbox',
    });

    dialogRef.afterClosed().subscribe((response: any) => {
    });
  }

  handleKeyupPhone(event: any){
    let keyStr = event.key;
    let isNumber = !Number.isNaN(Number(keyStr));

    if(!isNumber){
      this.candidate.candidatePhone = this.candidate.candidatePhone.replace(event.key, "");
      if(keyStr!=null || keyStr!=undefined || keyStr!=''){
        this.candidate.candidatePhone = this.candidate.candidatePhone.replace(/\D/g,'');
      }
    }
  }

}
