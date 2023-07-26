import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyForCourseComponent } from './applyForCourse.component';

describe('ApplyForCourseComponent', () => {
  let component: ApplyForCourseComponent;
  let fixture: ComponentFixture<ApplyForCourseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApplyForCourseComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ApplyForCourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
