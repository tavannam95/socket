import { Component, Input, OnChanges, OnInit } from '@angular/core';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.css'],
})
export class SpinnerComponent implements OnInit, OnChanges {
  @Input() visible: any = false;

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges(): void {}
}
