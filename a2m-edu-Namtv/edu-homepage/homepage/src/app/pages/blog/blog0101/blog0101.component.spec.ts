import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Blog0101Component } from './blog0101.component';

describe('Blog0101Component', () => {
  let component: Blog0101Component;
  let fixture: ComponentFixture<Blog0101Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Blog0101Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Blog0101Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
