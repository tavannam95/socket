import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleFormExcelComponent } from './schedule-form-excel.component';

describe('ScheduleFormExcelComponent', () => {
  let component: ScheduleFormExcelComponent;
  let fixture: ComponentFixture<ScheduleFormExcelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScheduleFormExcelComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleFormExcelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
