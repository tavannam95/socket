import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyEmailPopupComponent } from './modify-email-popup.component';

describe('ModifyEmailPopupComponent', () => {
  let component: ModifyEmailPopupComponent;
  let fixture: ComponentFixture<ModifyEmailPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyEmailPopupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyEmailPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
