import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatChipsAutocompletePersonComponent } from './mat-chips-autocomplete-person.component';

describe('MatChipsAutocompletePersonComponent', () => {
  let component: MatChipsAutocompletePersonComponent;
  let fixture: ComponentFixture<MatChipsAutocompletePersonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MatChipsAutocompletePersonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MatChipsAutocompletePersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
