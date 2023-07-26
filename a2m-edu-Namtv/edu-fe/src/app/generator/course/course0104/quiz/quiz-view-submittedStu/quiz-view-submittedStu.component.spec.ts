import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizViewSubmittedStuComponent } from './quiz-view-submittedStu.component';

describe('QuizViewSubmittedStuComponent', () => {
  let component: QuizViewSubmittedStuComponent;
  let fixture: ComponentFixture<QuizViewSubmittedStuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuizViewSubmittedStuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuizViewSubmittedStuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
