import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectScheduleTableComponent } from './subject-schedule-table.component';

describe('SubjectScheduleTableComponent', () => {
  let component: SubjectScheduleTableComponent;
  let fixture: ComponentFixture<SubjectScheduleTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubjectScheduleTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubjectScheduleTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
