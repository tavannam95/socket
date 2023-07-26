import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseStatisticalComponent } from './course-statistical.component';

describe('CourseStatisticalComponent', () => {
  let component: CourseStatisticalComponent;
  let fixture: ComponentFixture<CourseStatisticalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseStatisticalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseStatisticalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
