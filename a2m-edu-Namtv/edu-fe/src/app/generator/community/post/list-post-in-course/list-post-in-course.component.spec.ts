import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPostInCourseComponent } from './list-post-in-course.component';

describe('ListPostInCourseComponent', () => {
  let component: ListPostInCourseComponent;
  let fixture: ComponentFixture<ListPostInCourseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListPostInCourseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListPostInCourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
