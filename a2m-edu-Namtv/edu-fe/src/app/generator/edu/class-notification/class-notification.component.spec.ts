import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassNotificationComponent } from './class-notification.component';

describe('ClassNotificationComponent', () => {
  let component: ClassNotificationComponent;
  let fixture: ComponentFixture<ClassNotificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClassNotificationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
