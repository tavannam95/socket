import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventCourseComponent } from './eventCourse.component';

describe('EventCourseComponent', () => {
  let component: EventCourseComponent;
  let fixture: ComponentFixture<EventCourseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventCourseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventCourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
