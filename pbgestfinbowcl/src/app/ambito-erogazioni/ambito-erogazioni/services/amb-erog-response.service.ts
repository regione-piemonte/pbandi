/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { Observable } from 'rxjs';
import { PropostaErogazioneVO } from '../commons/proposta-erogazione-vo';

@Injectable({
  providedIn: 'root'
})
export class AmbErogResponseService {

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { }

  cercaProposteErogazione(bando: string, agevolazione: string, codiceFondoFinpis: string, denominazione: string, codiceProgetto: string, contrPreErogazione: string): Observable<Array<PropostaErogazioneVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoErogazione/getProposteErogazione';

    let params = new HttpParams()
      .set("progrBandoLinea", codiceFondoFinpis != null ? codiceFondoFinpis?.toString() : (bando!=null ? bando?.toString() : "-1"))
      .set("agevolazione", agevolazione ? agevolazione.toString() : "-1")
      .set("idSoggetto", denominazione ? denominazione?.toString() : "-1")
      .set("idProgetto", codiceProgetto ? codiceProgetto?.toString() : "-1")
      .set("contrPreErogazione", contrPreErogazione?.toString())   //da rivedere


    return this.http.get<Array<PropostaErogazioneVO>>(url, { params: params })/*.subscribe(data => {
      if (data) {
        this.finEro = data;
        this.finEroInfo.next(data);

        //console.log(data);

      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });*/

  }

  getSuggestion(value: string, id: number) {

    let url = this.configService.getApiURL() + '/restfacade/ambitoErogazione/getSuggestion';

    let params = new HttpParams().set("value", value.toString().toUpperCase()).set("id", id.toString());

    return this.http.get<any>(url, { params: params });
  }
}
