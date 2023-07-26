import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LecturePreviewComponent } from './lecture-preview.component';

describe('LecturePreviewComponent', () => {
  let component: LecturePreviewComponent;
  let fixture: ComponentFixture<LecturePreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LecturePreviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LecturePreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
