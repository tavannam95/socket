import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormPostFormComponent } from './form-post-form.component';

describe('FormPostFormComponent', () => {
  let component: FormPostFormComponent;
  let fixture: ComponentFixture<FormPostFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FormPostFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormPostFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
