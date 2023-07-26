import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectMgtComponent } from './subject-mgt.component';

describe('SubjectMgtComponent', () => {
  let component: SubjectMgtComponent;
  let fixture: ComponentFixture<SubjectMgtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubjectMgtComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubjectMgtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
