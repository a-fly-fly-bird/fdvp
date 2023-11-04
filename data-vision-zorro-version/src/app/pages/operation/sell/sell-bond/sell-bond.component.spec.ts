import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellBondComponent } from './sell-bond.component';

describe('SellBondComponent', () => {
  let component: SellBondComponent;
  let fixture: ComponentFixture<SellBondComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SellBondComponent]
    });
    fixture = TestBed.createComponent(SellBondComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
