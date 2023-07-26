import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListChooseSubjectComponent } from './list-choose-subject.component';

describe('ListChooseSubjectComponent', () => {
  let component: ListChooseSubjectComponent;
  let fixture: ComponentFixture<ListChooseSubjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListChooseSubjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListChooseSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
