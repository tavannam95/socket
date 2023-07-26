import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Edu0202Service } from 'src/app/services/edu/edu0202.service';

@Component({
  selector: 'app-total-candidate',
  templateUrl: './total-candidate.component.html',
  styleUrls: ['./total-candidate.component.css']
})
export class TotalCandidateComponent implements OnInit {
  totalCandidate :any;
  pending :any;
  inProgress :any;

  constructor(
    private router: Router,
    private edu0202Service: Edu0202Service,
    private titleService:Title,
  ) { }


  ngOnInit(): void {
    this.getCountCandidateAllType();
  }

  gotoCandidate(type : any){
    this.router.navigate(['/edu/edu0202'], {
      queryParams: { type: type },
    });
  }

  getCountCandidateAllType(){
    this.edu0202Service.getCountCandidateAllType().subscribe(res=>{
     let string = res.totalItems;
     let totalItems = string.split("!#@");
     this.totalCandidate = totalItems[0];
     this.pending = totalItems[1];
     this.inProgress = totalItems[2];
     if(this.totalCandidate>0){
       this.titleService.setTitle("("+this.totalCandidate+") "+"A2M Education Management System");
     }else{
      this.titleService.setTitle("A2M Education Management System");
     }
    })
  }

}
