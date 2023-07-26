import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Course0201Component } from './course0201.component';

describe('Course0201Component', () => {
  let component: Course0201Component;
  let fixture: ComponentFixture<Course0201Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Course0201Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Course0201Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
