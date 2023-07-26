import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-feedbaack',
  templateUrl: './feedbaack.component.html',
  styleUrls: ['./feedbaack.component.css']
})
export class FeedbaackComponent implements OnInit {
  @Inject(MAT_DIALOG_DATA) public data: any
  feedBack : String = "";

  constructor(
    public dialogRef: MatDialogRef<FeedbaackComponent>,
  ) { }

  ngOnInit(): void {
    this.feedBack = "";
  }

  close(){
    this.dialogRef.close(this.feedBack);
  }

}
