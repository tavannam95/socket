import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizViewSubmittedComponent } from './quiz-view-submitted.component';

describe('QuizViewSubmittedComponent', () => {
  let component: QuizViewSubmittedComponent;
  let fixture: ComponentFixture<QuizViewSubmittedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuizViewSubmittedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuizViewSubmittedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
