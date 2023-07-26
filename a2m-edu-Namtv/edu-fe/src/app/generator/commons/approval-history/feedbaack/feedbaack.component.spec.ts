import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbaackComponent } from './feedbaack.component';

describe('FeedbaackComponent', () => {
  let component: FeedbaackComponent;
  let fixture: ComponentFixture<FeedbaackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FeedbaackComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FeedbaackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
