import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabStandardComponent } from './tabStandard.component';

describe('TabStandardComponent', () => {
  let component: TabStandardComponent;
  let fixture: ComponentFixture<TabStandardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabStandardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabStandardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
