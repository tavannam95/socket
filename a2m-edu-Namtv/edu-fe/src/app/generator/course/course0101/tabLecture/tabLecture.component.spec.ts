import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabSubjectComponent } from './tabLecture.component';

describe('TabSubjectComponent', () => {
  let component: TabSubjectComponent;
  let fixture: ComponentFixture<TabSubjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabSubjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
