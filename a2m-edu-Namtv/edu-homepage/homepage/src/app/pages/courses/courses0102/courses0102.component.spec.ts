import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Courses0102Component } from './courses0102.component';

describe('Courses0102Component', () => {
  let component: Courses0102Component;
  let fixture: ComponentFixture<Courses0102Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Courses0102Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Courses0102Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
