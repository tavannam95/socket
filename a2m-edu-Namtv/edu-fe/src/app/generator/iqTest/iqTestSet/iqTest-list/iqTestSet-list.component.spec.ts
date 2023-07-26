import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IqTestSetListComponent } from './iqTestSet-list.component';

describe('IqTestListComponent', () => {
  let component: IqTestSetListComponent;
  let fixture: ComponentFixture<IqTestSetListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqTestSetListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqTestSetListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
