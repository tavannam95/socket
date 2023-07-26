import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovalHistoryComponent } from './approval-history.component';

describe('ApprovalHistoryComponent', () => {
  let component: ApprovalHistoryComponent;
  let fixture: ComponentFixture<ApprovalHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApprovalHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApprovalHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
