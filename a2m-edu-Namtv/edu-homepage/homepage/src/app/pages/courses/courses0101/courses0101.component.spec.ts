import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Courses0101Component } from './courses0101.component';

describe('Courses0101Component', () => {
  let component: Courses0101Component;
  let fixture: ComponentFixture<Courses0101Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Courses0101Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Courses0101Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
