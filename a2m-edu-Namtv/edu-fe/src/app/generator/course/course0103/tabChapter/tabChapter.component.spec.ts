import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabChapterComponent } from './tabChapter.component';

describe('TabChapterComponent', () => {
  let component: TabChapterComponent;
  let fixture: ComponentFixture<TabChapterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabChapterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabChapterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
