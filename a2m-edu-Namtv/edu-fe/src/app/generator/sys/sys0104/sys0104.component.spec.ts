import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Sys0104Component } from './sys0104.component';

describe('Sys0104Component', () => {
  let component: Sys0104Component;
  let fixture: ComponentFixture<Sys0104Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Sys0104Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Sys0104Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
