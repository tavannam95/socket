import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentEventComponent } from './content-event.component';

describe('ContentEventComponent', () => {
  let component: ContentEventComponent;
  let fixture: ComponentFixture<ContentEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ContentEventComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ContentEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
