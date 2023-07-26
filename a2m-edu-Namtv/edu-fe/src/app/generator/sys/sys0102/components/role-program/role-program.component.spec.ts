import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleProgramComponent } from './role-program.component';

describe('RoleProgramComponent', () => {
  let component: RoleProgramComponent;
  let fixture: ComponentFixture<RoleProgramComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleProgramComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoleProgramComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
