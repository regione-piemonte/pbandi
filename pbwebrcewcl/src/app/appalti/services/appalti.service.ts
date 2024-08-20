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
import {CodiceDescrizione} from "../commons/dto/codice-descrizione";
import {Appalto} from "../commons/dto/appalto";
import {CreaAppaltoRequest} from "../commons/dto/crea-appalto-request";
import {ProceduraAggiudicazione} from "../commons/dto/procedura-aggiudicazione";
import {GetProcedureAggiudicazioneRequest} from "../commons/dto/get-procedure-aggiudicazione-request";
import {ConfigService} from "../../core/services/config.service";
import {ModificaProcedureAggiudicazioneRequest} from "../commons/dto/modifica-procedure-aggiudicazione-request";
import {EsitoProceduraAggiudicazioneDTO} from "../commons/dto/esito-procedura-aggiudicazione-dto";
import {StepAggiudicazione} from "../commons/dto/step-aggiudicazione";
import {EsitoGestioneAppalti} from "../commons/dto/esito-gestione-appalti";
import {CreaProcAggRequest} from "../commons/dto/crea-proc-agg-request";



@Injectable({
  providedIn: 'root',
})
class AppaltiService extends __BaseService {
  static readonly getAppaltiPath = '/applati';
  static readonly getCodiceProgettoPath = '/applati/progetto';
  static readonly getTipologieAppaltiPath = '/applati/tipologieAppalti';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds appalti by idProgetto e altri filtri di ricerca
   * @param params The `AppaltiService.GetAppaltiParams` containing the following parameters:
   *
   * - `idProgetto`:
   *
   * - `body`:
   *
   * @return successful operation
   */
  getAppaltiResponse(params: AppaltiService.GetAppaltiParams): __Observable<__StrictHttpResponse<Array<Appalto>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    __body = params.body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/applati`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<Appalto>>;
      })
    );
  }
  /**
   * Finds appalti by idProgetto e altri filtri di ricerca
   * @param params The `AppaltiService.GetAppaltiParams` containing the following parameters:
   *
   * - `idProgetto`:
   *
   * - `body`:
   *
   * @return successful operation
   */
  getAppalti(params: AppaltiService.GetAppaltiParams): __Observable<Array<Appalto>> {
    return this.getAppaltiResponse(params).pipe(
      __map(_r => _r.body as Array<Appalto>)
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
      this.rootUrl + `/restfacade/applati/progetto`,
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

  /**
   * Finds Tipologie Appalti
   * @return successful operation
   */
  getTipologieAppaltiResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/applati/tipologieAppalti`,
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
   * Finds Tipologie Appalti
   * @return successful operation
   */
  getTipologieAppalti(): __Observable<Array<CodiceDescrizione>> {
    return this.getTipologieAppaltiResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds Tipologie Procedure Aggiudicazione by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getTipologieProcedureAggiudicazioneResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/applati/tipologieProcedureAggiudicazione`,
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
   * Finds Tipologie Procedure Aggiudicazione by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getTipologieProcedureAggiudicazione(idProgetto?: number): __Observable<Array<CodiceDescrizione>> {
    return this.getTipologieProcedureAggiudicazioneResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds All Procedure Aggiudicazione by idProgetto
   * @param body undefined
   * @return successful operation
   */
  getAllProcedureAggiudicazioneResponse(body?: GetProcedureAggiudicazioneRequest): __Observable<__StrictHttpResponse<Array<ProceduraAggiudicazione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/applati/procedureAggiudicazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<ProceduraAggiudicazione>>;
      })
    );
  }
  /**
   * Finds All Procedure Aggiudicazione by idProgetto
   * @param body undefined
   * @return successful operation
   */
  getAllProcedureAggiudicazione(body?: GetProcedureAggiudicazioneRequest): __Observable<Array<ProceduraAggiudicazione>> {
    return this.getAllProcedureAggiudicazioneResponse(body).pipe(
      __map(_r => _r.body as Array<ProceduraAggiudicazione>)
    );
  }

  /**
   * Finds a Procedura Aggiudicazione by idProcedura
   * @param idProcedura undefined
   * @return successful operation
   */
  getDettaglioProceduraAggiudicazioneResponse(idProcedura: number): __Observable<__StrictHttpResponse<ProceduraAggiudicazione>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/applati/procedureAggiudicazione/${encodeURIComponent(String(idProcedura))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ProceduraAggiudicazione>;
      })
    );
  }
  /**
   * Finds a Procedura Aggiudicazione by idProcedura
   * @param idProcedura undefined
   * @return successful operation
   */
  getDettaglioProceduraAggiudicazione(idProcedura: number): __Observable<ProceduraAggiudicazione> {
    return this.getDettaglioProceduraAggiudicazioneResponse(idProcedura).pipe(
      __map(_r => _r.body as ProceduraAggiudicazione)
    );
  }

  /**
   * Updates a Procedura Aggiudicazione by idProgetto
   * @param body undefined
   * @return successful operation
   */
  modificaProceduraAggiudicazioneResponse(body?: ModificaProcedureAggiudicazioneRequest): __Observable<__StrictHttpResponse<EsitoProceduraAggiudicazioneDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/applati/proceduraAggiudicazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoProceduraAggiudicazioneDTO>;
      })
    );
  }
  /**
   * Updates a Procedura Aggiudicazione by idProgetto
   * @param body undefined
   * @return successful operation
   */
  modificaProceduraAggiudicazione(body?: ModificaProcedureAggiudicazioneRequest): __Observable<EsitoProceduraAggiudicazioneDTO> {
    return this.modificaProceduraAggiudicazioneResponse(body).pipe(
      __map(_r => _r.body as EsitoProceduraAggiudicazioneDTO)
    );
  }

  /**
   * Crea appalto
   * @param body undefined
   * @return successful operation
   */
  creaAppaltoResponse(body?: CreaAppaltoRequest): __Observable<__StrictHttpResponse<EsitoGestioneAppalti>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/applati/appalto`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoGestioneAppalti>;
      })
    );
  }
  /**
   * Crea appalto
   * @param body undefined
   * @return successful operation
   */
  creaAppalto(body?: CreaAppaltoRequest): __Observable<EsitoGestioneAppalti> {
    return this.creaAppaltoResponse(body).pipe(
      __map(_r => _r.body as EsitoGestioneAppalti)
    );
  }

  /**
   * Finds List of Step Aggiudicazione by IdTipologiaAggiudicaz
   * @param idTipologiaAggiudicaz undefined
   * @return successful operation
   */
  getStepsAggiudicazioneResponse(idTipologiaAggiudicaz: number): __Observable<__StrictHttpResponse<Array<StepAggiudicazione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/applati/stepsAggiudicazione/${encodeURIComponent(String(idTipologiaAggiudicaz))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<StepAggiudicazione>>;
      })
    );
  }
  /**
   * Finds List of Step Aggiudicazione by IdTipologiaAggiudicaz
   * @param idTipologiaAggiudicaz undefined
   * @return successful operation
   */
  getStepsAggiudicazione(idTipologiaAggiudicaz: number): __Observable<Array<StepAggiudicazione>> {
    return this.getStepsAggiudicazioneResponse(idTipologiaAggiudicaz).pipe(
      __map(_r => _r.body as Array<StepAggiudicazione>)
    );
  }

  /**
   * Crea Procedura Aggiudicazione
   * @param body undefined
   * @return successful operation
   */
  creaProceduraAggiudicazioneResponse(body?: CreaProcAggRequest): __Observable<__StrictHttpResponse<EsitoProceduraAggiudicazioneDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/applati/proceduraAggiudicazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoProceduraAggiudicazioneDTO>;
      })
    );
  }
  /**
   * Crea Procedura Aggiudicazione
   * @param body undefined
   * @return successful operation
   */
  creaProceduraAggiudicazione(body?: CreaProcAggRequest): __Observable<EsitoProceduraAggiudicazioneDTO> {
    return this.creaProceduraAggiudicazioneResponse(body).pipe(
      __map(_r => _r.body as EsitoProceduraAggiudicazioneDTO)
    );
  }


}

module AppaltiService {

  /**
   * Parameters for getAppalti
   */
  export interface GetAppaltiParams {
    idProgetto?: number;
    body?: Appalto;
  }
}

export { AppaltiService }
