import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListTargetComponent } from './list-target.component';

describe('ListTargetComponent', () => {
  let component: ListTargetComponent;
  let fixture: ComponentFixture<ListTargetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListTargetComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListTargetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
