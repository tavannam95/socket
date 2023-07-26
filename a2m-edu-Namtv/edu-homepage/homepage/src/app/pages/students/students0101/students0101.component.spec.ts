import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Students0101Component } from './students0101.component';

describe('Students0101Component', () => {
  let component: Students0101Component;
  let fixture: ComponentFixture<Students0101Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Students0101Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Students0101Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
