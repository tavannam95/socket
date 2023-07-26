import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Sys0102Component } from './sys0102.component';

describe('Sys0102Component', () => {
  let component: Sys0102Component;
  let fixture: ComponentFixture<Sys0102Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Sys0102Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Sys0102Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
