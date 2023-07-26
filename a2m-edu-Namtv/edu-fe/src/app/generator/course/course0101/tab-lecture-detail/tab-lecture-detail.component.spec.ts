import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabLectureDetailComponent } from './tab-lecture-detail.component';

describe('TabLectureDetailComponent', () => {
  let component: TabLectureDetailComponent;
  let fixture: ComponentFixture<TabLectureDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabLectureDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabLectureDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
