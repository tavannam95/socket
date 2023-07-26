import { ComponentFixture, TestBed } from '@angular/core/testing';
import { IqTestFormComponent } from './iqTest-form.component';


describe('IqTestFormComponent', () => {
  let component: IqTestFormComponent;
  let fixture: ComponentFixture<IqTestFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqTestFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqTestFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
