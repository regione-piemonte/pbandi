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

import { CodiceDescrizione } from '../commons/models/codice-descrizione';
import { EsitoCampionamentoDTO } from '../commons/dto/esito-campionamento-dto';
import { RequestGetProgettiCampione } from '../commons/models/request-get-progetti-campione';
import { ElenchiProgettiCampionamento } from '../commons/models/elenchi-progetti-campionamento';
import { EsitoGenerazioneReportDTO } from '../commons/dto/esito-generazione-report-dto';
import { FiltroRilevazione } from '../commons/models/filtro-rilevazione';
import { ConfigService } from '../../core/services/config.service';

@Injectable({
  providedIn: 'root',
})
class MonitoraggioControlliService extends __BaseService {
  static readonly getAssePath = '/monitoraggioControlli/asse';
  static readonly getAutoritaControlloPath = '/monitoraggioControlli/autoritaControllo';
  static readonly getBandiPath = '/monitoraggioControlli/bandi';
  static readonly registraCampionamentoProgettiPath = '/monitoraggioControlli/campionamento';
  static readonly getNormativePath = '/monitoraggioControlli/normative';
  static readonly getAnnoContabiliPath = '/monitoraggioControlli/periodi';
  static readonly getProgettiCampionePath = '/monitoraggioControlli/progettiCampione';
  static readonly generaReportCampionamentoPath = '/monitoraggioControlli/reportCampionamento';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds asse by idProgetto and idLineaDiIntervento
   * @param params The `MonitoraggioControlliService.GetAsseParams` containing the following parameters:
   *
   * - `isConsultazione`:
   *
   * - `idLineaDiIntervento`:
   *
   * @return successful operation
   */
  getAsseResponse(params: MonitoraggioControlliService.GetAsseParams): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.isConsultazione != null) __params = __params.set('isConsultazione', params.isConsultazione.toString());
    if (params.idLineaDiIntervento != null) __params = __params.set('idLineaDiIntervento', params.idLineaDiIntervento.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/monitoraggioControlli/asse`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<CodiceDescrizione>>;
      })
    );
  }
  /**
   * Finds asse by idProgetto and idLineaDiIntervento
   * @param params The `MonitoraggioControlliService.GetAsseParams` containing the following parameters:
   *
   * - `isConsultazione`:
   *
   * - `idLineaDiIntervento`:
   *
   * @return successful operation
   */
  getAsse(params: MonitoraggioControlliService.GetAsseParams): __Observable<Array<CodiceDescrizione>> {
    return this.getAsseResponse(params).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds Autorita controllo by idProgetto
   * @param isConsultazione undefined
   * @return successful operation
   */
  getAutoritaControlloResponse(isConsultazione?: boolean): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (isConsultazione != null) __params = __params.set('isConsultazione', isConsultazione.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/monitoraggioControlli/autoritaControllo`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<CodiceDescrizione>>;
      })
    );
  }
  /**
   * Finds Autorita controllo by idProgetto
   * @param isConsultazione undefined
   * @return successful operation
   */
  getAutoritaControllo(isConsultazione?: boolean): __Observable<Array<CodiceDescrizione>> {
    return this.getAutoritaControlloResponse(isConsultazione).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds bandi by idLineaDiIntervento and idAsse
   * @param params The `MonitoraggioControlliService.GetBandiParams` containing the following parameters:
   *
   * - `isConsultazione`:
   *
   * - `idLineaDiIntervento`:
   *
   * - `idAsse`:
   *
   * @return successful operation
   */
  getBandiResponse(params: MonitoraggioControlliService.GetBandiParams): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.isConsultazione != null) __params = __params.set('isConsultazione', params.isConsultazione.toString());
    if (params.idLineaDiIntervento != null) __params = __params.set('idLineaDiIntervento', params.idLineaDiIntervento.toString());
    if (params.idAsse != null) __params = __params.set('idAsse', params.idAsse.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/monitoraggioControlli/bandi`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<CodiceDescrizione>>;
      })
    );
  }
  /**
   * Finds bandi by idLineaDiIntervento and idAsse
   * @param params The `MonitoraggioControlliService.GetBandiParams` containing the following parameters:
   *
   * - `isConsultazione`:
   *
   * - `idLineaDiIntervento`:
   *
   * - `idAsse`:
   *
   * @return successful operation
   */
  getBandi(params: MonitoraggioControlliService.GetBandiParams): __Observable<Array<CodiceDescrizione>> {
    return this.getBandiResponse(params).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Salva campionamento progetti
   * @param body undefined
   * @return successful operation
   */
  registraCampionamentoProgettiResponse(body?: RequestGetProgettiCampione): __Observable<__StrictHttpResponse<EsitoCampionamentoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/monitoraggioControlli/campionamento`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoCampionamentoDTO>;
      })
    );
  }
  /**
   * Salva campionamento progetti
   * @param body undefined
   * @return successful operation
   */
  registraCampionamentoProgetti(body?: RequestGetProgettiCampione): __Observable<EsitoCampionamentoDTO> {
    return this.registraCampionamentoProgettiResponse(body).pipe(
      __map(_r => _r.body as EsitoCampionamentoDTO)
    );
  }

  /**
   * Finds normative
   * @param isConsultazione undefined
   * @return successful operation
   */
  getNormativeResponse(isConsultazione?: boolean): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (isConsultazione != null) __params = __params.set('isConsultazione', isConsultazione.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/monitoraggioControlli/normative`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<CodiceDescrizione>>;
      })
    );
  }
  /**
   * Finds normative
   * @param isConsultazione undefined
   * @return successful operation
   */
  getNormative(isConsultazione?: boolean): __Observable<Array<CodiceDescrizione>> {
    return this.getNormativeResponse(isConsultazione).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds periodi
   * @param isConsultazione undefined
   * @return successful operation
   */
  getAnnoContabiliResponse(isConsultazione?: boolean): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (isConsultazione != null) __params = __params.set('isConsultazione', isConsultazione.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/monitoraggioControlli/periodi`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<CodiceDescrizione>>;
      })
    );
  }
  /**
   * Finds periodi
   * @param isConsultazione undefined
   * @return successful operation
   */
  getAnnoContabili(isConsultazione?: boolean): __Observable<Array<CodiceDescrizione>> {
    return this.getAnnoContabiliResponse(isConsultazione).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds elenchi progetti campionamento
   * @param body undefined
   * @return successful operation
   */
  getProgettiCampioneResponse(body?: RequestGetProgettiCampione): __Observable<__StrictHttpResponse<ElenchiProgettiCampionamento>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/monitoraggioControlli/progettiCampione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ElenchiProgettiCampionamento>;
      })
    );
  }
  /**
   * Finds elenchi progetti campionamento
   * @param body undefined
   * @return successful operation
   */
  getProgettiCampione(body?: RequestGetProgettiCampione): __Observable<ElenchiProgettiCampionamento> {
    return this.getProgettiCampioneResponse(body).pipe(
      __map(_r => _r.body as ElenchiProgettiCampionamento)
    );
  }
}

module MonitoraggioControlliService {

  /**
   * Parameters for getAsse
   */
  export interface GetAsseParams {
    isConsultazione?: boolean;
    idLineaDiIntervento?: number;
  }

  /**
   * Parameters for getBandi
   */
  export interface GetBandiParams {
    isConsultazione?: boolean;
    idLineaDiIntervento?: number;
    idAsse?: number;
  }
}

export { MonitoraggioControlliService }
