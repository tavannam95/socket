import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectMgtDetailComponent } from './subject-mgt-detail.component';

describe('SubjectMgtDetailComponent', () => {
  let component: SubjectMgtDetailComponent;
  let fixture: ComponentFixture<SubjectMgtDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubjectMgtDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubjectMgtDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
