import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LectureFormComponent } from './lecture-form.component';

describe('LectureFormComponent', () => {
  let component: LectureFormComponent;
  let fixture: ComponentFixture<LectureFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LectureFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LectureFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
