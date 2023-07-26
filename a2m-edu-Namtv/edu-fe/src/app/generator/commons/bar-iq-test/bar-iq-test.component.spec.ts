import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarIqTestComponent } from './bar-iq-test.component';

describe('BarIqTestComponent', () => {
  let component: BarIqTestComponent;
  let fixture: ComponentFixture<BarIqTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BarIqTestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BarIqTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
