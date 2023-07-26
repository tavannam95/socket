import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-successfull',
  templateUrl: './successfull.component.html',
  styleUrls: ['./successfull.component.css']
})
export class SuccessfullComponent implements OnInit {


  messages: any;
  nameLeft: any = "";
  nameRight: any = "";
  constructor(
    public dialogRef: MatDialogRef<SuccessfullComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    
    this.messages = this.data.messages;
    this.nameLeft = this.data.nameLeft;
    this.nameRight = this.data.nameRight;
  }

  leftButon(){
    this.dialogRef.close("leftButton");

  }

  rightButon(){
    this.dialogRef.close("rightButton");

  }
}
