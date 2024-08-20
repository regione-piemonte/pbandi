/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../../shared/api/base-service';
import { ApiConfiguration as __Configuration } from '../../shared/api/api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../../shared/api/strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';
import { ResponseGetIndicatori } from "../commons/dto/response-get-indicatori";
import { SalvaIndicatoriRequest } from "../commons/dto/salva-indicatori-request";
import { ValidazioneDatiIndicatoriRequest } from "../commons/dto/validazione-dati-indicatori-request";
import { ExecResults } from "../../cronoprogramma/commons/dto/exec-results";
import { EsitoSaveIndicatori } from "../commons/dto/esito-save-indicatori";
import { ConfigService } from "../../core/services/config.service";


@Injectable({
  providedIn: 'root',
})
class IndicatoriService extends __BaseService {
  static readonly getIndicatoriPath = '/indicatori';
  static readonly saveIndicatoriGestionePath = '/indicatori/indicatoriGestione';
  static readonly getCodiceProgettoPath = '/indicatori/progetto';
  static readonly controllaDatiPerSalvataggioPath = '/indicatori/validazioneDati';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds list of indicatori monitoraggio avvio by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getIndicatoriAvvioResponse(idProgetto?: number): __Observable<__StrictHttpResponse<ResponseGetIndicatori>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/indicatori/indicatoriAvvio`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ResponseGetIndicatori>;
      })
    );
  }
  /**
   * Finds list of indicatori monitoraggio avvio by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getIndicatoriAvvio(idProgetto?: number): __Observable<ResponseGetIndicatori> {
    return this.getIndicatoriAvvioResponse(idProgetto).pipe(
      __map(_r => _r.body as ResponseGetIndicatori)
    );
  }

  /**
   * Finds list of indicatori monitoraggio by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getIndicatoriResponse(idProgetto?: number, isBandoSif?: boolean): __Observable<__StrictHttpResponse<ResponseGetIndicatori>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString()).set("isBandoSif", isBandoSif.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/indicatori/indicatoriGestione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ResponseGetIndicatori>;
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
   * Saves validated indicatori avvio data to database
   * @param body undefined
   * @return successful operation
   */
  saveIndicatoriAvvioResponse(body?: SalvaIndicatoriRequest): __Observable<__StrictHttpResponse<EsitoSaveIndicatori>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/indicatori/indicatoriAvvio`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSaveIndicatori>;
      })
    );
  }
  /**
   * Saves validated indicatori avvio data to database
   * @param body undefined
   * @return successful operation
   */
  saveIndicatoriAvvio(body?: SalvaIndicatoriRequest): __Observable<EsitoSaveIndicatori> {
    return this.saveIndicatoriAvvioResponse(body).pipe(
      __map(_r => _r.body as EsitoSaveIndicatori)
    );
  }

  /**
   * Saves validated indicatori data to database
   * @param body undefined
   * @return successful operation
   */
  saveIndicatoriGestioneResponse(body?: SalvaIndicatoriRequest): __Observable<__StrictHttpResponse<EsitoSaveIndicatori>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/indicatori/indicatoriGestione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSaveIndicatori>;
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
  getCodiceProgettoResponse(idProgetto?: number): __Observable<__StrictHttpResponse<string>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/indicatori/progetto`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'text'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<string>;
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

  /*validazione spostata su angular
    controllaDatiPerSalvataggioAvvioResponse(body?: ValidazioneDatiIndicatoriRequest): __Observable<__StrictHttpResponse<ExecResults>> {
      let __params = this.newParams();
      let __headers = new HttpHeaders();
      let __body: any = null;
      __body = body;
      let req = new HttpRequest<any>(
        'POST',
        this.rootUrl + `/restfacade/indicatori/indicatoriAvvio/validazioneDati`,
        __body,
        {
          headers: __headers,
          params: __params,
          responseType: 'json'
        });
  
      return this.http.request<any>(req).pipe(
        __filter(_r => _r instanceof HttpResponse),
        __map((_r) => {
          return _r as __StrictHttpResponse<ExecResults>;
        })
      );
    }
  
    controllaDatiPerSalvataggioAvvio(body?: ValidazioneDatiIndicatoriRequest): __Observable<ExecResults> {
      return this.controllaDatiPerSalvataggioAvvioResponse(body).pipe(
        __map(_r => _r.body as ExecResults)
      );
    }

  controllaDatiPerSalvataggioGestioneResponse(body?: ValidazioneDatiIndicatoriRequest): __Observable<__StrictHttpResponse<ExecResults>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/indicatori/indicatoriGestione/validazioneDati`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ExecResults>;
      })
    );
  }

  controllaDatiPerSalvataggioGestione(body?: ValidazioneDatiIndicatoriRequest): __Observable<ExecResults> {
    return this.controllaDatiPerSalvataggioGestioneResponse(body).pipe(
      __map(_r => _r.body as ExecResults)
    );
  }*/
}

module IndicatoriService {
}

export { IndicatoriService }
