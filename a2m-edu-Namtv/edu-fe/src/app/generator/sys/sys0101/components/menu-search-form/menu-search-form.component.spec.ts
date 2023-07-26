import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuSearchFormComponent } from './menu-search-form.component';

describe('MenuSearchFormComponent', () => {
  let component: MenuSearchFormComponent;
  let fixture: ComponentFixture<MenuSearchFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuSearchFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
