import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Sys0104wComponent } from './sys0104w.component';

describe('Sys0104wComponent', () => {
  let component: Sys0104wComponent;
  let fixture: ComponentFixture<Sys0104wComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Sys0104wComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Sys0104wComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
