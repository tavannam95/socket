import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Gen0601Component } from './gen0601.component';

describe('Gen0601Component', () => {
  let component: Gen0601Component;
  let fixture: ComponentFixture<Gen0601Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Gen0601Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Gen0601Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
