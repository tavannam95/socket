import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatChipsAutocompleteComponent } from './mat-chips-autocomplete.component';

describe('MatChipsAutocompleteComponent', () => {
  let component: MatChipsAutocompleteComponent;
  let fixture: ComponentFixture<MatChipsAutocompleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MatChipsAutocompleteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MatChipsAutocompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
