import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalCandidateComponent } from './total-candidate.component';

describe('TotalCandidateComponent', () => {
  let component: TotalCandidateComponent;
  let fixture: ComponentFixture<TotalCandidateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TotalCandidateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TotalCandidateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
