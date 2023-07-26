import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Sam0102Component } from './sam0102.component';

describe('Sam0102Component', () => {
  let component: Sam0102Component;
  let fixture: ComponentFixture<Sam0102Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Sam0102Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Sam0102Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
