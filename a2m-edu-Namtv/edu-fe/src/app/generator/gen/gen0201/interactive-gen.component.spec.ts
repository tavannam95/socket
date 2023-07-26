import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InteractiveGenComponent } from './interactive-gen.component';



describe('InteractiveGenComponent', () => {
  let component: InteractiveGenComponent;
  let fixture: ComponentFixture<InteractiveGenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InteractiveGenComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InteractiveGenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
