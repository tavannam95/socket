import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassAnnouncementComponent } from './class-announcement.component';

describe('ClassAnnouncementComponent', () => {
  let component: ClassAnnouncementComponent;
  let fixture: ComponentFixture<ClassAnnouncementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClassAnnouncementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassAnnouncementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
