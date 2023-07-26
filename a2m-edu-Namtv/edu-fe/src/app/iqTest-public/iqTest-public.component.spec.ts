import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IqTestPublicComponent } from './iqTest-public.component';

describe('IqTestPublicComponent', () => {
  let component: IqTestPublicComponent;
  let fixture: ComponentFixture<IqTestPublicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqTestPublicComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqTestPublicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
