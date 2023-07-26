import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListApprovalComponent } from './list-approval.component';

describe('ListApprovalComponent', () => {
  let component: ListApprovalComponent;
  let fixture: ComponentFixture<ListApprovalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListApprovalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
