/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';

@Injectable()
export class DeclaratoriaService {
  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  
}
