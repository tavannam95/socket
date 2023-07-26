import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IqQuesListComponent } from './ques-list.component';

describe('IqTestListComponent', () => {
  let component: IqQuesListComponent;
  let fixture: ComponentFixture<IqQuesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqQuesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqQuesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
