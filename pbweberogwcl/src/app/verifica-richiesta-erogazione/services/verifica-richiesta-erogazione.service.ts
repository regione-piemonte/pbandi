/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';

@Injectable({
  providedIn: 'root'
})
export class VerificaRichiestaErogazioneService {

  constructor(private configService: ConfigService,
              private http: HttpClient) { }

  getDettaglioVerificaErogazione(idProgetto: number, idRichiestaErogazione: number) {
		let url = this.configService.getApiURL() + '/restfacade/erogazione/dettaglioVerificaErogazione';
		let params = new HttpParams().set('idProgetto', idProgetto.toString()).set('idErogazione', idRichiestaErogazione.toString());
		return this.http.get<any>(url, { params: params });
	}

  postVerificaErogazione(idErogazione: number, verificato: boolean) {
		let url = this.configService.getApiURL() + '/restfacade/erogazione/verificaErogazione';
		return this.http.post<any>(url, { idErogazione, verificato });
	}
}
