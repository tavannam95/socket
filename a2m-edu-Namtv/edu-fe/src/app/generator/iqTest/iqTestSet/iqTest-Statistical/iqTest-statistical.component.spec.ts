import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IqTestStatisticalComponent } from './iqTest-statistical.component';

describe('IqTestStatisticalComponent', () => {
  let component: IqTestStatisticalComponent;
  let fixture: ComponentFixture<IqTestStatisticalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqTestStatisticalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqTestStatisticalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
