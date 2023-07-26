import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabClassComponent } from './tab-class.component';

describe('TabClassComponent', () => {
  let component: TabClassComponent;
  let fixture: ComponentFixture<TabClassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabClassComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabClassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
