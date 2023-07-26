import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryQuizStudentComponent } from './history_quiz_stu.component';

describe('HistoryQuizStudentComponent', () => {
  let component: HistoryQuizStudentComponent;
  let fixture: ComponentFixture<HistoryQuizStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HistoryQuizStudentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoryQuizStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
