/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../../shared/base-service';
import { ApiConfiguration as __Configuration } from '../../shared/api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../../shared/strict-http-response'
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';
import {ConfigService} from "../../core/services/config.service";
import {EsitoOperazioni} from "../../shared/api/models/esito-operazioni";
import {EsitoOperazioneChiudiProgetto} from "../commons/models/esito-operazione-chiudi-progetto";
import {RequestChiudiProgetto} from "../commons/requests/request-chiudi-progetto";


@Injectable({
  providedIn: 'root',
})
class ChiusuraProgettoService extends __BaseService {
  static readonly verificaErogazioneSaldoPath = '/chiusuraProgetto/erogazioneSaldo';
  static readonly chiudiProgettoPath = '/chiusuraProgetto/progetto';
  static readonly isRinunciaPresentePath = '/chiusuraProgetto/rinuncia';
  static readonly chiudiProgettoDiUfficioPath = '/chiusuraProgetto/ufficio';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Verifica  se esiste il progetto associato/collegato/a contributo.
   * @param idProgetto undefined
   * @return successful operation
   */
  verificaErogazioneSaldoResponse(idProgetto?: number): __Observable<__StrictHttpResponse<EsitoOperazioni>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/chiusuraProgetto/erogazioneSaldo`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoOperazioni>;
      })
    );
  }
  /**
   * Verifica  se esiste il progetto associato/collegato/a contributo.
   * @param idProgetto undefined
   * @return successful operation
   */
  verificaErogazioneSaldo(idProgetto?: number): __Observable<EsitoOperazioni> {
    return this.verificaErogazioneSaldoResponse(idProgetto).pipe(
      __map(_r => _r.body as EsitoOperazioni)
    );
  }

  /**
   * Chiude la gestione operativa del progetto.
   * @param body undefined
   * @return successful operation
   */
  chiudiProgettoResponse(body?: RequestChiudiProgetto): __Observable<__StrictHttpResponse<EsitoOperazioneChiudiProgetto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/chiusuraProgetto/progetto`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoOperazioneChiudiProgetto>;
      })
    );
  }
  /**
   * Chiude la gestione operativa del progetto.
   * @param body undefined
   * @return successful operation
   */
  chiudiProgetto(body?: RequestChiudiProgetto): __Observable<EsitoOperazioneChiudiProgetto> {
    return this.chiudiProgettoResponse(body).pipe(
      __map(_r => _r.body as EsitoOperazioneChiudiProgetto)
    );
  }

  /**
   * Verifica se presente una rinuncia per il progetto.
   * @param idProgetto undefined
   * @return successful operation
   */
  isRinunciaPresenteResponse(idProgetto?: number): __Observable<__StrictHttpResponse<boolean>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/chiusuraProgetto/rinuncia`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'text'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return (_r as HttpResponse<any>).clone({ body: (_r as HttpResponse<any>).body === 'true' }) as __StrictHttpResponse<boolean>
      })
    );
  }
  /**
   * Verifica se presente una rinuncia per il progetto.
   * @param idProgetto undefined
   * @return successful operation
   */
  isRinunciaPresente(idProgetto?: number): __Observable<boolean> {
    return this.isRinunciaPresenteResponse(idProgetto).pipe(
      __map(_r => _r.body as boolean)
    );
  }

  /**
   * Chiude progetto di ufficio.
   * @param body undefined
   * @return successful operation
   */
  chiudiProgettoDiUfficioResponse(body?: RequestChiudiProgetto): __Observable<__StrictHttpResponse<EsitoOperazioneChiudiProgetto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/chiusuraProgetto/ufficio`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoOperazioneChiudiProgetto>;
      })
    );
  }
  /**
   * Chiude progetto di ufficio.
   * @param body undefined
   * @return successful operation
   */
  chiudiProgettoDiUfficio(body?: RequestChiudiProgetto): __Observable<EsitoOperazioneChiudiProgetto> {
    return this.chiudiProgettoDiUfficioResponse(body).pipe(
      __map(_r => _r.body as EsitoOperazioneChiudiProgetto)
    );
  }
}

module ChiusuraProgettoService {
}

export { ChiusuraProgettoService }
