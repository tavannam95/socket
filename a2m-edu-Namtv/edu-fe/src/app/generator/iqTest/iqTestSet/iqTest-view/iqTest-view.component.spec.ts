import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IqTestViewComponent } from './iqTest-view.component';

describe('IqTestViewComponent', () => {
  let component: IqTestViewComponent;
  let fixture: ComponentFixture<IqTestViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqTestViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqTestViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
