/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseService as __BaseService } from '../../shared/base-service';
// @ts-ignore
import { ApiConfiguration as __Configuration } from '../../shared/api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../../shared/strict-http-response'
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';
import { ConfigService } from "../../core/services/config.service";
import { CreaComunicazioneRinunciaRequest } from "../commons/dto/crea-comunicazione-rinuncia-request";
import { DichiarazioneRinuncia } from "../commons/dto/dichiarazione-rinuncia";
import { DelegatoDTO } from "../commons/dto/delegato-dto";
import { EsitoScaricaDichiarazioneDiRinuncia } from "../commons/dto/esito-scarica-dichiarazione-di-rinuncia";
import { CodiceDescrizione } from "../commons/dto/codice-descrizione";
import { ResponseCreaCommunicazioneRinuncia } from "../commons/dto/response-crea-communicazione-rinuncia";

@Injectable({
  providedIn: 'root',
})
class RinunciaService extends __BaseService {
  static readonly creaComunicazioneRinunciaPath = '/rinuncia/comunicazioneRinuncia';
  static readonly getDelegatiPath = '/rinuncia/delegati/{idProgetto}';
  static readonly scaricaDichiarazioneDiRinunciaPath = '/rinuncia/dichiarazioneDiRinuncia/{idDocumentoIndex}';
  static readonly getRappresentantiLegaliPath = '/rinuncia/rappresentantiLegali/{idProgetto}';

  configureService: ConfigService

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
    this.configureService = configService
  }

  /**
   * Finds ImportoTotaleErogato by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getImportoDaRestituireResponse(idProgetto?: number): __Observable<__StrictHttpResponse<number>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/rinuncia/importoDaRestituire`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'text'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return (_r as HttpResponse<any>).clone({ body: parseFloat((_r as HttpResponse<any>).body as string) }) as __StrictHttpResponse<number>
      })
    );
  }
  /**
   * Finds ImportoTotaleErogato by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getImportoDaRestituire(idProgetto?: number): __Observable<number> {
    return this.getImportoDaRestituireResponse(idProgetto).pipe(
      __map(_r => _r.body as number)
    );
  }

  /**
   * Finds delegati by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getDelegatiResponse(idProgetto: number): __Observable<__StrictHttpResponse<Array<DelegatoDTO>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/rinuncia/delegati/${encodeURIComponent(String(idProgetto))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<DelegatoDTO>>;
      })
    );
  }

  /**
   * Finds delegati by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getDelegati(idProgetto: number): __Observable<Array<DelegatoDTO>> {
    return this.getDelegatiResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<DelegatoDTO>)
    );
  }

  /**
   * Finds report content of Dichiarazione Di Rinuncia by idDocumentoIndex
   * @param idDocumentoIndex undefined
   * @return successful operation
   */
  scaricaDichiarazioneDiRinunciaResponse(idDocumentoIndex: number): __Observable<__StrictHttpResponse<EsitoScaricaDichiarazioneDiRinuncia>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/rinuncia/dichiarazioneDiRinuncia/${encodeURIComponent(String(idDocumentoIndex))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoScaricaDichiarazioneDiRinuncia>;
      })
    );
  }

  /**
   * Finds report content of Dichiarazione Di Rinuncia by idDocumentoIndex
   * @param idDocumentoIndex undefined
   * @return successful operation
   */
  scaricaDichiarazioneDiRinuncia(idDocumentoIndex: number): __Observable<EsitoScaricaDichiarazioneDiRinuncia> {
    return this.scaricaDichiarazioneDiRinunciaResponse(idDocumentoIndex).pipe(
      __map(_r => _r.body as EsitoScaricaDichiarazioneDiRinuncia)
    );
  }

  /**
   * Finds rappresentanti legali by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getRappresentantiLegaliResponse(idProgetto: number): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/rinuncia/rappresentantiLegali/${encodeURIComponent(String(idProgetto))}`,
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
   * Finds rappresentanti legali by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getRappresentantiLegali(idProgetto: number): __Observable<Array<CodiceDescrizione>> {
    return this.getRappresentantiLegaliResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }


  getIsBeneficiarioPrivatoCittadino(idProgetto: number) {
    let url = this.rootUrl + '/restfacade/rinuncia/getIsBeneficiarioPrivatoCittadino';
    let params = new HttpParams().set('idProgetto', idProgetto.toString());
    return this.http.get<boolean>(url, { params: params });
  }

  /*
  getIsRegolaApplicabileForBandoLinea(idProgetto: number, codiceRegola: string) {
    let url = this.rootUrl + '/restfacade/rinuncia/getIsRegolaApplicabileForProgetto';
    let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("codiceRegola", codiceRegola);
    return this.http.get<boolean>(url, { params: params });
  }*/

  getIsRegolaApplicabileForProgetto(idProgetto: number, codiceRegola: string) {
    let url = this.rootUrl + '/restfacade/rinuncia/getIsRegolaApplicabileForProgetto';
    let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("codiceRegola", codiceRegola);
    return this.http.get<boolean>(url, { params: params });
  }




}

module RinunciaService {
}

export { RinunciaService }
