import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Instructors0101Component } from './instructors0101.component';

describe('Instructors0101Component', () => {
  let component: Instructors0101Component;
  let fixture: ComponentFixture<Instructors0101Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Instructors0101Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Instructors0101Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
