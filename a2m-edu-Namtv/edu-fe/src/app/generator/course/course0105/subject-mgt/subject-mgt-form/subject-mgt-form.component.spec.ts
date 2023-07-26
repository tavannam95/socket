import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectMgtFormComponent } from './subject-mgt-form.component';

describe('SubjectMgtFormComponent', () => {
  let component: SubjectMgtFormComponent;
  let fixture: ComponentFixture<SubjectMgtFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubjectMgtFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubjectMgtFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
