import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Instructors0102Component } from './instructors0102.component';

describe('Instructors0102Component', () => {
  let component: Instructors0102Component;
  let fixture: ComponentFixture<Instructors0102Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Instructors0102Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Instructors0102Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
