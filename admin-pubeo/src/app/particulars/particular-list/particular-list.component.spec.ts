import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticularListComponent } from './particular-list.component';

describe('ParticularListComponent', () => {
  let component: ParticularListComponent;
  let fixture: ComponentFixture<ParticularListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParticularListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticularListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
