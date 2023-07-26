import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubcribeMngFormComponent } from './subcribe-mng-form.component';

describe('SubcribeMngFormComponent', () => {
  let component: SubcribeMngFormComponent;
  let fixture: ComponentFixture<SubcribeMngFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubcribeMngFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubcribeMngFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
