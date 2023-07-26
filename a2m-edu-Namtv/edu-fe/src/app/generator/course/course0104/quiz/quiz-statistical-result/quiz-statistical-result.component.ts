import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-quiz-statistical-result',
  templateUrl: './quiz-statistical-result.component.html',
  styleUrls: ['./quiz-statistical-result.component.css']
})
export class QuizStatisticalResultComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  
  changeTab(tabNumber: any){
    const ele = document.getElementById('quizStatistical'+tabNumber);

      ele?.click();
  }
}
