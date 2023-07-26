import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCloneSubjectComponent } from './list-clone-subject.component';

describe('ListCloneSubjectComponent', () => {
  let component: ListCloneSubjectComponent;
  let fixture: ComponentFixture<ListCloneSubjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListCloneSubjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListCloneSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
