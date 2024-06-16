import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GiftcardsAdminComponent } from './giftcards-admin.component';

describe('GiftcardsAdminComponent', () => {
  let component: GiftcardsAdminComponent;
  let fixture: ComponentFixture<GiftcardsAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GiftcardsAdminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GiftcardsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
