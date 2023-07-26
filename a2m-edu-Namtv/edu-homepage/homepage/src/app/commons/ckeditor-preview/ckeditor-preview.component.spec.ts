import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CkeditorPreviewComponent } from './ckeditor-preview.component';

describe('CkeditorPreviewComponent', () => {
  let component: CkeditorPreviewComponent;
  let fixture: ComponentFixture<CkeditorPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CkeditorPreviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CkeditorPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
