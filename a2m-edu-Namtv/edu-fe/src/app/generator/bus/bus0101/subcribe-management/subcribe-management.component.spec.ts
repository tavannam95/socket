import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubcribeManagementComponent } from './subcribe-management.component';

describe('SubcribeManagementComponent', () => {
  let component: SubcribeManagementComponent;
  let fixture: ComponentFixture<SubcribeManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubcribeManagementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubcribeManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
