import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IqtestStatisticalResultComponent } from './iqTest-statistical-result.component';

describe('QuizStatisticalResultComponent', () => {
  let component: IqtestStatisticalResultComponent;
  let fixture: ComponentFixture<IqtestStatisticalResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqtestStatisticalResultComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqtestStatisticalResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
