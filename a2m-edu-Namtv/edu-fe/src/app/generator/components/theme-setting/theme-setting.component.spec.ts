import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeSettingComponent } from './theme-setting.component';

describe('ThemeSettingComponent', () => {
  let component: ThemeSettingComponent;
  let fixture: ComponentFixture<ThemeSettingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ThemeSettingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ThemeSettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
