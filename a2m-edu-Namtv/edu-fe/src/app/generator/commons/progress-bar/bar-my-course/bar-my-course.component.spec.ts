import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarMyCourseComponent } from './bar-my-course.component';

describe('BarMyCourseComponent', () => {
  let component: BarMyCourseComponent;
  let fixture: ComponentFixture<BarMyCourseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BarMyCourseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BarMyCourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
