import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Sam0101Component } from './sam0101.component';

describe('Sam0101Component', () => {
  let component: Sam0101Component;
  let fixture: ComponentFixture<Sam0101Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Sam0101Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Sam0101Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
