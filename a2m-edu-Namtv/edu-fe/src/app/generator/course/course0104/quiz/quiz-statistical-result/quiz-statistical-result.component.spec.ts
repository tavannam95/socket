import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizStatisticalResultComponent } from './quiz-statistical-result.component';

describe('QuizStatisticalResultComponent', () => {
  let component: QuizStatisticalResultComponent;
  let fixture: ComponentFixture<QuizStatisticalResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuizStatisticalResultComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuizStatisticalResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
