import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListChooseChapterComponent } from './list-choose-chapter.component';

describe('ListChooseChapterComponent', () => {
  let component: ListChooseChapterComponent;
  let fixture: ComponentFixture<ListChooseChapterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListChooseChapterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListChooseChapterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
