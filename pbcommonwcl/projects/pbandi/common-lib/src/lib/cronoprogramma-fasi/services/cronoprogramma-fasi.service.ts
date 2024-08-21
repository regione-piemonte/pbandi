/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AllegatiCronoprogrammaFasi } from '../commons/dto/allegati-cronoprogramma-fasi';
import { CronoprogrammaListFasiItemAllegatiInterface } from '../commons/dto/cronoprogramma-list-fasi-item-allegati-interface';
import { ResponseCodeMessageAllegati } from '../../shared/commons/dto/response-code-message';
import { EsitoDTO } from './esito-dto';

@Injectable()
export class CronoprogrammaFasiService {
  constructor(
    private http: HttpClient
  ) { }

  PATH_BASE = "/restfacade/cronoprogrammaFasi";


  //cronoprogramma service 

  getCodiceProgetto(apiURL: string, idProgetto: number) {
    let url = apiURL + "/restfacade/cronoprogramma/progetto";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get(url, { params: params, responseType: 'text' });
  }

  getDtPresentazioneDomanda(apiURL: string, idProgetto: number) {
    let url = apiURL + "/restfacade/cronoprogramma/dataPresentazioneDomanda";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Date>(url, { params: params });
  }

  //cronoprogramma fasi service

  getDataCronoprogramma(apiURL: string, idProgetto: number): Observable<Array<CronoprogrammaListFasiItemAllegatiInterface>> {
    let url = apiURL + this.PATH_BASE + "/getDataCronoprogramma";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());

    return this.http.get<Array<CronoprogrammaListFasiItemAllegatiInterface>>(url, { params: params });
  }

  saveDataCronoprogramma(apiURL: string, idProgetto: number, dataCronoprograma: Array<CronoprogrammaListFasiItemAllegatiInterface>): Observable<ResponseCodeMessageAllegati> {
    let url = apiURL + this.PATH_BASE + "/saveDataCronoprogramma";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.post<ResponseCodeMessageAllegati>(url, dataCronoprograma, { params: params });
  }

  disassociaAllegato(apiURL: string, idDocumentoIndex: number, idProgettoIter: number, idProgetto: number): Observable<EsitoDTO> {
    let url = apiURL + this.PATH_BASE + '/disassociaAllegato';
    let params = new HttpParams()
      .set("idDocumentoIndex", idDocumentoIndex.toString())
      .set("idProgettoIter", idProgettoIter.toString())
      .set("idProgetto", idProgetto.toString());
    return this.http.post<EsitoDTO>(url, { body: null }, { params: params });
  }

  getAllegatiIterProgetto(apiURL: string, idIter: number, idProgetto: number): Observable<Array<AllegatiCronoprogrammaFasi>> {
    let url = apiURL + this.PATH_BASE + '/getAllegatiIterProgetto';
    let params = new HttpParams()
      .set("idIter", idIter.toString())
      .set("idProgetto", idProgetto.toString());
    return this.http.get<Array<AllegatiCronoprogrammaFasi>>(url, { params: params });
  }

}
