import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchTagComponent } from './search-tag.component';

describe('SearchTagComponent', () => {
  let component: SearchTagComponent;
  let fixture: ComponentFixture<SearchTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
