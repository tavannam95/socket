import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ControlContainer, NgForm } from '@angular/forms';
import { dE } from '@fullcalendar/core/internal-common';

@Component({
  selector: 'app-inp-vc',
  templateUrl: './inp-vc.component.html',
  styleUrls: ['./inp-vc.component.css'],
  viewProviders: [{ provide: ControlContainer, useExisting: NgForm }],
})
export class InpVcComponent implements OnInit {

  @Input("name") inputName = '';
  @Input("inpValue") inpValue = '';
  @Input("optionLabel") optionLabel = '';
  @Input("optionValue") optionValue = '';
  @Input("inpLable") inpLable = '';
  @Input("requiredMessage") requiredMessage = '';
  @Input("submitted") submitted = false;
  @Input("vc") vc = "VIEW"; //VIEW,UPD,INS

  @Input() type = "text";
  @Input() minlength = 0;
  @Input("requiredMin") requiredMin = '';
  @Input() maxlength = NaN;
  @Input("requiredMax") requiredMax = '';
  @Input() placeholder = '';
  @Input("required") required = false;
  @Input() ngModelOptions = { standalone: true };
  @Input("elmType") elmType = "INP";
  @Input("option") option = "INP";
  @Input("minDate") minDate :any;
  @Input("dateFormat") dateFormat = "dd/mm/yyyy";
  @Input("optionsDropDown") optionsDropDown: any[] = [];

  @Output() inpValueChange = new EventEmitter<any>();
  constructor() { }

  ngOnInit(): void {

  }

  changeInpValue(event: any){
    this.inpValueChange.emit(this.inpValue);
  }

  showComNm(inpValue : any) {
    // optionsDropDown
    let result : String = "";
    this.optionsDropDown.forEach((element:any) => {
      if(element.commCd==inpValue){
        result = element.commNm;
      }
    });
    return result;
  }

}
