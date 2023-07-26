import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StandardSummaryTableComponent } from './standard-summary-table.component';

describe('StandardSummaryTableComponent', () => {
  let component: StandardSummaryTableComponent;
  let fixture: ComponentFixture<StandardSummaryTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StandardSummaryTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StandardSummaryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
