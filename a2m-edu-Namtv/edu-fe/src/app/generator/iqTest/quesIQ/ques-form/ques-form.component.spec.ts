import { ComponentFixture, TestBed } from '@angular/core/testing';
import { IqQuesFormComponent } from './ques-form.component';


describe('IqTestFormComponent', () => {
  let component: IqQuesFormComponent;
  let fixture: ComponentFixture<IqQuesFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqQuesFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqQuesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
