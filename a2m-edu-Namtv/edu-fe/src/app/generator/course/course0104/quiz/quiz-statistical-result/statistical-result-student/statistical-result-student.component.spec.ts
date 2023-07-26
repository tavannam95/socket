import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticalResultStudentComponent } from './statistical-result-student.component';

describe('StatisticalResultStudentComponent', () => {
  let component: StatisticalResultStudentComponent;
  let fixture: ComponentFixture<StatisticalResultStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StatisticalResultStudentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StatisticalResultStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
