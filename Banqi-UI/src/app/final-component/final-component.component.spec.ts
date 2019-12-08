import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalComponentComponent } from './final-component.component';

describe('FinalComponentComponent', () => {
  let component: FinalComponentComponent;
  let fixture: ComponentFixture<FinalComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FinalComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FinalComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
