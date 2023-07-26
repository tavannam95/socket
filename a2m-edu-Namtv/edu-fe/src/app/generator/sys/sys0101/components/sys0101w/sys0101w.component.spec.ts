import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Sys0101wComponent } from './sys0101w.component';

describe('Sys0101wComponent', () => {
  let component: Sys0101wComponent;
  let fixture: ComponentFixture<Sys0101wComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Sys0101wComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Sys0101wComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
