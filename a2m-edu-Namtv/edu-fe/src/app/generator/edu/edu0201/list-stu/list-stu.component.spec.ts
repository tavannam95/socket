import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListStuComponent } from './list-stu.component';

describe('ListStuComponent', () => {
  let component: ListStuComponent;
  let fixture: ComponentFixture<ListStuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListStuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListStuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
