import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Students0102Component } from './students0102.component';

describe('Students0102Component', () => {
  let component: Students0102Component;
  let fixture: ComponentFixture<Students0102Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Students0102Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Students0102Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
