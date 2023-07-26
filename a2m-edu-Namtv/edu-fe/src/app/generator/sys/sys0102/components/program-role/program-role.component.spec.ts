import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgramRoleComponent } from './program-role.component';

describe('ProgramRoleComponent', () => {
  let component: ProgramRoleComponent;
  let fixture: ComponentFixture<ProgramRoleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProgramRoleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProgramRoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
