/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';
import { ResponseGetIndicatori } from "../commons/dto/response-get-indicatori";
import { SalvaIndicatoriRequest } from "../commons/dto/salva-indicatori-request";
import { ValidazioneDatiIndicatoriRequest } from "../commons/dto/validazione-dati-indicatori-request";
import { ExecResults } from "../../cronoprogramma/commons/dto/exec-results";
import { EsitoSaveIndicatori } from "../commons/dto/esito-save-indicatori";
import { ConfigService } from "../../core/services/config.service";


@Injectable()
export class IndicatoriService {
  static readonly getIndicatoriPath = '/indicatori';
  static readonly saveIndicatoriGestionePath = '/indicatori/indicatoriGestione';
  static readonly getCodiceProgettoPath = '/indicatori/progetto';
  static readonly controllaDatiPerSalvataggioPath = '/indicatori/validazioneDati';

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) {
  }

  /**
   * Finds list of indicatori monitoraggio by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getIndicatoriResponse(idProgetto?: number, isBandoSif?: boolean): __Observable<HttpResponse<ResponseGetIndicatori>> {
    let __params = new HttpParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString()).set("isBandoSif", isBandoSif.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.configService.getApiURL() + `/restfacade/indicatori/indicatoriGestione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as HttpResponse<ResponseGetIndicatori>;
      })
    );
  }
  /**
   * Finds list of indicatori monitoraggio by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getIndicatori(idProgetto?: number, isBandoSif?: boolean): __Observable<ResponseGetIndicatori> {
    return this.getIndicatoriResponse(idProgetto, isBandoSif).pipe(
      __map(_r => _r.body as ResponseGetIndicatori)
    );
  }

  /**
   * Saves validated indicatori data to database
   * @param body undefined
   * @return successful operation
   */
  saveIndicatoriGestioneResponse(body?: SalvaIndicatoriRequest): __Observable<HttpResponse<EsitoSaveIndicatori>> {
    let __params = new HttpParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.configService.getApiURL() + `/restfacade/indicatori/indicatoriGestione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as HttpResponse<EsitoSaveIndicatori>;
      })
    );
  }
  /**
   * Saves validated indicatori data to database
   * @param body undefined
   * @return successful operation
   */
  saveIndicatoriGestione(body?: SalvaIndicatoriRequest): __Observable<EsitoSaveIndicatori> {
    return this.saveIndicatoriGestioneResponse(body).pipe(
      __map(_r => _r.body as EsitoSaveIndicatori)
    );
  }

  /**
   * Finds Codice Progetto by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getCodiceProgettoResponse(idProgetto?: number): __Observable<HttpResponse<string>> {
    let __params = new HttpParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.configService.getApiURL() + `/restfacade/indicatori/progetto`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'text'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as HttpResponse<string>;
      })
    );
  }
  /**
   * Finds Codice Progetto by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getCodiceProgetto(idProgetto?: number): __Observable<string> {
    return this.getCodiceProgettoResponse(idProgetto).pipe(
      __map(_r => _r.body as string)
    );
  }

  /* controlli spostati su angular
  controllaDatiPerSalvataggioGestioneResponse(body?: ValidazioneDatiIndicatoriRequest): __Observable<HttpResponse<ExecResults>> {
    let __params = new HttpParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.configService.getApiURL() + `/restfacade/indicatori/indicatoriGestione/validazioneDati`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as HttpResponse<ExecResults>;
      })
    );
  }

  controllaDatiPerSalvataggioGestione(body?: ValidazioneDatiIndicatoriRequest): __Observable<ExecResults> {
    return this.controllaDatiPerSalvataggioGestioneResponse(body).pipe(
      __map(_r => _r.body as ExecResults)
    );
  }*/
}
