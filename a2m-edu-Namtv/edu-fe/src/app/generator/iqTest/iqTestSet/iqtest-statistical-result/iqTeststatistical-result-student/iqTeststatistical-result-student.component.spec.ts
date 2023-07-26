import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IqTestStatisticalResultStudentComponent } from './iqTeststatistical-result-student.component';

describe('IqTestStatisticalResultStudentComponent', () => {
  let component: IqTestStatisticalResultStudentComponent;
  let fixture: ComponentFixture<IqTestStatisticalResultStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqTestStatisticalResultStudentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqTestStatisticalResultStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
