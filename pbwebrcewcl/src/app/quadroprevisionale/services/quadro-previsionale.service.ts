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
import {ConfigService} from "../../core/services/config.service";
import {EsitoFindQuadroPrevisionaleDTO} from "../common/dto/esito-find-quadro-previsionale-dto";
import {SalvaQuadroPrevisionaleRequest} from "../common/request/salva-quadro-previsionale-request";
import {EsitoSalvaQuadroPrevisionaleDTO} from "../common/dto/esito-salva-quadro-previsionale-dto";
import {ValidazioneDatiQuadroProvisionaleRequest} from "../common/request/validazione-dati-quadro-provisionale-request";
import {ExecResults} from "../common/dto/exec-results";
import {ResponseGetQuadroPrevisionale} from "../common/dto/response-get-quadro-previsionale";



@Injectable({
  providedIn: 'root',
})
class QuadroPrevisionaleService extends __BaseService {
  static readonly getQuadroPrevisionalePath = '/quadroPrevisionale';
  static readonly salvaQuadroPrevisionalePath = '/quadroPrevisionale';
  static readonly controllaDatiPerSalvataggioPath = '/quadroPrevisionale/validazioneDati';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds Quadro Previsionale by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getQuadroPrevisionaleResponse(idProgetto?: number): __Observable<__StrictHttpResponse<ResponseGetQuadroPrevisionale>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/quadroPrevisionale`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ResponseGetQuadroPrevisionale>;
      })
    );
  }
  /**
   * Finds Quadro Previsionale by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getQuadroPrevisionale(idProgetto?: number): __Observable<ResponseGetQuadroPrevisionale> {
    return this.getQuadroPrevisionaleResponse(idProgetto).pipe(
      __map(_r => _r.body as ResponseGetQuadroPrevisionale)
    );
  }

  /**
   * Saves validated Quadro Previsionale data to database
   * @param body undefined
   * @return successful operation
   */
  salvaQuadroPrevisionaleResponse(body?: ResponseGetQuadroPrevisionale): __Observable<__StrictHttpResponse<EsitoSalvaQuadroPrevisionaleDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/quadroPrevisionale`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaQuadroPrevisionaleDTO>;
      })
    );
  }
  /**
   * Saves validated Quadro Previsionale data to database
   * @param body undefined
   * @return successful operation
   */
  salvaQuadroPrevisionale(body?: ResponseGetQuadroPrevisionale): __Observable<EsitoSalvaQuadroPrevisionaleDTO> {
    return this.salvaQuadroPrevisionaleResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaQuadroPrevisionaleDTO)
    );
  }

  /**
   * Controlla dati quadro previsionale per salvattaggio
   * @param body undefined
   * @return successful operation
   */
  controllaDatiPerSalvataggioResponse(body?: ValidazioneDatiQuadroProvisionaleRequest): __Observable<__StrictHttpResponse<ExecResults>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/quadroPrevisionale/validazioneDati`,
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
  /**
   * Controlla dati quadro previsionale per salvattaggio
   * @param body undefined
   * @return successful operation
   */
  controllaDatiPerSalvataggio(body?: ValidazioneDatiQuadroProvisionaleRequest): __Observable<ExecResults> {
    return this.controllaDatiPerSalvataggioResponse(body).pipe(
      __map(_r => _r.body as ExecResults)
    );
  }

  /**
   * Finds Codice progetto by idProgetto
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
      this.rootUrl + `/restfacade/quadroPrevisionale/progetto`,
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
   * Finds Codice progetto by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getCodiceProgetto(idProgetto?: number): __Observable<string> {
    return this.getCodiceProgettoResponse(idProgetto).pipe(
      __map(_r => _r.body as string)
    );
  }
}

module QuadroPrevisionaleService {
}

export { QuadroPrevisionaleService }
