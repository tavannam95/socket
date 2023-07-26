import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostDiscussComponent } from './post-discuss.component';

describe('PostDiscussComponent', () => {
  let component: PostDiscussComponent;
  let fixture: ComponentFixture<PostDiscussComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostDiscussComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostDiscussComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
