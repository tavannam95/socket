import { ComponentFixture, TestBed } from '@angular/core/testing';
import { IqTestByQuesListComponent } from './iqTestByQuesList-form.component';


describe('IqTestFormComponent', () => {
  let component: IqTestByQuesListComponent;
  let fixture: ComponentFixture<IqTestByQuesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IqTestByQuesListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IqTestByQuesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
