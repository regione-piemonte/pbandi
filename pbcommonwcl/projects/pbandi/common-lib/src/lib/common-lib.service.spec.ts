/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { TestBed } from '@angular/core/testing';

import { CommonLibService } from './common-lib.service';

describe('CommonLibService', () => {
  let service: CommonLibService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommonLibService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
