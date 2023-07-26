import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IqTestStatisticalResultQuestionComponent } from './iqTeststatistical-result-question.component';

describe('IqTestStatisticalResultQuestionComponent', () => {
  let component: IqTestStatisticalResultQuestionComponent;
  let fixture: ComponentFixture<IqTestStatisticalResultQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqTestStatisticalResultQuestionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqTestStatisticalResultQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
