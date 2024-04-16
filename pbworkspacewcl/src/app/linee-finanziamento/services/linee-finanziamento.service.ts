/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { BandoProcesso } from "../commons/dto/bando-processo";
import { EsitoAvviaProgettiDTO } from "../commons/dto/esito-avvia-progetti-dto";
import { InizializzaLineeDiFinanziamentoDTO } from "../commons/dto/inizializza-linee-di-finanziamento-dto";
import { Progetto } from "../commons/dto/progetto";
import { AvviaProgettiRequest } from "../commons/requests/avvia-progetti-request";
import { RicercaProgettiRequest } from "../commons/requests/ricerca-progetti-request";

@Injectable()
export class LineeFinanziamentoService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  inizializzaLineeDiFinanziamento(progrBandoLineaIntervento: number, idSoggetto: number, codiceRuolo: string): Observable<InizializzaLineeDiFinanziamentoDTO> {
    let url = this.configService.getApiURL() + "/restfacade/lineeDiFinanziamento/inizializzaLineeDiFinanziamento";
    let params = new HttpParams()
      .set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString())
      .set("idSoggetto", idSoggetto.toString())
      .set("codiceRuolo", codiceRuolo);
    return this.http.get<InizializzaLineeDiFinanziamentoDTO>(url, { params: params });
  }

  lineeDiFinanziamento(descrizione: string): Observable<Array<BandoProcesso>> {
    let url = this.configService.getApiURL() + "/restfacade/lineeDiFinanziamento/lineeDiFinanziamento";
    let params = new HttpParams();
    if (descrizione) {
      params = params.set("descrizione", descrizione);
    }
    return this.http.get<Array<BandoProcesso>>(url, { params: params });
  }

  ricercaProgetti(request: RicercaProgettiRequest): Observable<Array<Progetto>> {
    let url = this.configService.getApiURL() + "/restfacade/lineeDiFinanziamento/ricercaProgetti";
    return this.http.post<Array<Progetto>>(url, request);
  }

  avviaProgetti(request: AvviaProgettiRequest): Observable<EsitoAvviaProgettiDTO> {
    let url = this.configService.getApiURL() + "/restfacade/lineeDiFinanziamento/avviaProgetti";
    return this.http.post<EsitoAvviaProgettiDTO>(url, request);
  }
}