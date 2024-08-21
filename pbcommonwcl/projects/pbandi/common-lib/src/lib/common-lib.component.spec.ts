/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommonLibComponent } from './common-lib.component';

describe('CommonLibComponent', () => {
  let component: CommonLibComponent;
  let fixture: ComponentFixture<CommonLibComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommonLibComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommonLibComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
