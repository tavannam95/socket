import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Blog0102Component } from './blog0102.component';

describe('Blog0102Component', () => {
  let component: Blog0102Component;
  let fixture: ComponentFixture<Blog0102Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Blog0102Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Blog0102Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
