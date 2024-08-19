/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../base-service';
import { ApiConfiguration as __Configuration } from '../api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';
import {AttivitaPregresseDTO} from "../commons/dto/attivita-pregresse-dto";
import {DatiGenerali} from "../commons/dto/dati-generali";
import {ConfigService} from "../../core/services/config.service";

@Injectable({
  providedIn: 'root',
})
class InizializzazioneService extends __BaseService {
  static readonly getAttivitaPregressePath = '/inizializzazione/attivitaPregresse';
  static readonly getDatiGeneraliPath = '/inizializzazione/datiGenerali';
  static readonly inizializzaHomePath = '/inizializzazione/home';
  static readonly getCodiceProgettoPath = '/inizializzazione/progetto';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds attivita pregresse by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getAttivitaPregresseResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<AttivitaPregresseDTO>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/inizializzazione/attivitaPregresse`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<AttivitaPregresseDTO>>;
      })
    );
  }
  /**
   * Finds attivita pregresse by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getAttivitaPregresse(idProgetto?: number): __Observable<Array<AttivitaPregresseDTO>> {
    return this.getAttivitaPregresseResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<AttivitaPregresseDTO>)
    );
  }

  /**
   * Finds dati generali by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getDatiGeneraliResponse(idProgetto?: number): __Observable<__StrictHttpResponse<DatiGenerali>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/inizializzazione/datiGenerali`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<DatiGenerali>;
      })
    );
  }
  /**
   * Finds dati generali by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getDatiGenerali(idProgetto?: number): __Observable<DatiGenerali> {
    return this.getDatiGeneraliResponse(idProgetto).pipe(
      __map(_r => _r.body as DatiGenerali)
    );
  }

  /**
   * @param params The `InizializzazioneService.InizializzaHomeParams` containing the following parameters:
   *
   * - `role`:
   *
   * - `idU`:
   *
   * - `idSgBen`:
   *
   * - `idSg`:
   */
  inizializzaHomeResponse(params: InizializzazioneService.InizializzaHomeParams): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.role != null) __params = __params.set('role', params.role.toString());
    if (params.idU != null) __params = __params.set('idU', params.idU.toString());
    if (params.idSgBen != null) __params = __params.set('idSgBen', params.idSgBen.toString());
    if (params.idSg != null) __params = __params.set('idSg', params.idSg.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/inizializzazione/home`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<null>;
      })
    );
  }
  /**
   * @param params The `InizializzazioneService.InizializzaHomeParams` containing the following parameters:
   *
   * - `role`:
   *
   * - `idU`:
   *
   * - `idSgBen`:
   *
   * - `idSg`:
   */
  inizializzaHome(params: InizializzazioneService.InizializzaHomeParams): __Observable<null> {
    return this.inizializzaHomeResponse(params).pipe(
      __map(_r => _r.body as null)
    );
  }

  /**
   * Finds CODICE_VISUALIZZATO by idProgetto
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
      this.rootUrl + `/restfacade/inizializzazione/progetto`,
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
   * Finds CODICE_VISUALIZZATO by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getCodiceProgetto(idProgetto?: number): __Observable<string> {
    return this.getCodiceProgettoResponse(idProgetto).pipe(
      __map(_r => _r.body as string)
    );
  }
}

module InizializzazioneService {

  /**
   * Parameters for inizializzaHome
   */
  export interface InizializzaHomeParams {
    role?: string;
    idU?: number;
    idSgBen?: number;
    idSg?: number;
  }
}

export { InizializzazioneService }
