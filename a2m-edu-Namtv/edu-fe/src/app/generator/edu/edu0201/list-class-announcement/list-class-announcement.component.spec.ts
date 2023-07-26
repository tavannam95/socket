import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListClassAnnouncementComponent } from './list-class-announcement.component';

describe('ListClassAnnouncementComponent', () => {
  let component: ListClassAnnouncementComponent;
  let fixture: ComponentFixture<ListClassAnnouncementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListClassAnnouncementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListClassAnnouncementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
