import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPostNotificatonComponent } from './list-post-notificaton.component';

describe('ListPostNotificatonComponent', () => {
  let component: ListPostNotificatonComponent;
  let fixture: ComponentFixture<ListPostNotificatonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListPostNotificatonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListPostNotificatonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
