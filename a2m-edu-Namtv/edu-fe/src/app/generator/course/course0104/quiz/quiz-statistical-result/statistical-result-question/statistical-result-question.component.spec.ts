import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticalResultQuestionComponent } from './statistical-result-question.component';

describe('StatisticalResultQuestionComponent', () => {
  let component: StatisticalResultQuestionComponent;
  let fixture: ComponentFixture<StatisticalResultQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StatisticalResultQuestionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StatisticalResultQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
