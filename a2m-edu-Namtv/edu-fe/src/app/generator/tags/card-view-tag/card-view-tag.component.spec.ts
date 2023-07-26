import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardViewTagComponent } from './card-view-tag.component';

describe('CardViewTagComponent', () => {
  let component: CardViewTagComponent;
  let fixture: ComponentFixture<CardViewTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CardViewTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardViewTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
