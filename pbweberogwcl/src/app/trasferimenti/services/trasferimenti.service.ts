/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../../shared/base-service';
import { ApiConfiguration as __Configuration } from '../../shared/api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../../shared/strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { SoggettoTrasferimento } from '../commons/dto/soggetto-trasferimento';
import { CausaleTrasferimento } from '../commons/dto/causale-trasferimento';
import { Trasferimento } from '../commons/dto/trasferimento';
import { TrasferimentiItem } from '../commons/dto/trasferimenti-item';
import { FiltroTrasferimento } from '../commons/dto/filtro-trasferimento';
import { EsitoSalvaTrasferimentoDTO } from '../commons/dto/esito-salva-trasferimento-dto';
import { ConfigService } from '../../core/services/config.service';

@Injectable({
  providedIn: 'root',
})
class TrasferimentiService extends __BaseService {
  static readonly getSoggettiTrasferimentoPath = '/trasferimenti/beneficiari';
  static readonly getCausaliTrasferimentoPath = '/trasferimenti/causali';
  static readonly getDettaglioTrasferimentoPath = '/trasferimenti/dettaglio/{idTrasferimento}';
  static readonly ricercaTrasferimentiPath = '/trasferimenti/ricerca';
  static readonly inserisciTrasferimentoPath = '/trasferimenti/trasferimento';
  static readonly modificaTrasferimentoPath = '/trasferimenti/trasferimento';
  static readonly cancellaTrasferimentoPath = '/trasferimenti/{idTrasferimento}';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds Soggetti trasferimento (Carica combo beneficiari)
   * @return successful operation
   */
  getSoggettiTrasferimentoResponse(): __Observable<__StrictHttpResponse<Array<SoggettoTrasferimento>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/trasferimenti/beneficiari`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<SoggettoTrasferimento>>;
      })
    );
  }
  /**
   * Finds Soggetti trasferimento (Carica combo beneficiari)
   * @return successful operation
   */
  getSoggettiTrasferimento(): __Observable<Array<SoggettoTrasferimento>> {
    return this.getSoggettiTrasferimentoResponse().pipe(
      __map(_r => _r.body as Array<SoggettoTrasferimento>)
    );
  }

  /**
   * Finds Causali trasferimento (Carica combo causali)
   * @return successful operation
   */
  getCausaliTrasferimentoResponse(): __Observable<__StrictHttpResponse<Array<CausaleTrasferimento>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/trasferimenti/causali`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<CausaleTrasferimento>>;
      })
    );
  }
  /**
   * Finds Causali trasferimento (Carica combo causali)
   * @return successful operation
   */
  getCausaliTrasferimento(): __Observable<Array<CausaleTrasferimento>> {
    return this.getCausaliTrasferimentoResponse().pipe(
      __map(_r => _r.body as Array<CausaleTrasferimento>)
    );
  }

  /**
   * Finds dettaglio trasferimento by idTrasferimento
   * @param idTrasferimento undefined
   * @return successful operation
   */
  getDettaglioTrasferimentoResponse(idTrasferimento: number): __Observable<__StrictHttpResponse<Trasferimento>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/trasferimenti/dettaglio/${encodeURIComponent(String(idTrasferimento))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Trasferimento>;
      })
    );
  }
  /**
   * Finds dettaglio trasferimento by idTrasferimento
   * @param idTrasferimento undefined
   * @return successful operation
   */
  getDettaglioTrasferimento(idTrasferimento: number): __Observable<Trasferimento> {
    return this.getDettaglioTrasferimentoResponse(idTrasferimento).pipe(
      __map(_r => _r.body as Trasferimento)
    );
  }

  /**
   * Finds elenco trasferimenti by FiltroTrasferimento
   * @param body undefined
   * @return successful operation
   */
  ricercaTrasferimentiResponse(body?: FiltroTrasferimento): __Observable<__StrictHttpResponse<Array<TrasferimentiItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/trasferimenti/ricerca`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<TrasferimentiItem>>;
      })
    );
  }
  /**
   * Finds elenco trasferimenti by FiltroTrasferimento
   * @param body undefined
   * @return successful operation
   */
  ricercaTrasferimenti(body?: FiltroTrasferimento): __Observable<Array<TrasferimentiItem>> {
    return this.ricercaTrasferimentiResponse(body).pipe(
      __map(_r => _r.body as Array<TrasferimentiItem>)
    );
  }

  /**
   * Creates trasferimento
   * @param body undefined
   * @return successful operation
   */
  inserisciTrasferimentoResponse(body?: Trasferimento): __Observable<__StrictHttpResponse<EsitoSalvaTrasferimentoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/trasferimenti/trasferimento`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaTrasferimentoDTO>;
      })
    );
  }
  /**
   * Creates trasferimento
   * @param body undefined
   * @return successful operation
   */
  inserisciTrasferimento(body?: Trasferimento): __Observable<EsitoSalvaTrasferimentoDTO> {
    return this.inserisciTrasferimentoResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaTrasferimentoDTO)
    );
  }

  /**
   * Updates trasferimento by idTrasferimento
   * @param body undefined
   * @return successful operation
   */
  modificaTrasferimentoResponse(body?: Trasferimento): __Observable<__StrictHttpResponse<EsitoSalvaTrasferimentoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/trasferimenti/trasferimento`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaTrasferimentoDTO>;
      })
    );
  }
  /**
   * Updates trasferimento by idTrasferimento
   * @param body undefined
   * @return successful operation
   */
  modificaTrasferimento(body?: Trasferimento): __Observable<EsitoSalvaTrasferimentoDTO> {
    return this.modificaTrasferimentoResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaTrasferimentoDTO)
    );
  }

  /**
   * Deletes trasferimento by idTrasferimento
   * @param idTrasferimento undefined
   * @return successful operation
   */
  cancellaTrasferimentoResponse(idTrasferimento: number): __Observable<__StrictHttpResponse<EsitoSalvaTrasferimentoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/trasferimenti/${encodeURIComponent(String(idTrasferimento))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaTrasferimentoDTO>;
      })
    );
  }
  /**
   * Deletes trasferimento by idTrasferimento
   * @param idTrasferimento undefined
   * @return successful operation
   */
  cancellaTrasferimento(idTrasferimento: number): __Observable<EsitoSalvaTrasferimentoDTO> {
    return this.cancellaTrasferimentoResponse(idTrasferimento).pipe(
      __map(_r => _r.body as EsitoSalvaTrasferimentoDTO)
    );
  }
}

module TrasferimentiService {
}

export { TrasferimentiService }
