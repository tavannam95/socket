import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuesLstAreaComponent } from './ques-lst-area.component';

describe('QuesLstAreaComponent', () => {
  let component: QuesLstAreaComponent;
  let fixture: ComponentFixture<QuesLstAreaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuesLstAreaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuesLstAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
