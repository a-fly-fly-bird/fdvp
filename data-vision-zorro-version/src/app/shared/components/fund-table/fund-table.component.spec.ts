import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FundTableComponent } from './fund-table.component';

describe('FundTableComponent', () => {
  let component: FundTableComponent;
  let fixture: ComponentFixture<FundTableComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FundTableComponent]
    });
    fixture = TestBed.createComponent(FundTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
