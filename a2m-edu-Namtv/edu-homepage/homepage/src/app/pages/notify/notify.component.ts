import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-notify',
  templateUrl: './notify.component.html',
  styleUrls: ['./notify.component.css']
})
export class NotifyComponent implements OnInit {
  messages: any;
  constructor(
    public dialogRef: MatDialogRef<NotifyComponent>,
  ) { }
   ngOnInit(): void {
  }

  close(){
    this.dialogRef.close("");
  }

}
