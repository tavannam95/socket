import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InpVcComponent } from './inp-vc.component';

describe('InpVcComponent', () => {
  let component: InpVcComponent;
  let fixture: ComponentFixture<InpVcComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InpVcComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InpVcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
