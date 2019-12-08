import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameRuleComponent } from './game-rule.component';

describe('GameRuleComponent', () => {
  let component: GameRuleComponent;
  let fixture: ComponentFixture<GameRuleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameRuleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameRuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
