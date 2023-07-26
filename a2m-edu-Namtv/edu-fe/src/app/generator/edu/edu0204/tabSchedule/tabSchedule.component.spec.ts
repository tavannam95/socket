import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabScheduleComponent } from './tabSchedule.component';

describe('TabScheduleComponent', () => {
  let component: TabScheduleComponent;
  let fixture: ComponentFixture<TabScheduleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabScheduleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
