/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { ResponseInizializzaIndicatori } from '../commons/responses/response-inizializza-indicatori';

@Injectable()
export class Indicatori2Service {
  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  inizializzaIndicatori(idProgetto: number): Observable<ResponseInizializzaIndicatori> {
    let url = this.configService.getApiURL() + "/restfacade/indicatori/inizializzaIndicatori";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<ResponseInizializzaIndicatori>(url, { params: params });
  }
}
