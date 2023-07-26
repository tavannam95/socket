import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListTeaComponent } from './list-tea.component';

describe('ListTeaComponent', () => {
  let component: ListTeaComponent;
  let fixture: ComponentFixture<ListTeaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListTeaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListTeaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
