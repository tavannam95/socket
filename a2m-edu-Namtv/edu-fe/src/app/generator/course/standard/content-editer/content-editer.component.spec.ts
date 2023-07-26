import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContentEditerComponent } from './content-editer.component';

describe('ContentEditerComponent', () => {
  let component: ContentEditerComponent;
  let fixture: ComponentFixture<ContentEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContentEditerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContentEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
