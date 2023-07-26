import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentFormExcelComponent } from './student-form-excel.component';

describe('StudentFormExcelComponent', () => {
  let component: StudentFormExcelComponent;
  let fixture: ComponentFixture<StudentFormExcelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentFormExcelComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentFormExcelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
