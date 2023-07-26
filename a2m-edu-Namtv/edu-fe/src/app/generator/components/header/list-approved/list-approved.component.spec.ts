import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListApprovedComponent } from './list-approved.component';

describe('ListApprovedComponent', () => {
  let component: ListApprovedComponent;
  let fixture: ComponentFixture<ListApprovedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListApprovedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListApprovedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
