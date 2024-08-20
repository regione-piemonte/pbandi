/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { CronoprogrammaItem } from '../commons/dto/cronoprogramma-item';
import { TipoOperazioneDTO } from '../commons/dto/tipo-operazione-dto';
import { Iter } from "../commons/dto/iter";
import { EsitoOperazioni } from "../../shared/commons/dto/esito-operazioni";
import { SalvaAffidamentoRequest } from "../../affidamenti/commons/requests/salva-affidamento-request";
import { SalvaFasiMonitoraggioGestioneRequest } from "../commons/request/salva-fasi-monitoraggio-gestione";
import { ValidazioneDatiRequest } from "../commons/request/validazione-dati-request";
import { MotivoScostamento } from "../commons/dto/motivo-scostamento";
import { ExecResults } from "../commons/dto/exec-results";
import { EsitoSaveFasiMonitoraggio } from "../commons/dto/EsitoSaveFasiMonitoraggio";
import { ResponseGetFasiMonit } from "../commons/dto/response-get-fasi-monit";

@Injectable()
export class CronoprogrammaService {
  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  getCodiceProgetto(idProgetto: number) {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/progetto";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get(url, { params: params, responseType: 'text' });
  }

  getProgrammazioneByIdProgetto(idProgetto: number) {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/programmazione";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get(url, { params: params, responseType: 'text' });
  }

  getTipoOperazione(idProgetto: number, programmazione: string): Observable<TipoOperazioneDTO> {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/tipoOperazione";
    let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("programmazione", programmazione);
    return this.http.get<TipoOperazioneDTO>(url, { params: params });
  }

  getFasiMonitoraggioGestione(idProgetto: number, programmazione: string): Observable<ResponseGetFasiMonit> {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/fasiMonitoraggioGestione";
    let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("programmazione", programmazione);
    return this.http.get<ResponseGetFasiMonit>(url, { params: params });
  }
  getFasiMonitoraggioAvvio(idProgetto: number, programmazione: string, idIter: number): Observable<Array<CronoprogrammaItem>> {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/fasiMonitoraggioAvvio";
    let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("programmazione", programmazione).set("idIter", idIter.toString());
    return this.http.get<Array<CronoprogrammaItem>>(url, { params: params });
  }
  getIter(idTipoOperazione: number, programmazione: string): Observable<Array<Iter>> {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/iter";
    let params = new HttpParams().set("idTipoOperazione", idTipoOperazione.toString()).set("programmazione", programmazione);
    return this.http.get<Array<Iter>>(url, { params: params });
  }

  salvaFasiMonitoraggioGestione(request: SalvaFasiMonitoraggioGestioneRequest) {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/fasiMonitoraggioGestione";
    return this.http.post<EsitoSaveFasiMonitoraggio>(url, request);
  }

  salvaFasiMonitoraggioAvvio(request: SalvaFasiMonitoraggioGestioneRequest) {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/fasiMonitoraggioAvvio";
    return this.http.post<EsitoSaveFasiMonitoraggio>(url, request);
  }

  //spostato la validazione di questo servizio su angular
  /*validateDatiCronoProgramma(request: ValidazioneDatiRequest): Observable<ExecResults> {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/cronoprogrammaGestione/validazioneDati";
    return this.http.post<ExecResults>(url, request);
  }*/

  //spostato la validazione di questo servizio su angular
  /*validateDatiCronoProgrammaAvvio(request: ValidazioneDatiRequest): Observable<ExecResults> {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/cronoprogrammaAvvio/validazioneDati";
    return this.http.post<ExecResults>(url, request);
  }*/

  getDtConcessione(idProgetto: number) {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/dataConcessione";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Date>(url, { params: params });
  }

  getDtPresentazioneDomanda(idProgetto: number) {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/dataPresentazioneDomanda";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Date>(url, { params: params });
  }

  getMotiviScostamento() {
    let url = this.configService.getApiURL() + "/restfacade/cronoprogramma/motivoScostamento";
    return this.http.get<Array<MotivoScostamento>>(url);
  }
}
