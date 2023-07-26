import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuDetailFormComponent } from './menu-detail-form.component';

describe('MenuDetailFormComponent', () => {
  let component: MenuDetailFormComponent;
  let fixture: ComponentFixture<MenuDetailFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuDetailFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuDetailFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
