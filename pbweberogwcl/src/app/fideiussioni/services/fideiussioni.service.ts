/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../../shared/base-service';
// @ts-ignore
import { ApiConfiguration as __Configuration } from '../../shared/api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../../shared/strict-http-response'
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';
import {CercaFideiussioniRequest} from "../commons/dto/cerca-fideiussioni-request";
import {Fideiussione} from "../commons/dto/fideiussione";
import {CreaAggiornaFideiussioneRequest} from "../commons/dto/crea-aggiorna-fideiussione-request";
import {FideiussioneEsitoGenericoDTO} from "../commons/dto/fideiussione-esito-generico-dto";
import {ConfigService} from "../../core/services/config.service";
import {FideiussioneDTO} from "../commons/dto/fideiussione-dto";
import {EsitoOperazioni} from "../commons/dto/esito-operazioni";
import {CodiceDescrizione} from "../commons/dto/codice-descrizione";



@Injectable({
  providedIn: 'root',
})
class FideiussioniService extends __BaseService {
  static readonly getFideiussioniPath = '/fideiussioni';
  static readonly creaAggiornaFideiussionePath = '/fideiussioni';
  static readonly dettaglioFideiussionePath = '/fideiussioni/{idFideiussione}';
  static readonly eliminaFideiussionePath = '/fideiussioni/{idFideiussione}';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Verify first that the Fideiussione is manageable
   * @param idProgetto undefined
   * @return successful operation
   */
  isFideiussioneGestibileResponse(idProgetto?: number): __Observable<__StrictHttpResponse<EsitoOperazioni>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/fideiussioni/home`,
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
   * Verify first that the Fideiussione is manageable
   * @param idProgetto undefined
   * @return successful operation
   */
  isFideiussioneGestibile(idProgetto?: number): __Observable<EsitoOperazioni> {
    return this.isFideiussioneGestibileResponse(idProgetto).pipe(
      __map(_r => _r.body as EsitoOperazioni)
    );
  }

  /**
   * Finds Tipi Di Trattamento (load combo tipo trattamento)
   * @param idProgetto undefined
   * @return successful operation
   */
  getTipiDiTrattamentoResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/fideiussioni/tipiDiTrattamento`,
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
   * Finds Tipi Di Trattamento (load combo tipo trattamento)
   * @param idProgetto undefined
   * @return successful operation
   */
  getTipiDiTrattamento(idProgetto?: number): __Observable<Array<CodiceDescrizione>> {
    return this.getTipiDiTrattamentoResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds Fideiussioni by idProgetto and FiltroRicercaFideiussione
   * @param body undefined
   * @return successful operation
   */
  getFideiussioniResponse(body?: CercaFideiussioniRequest): __Observable<__StrictHttpResponse<Array<Fideiussione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/fideiussioni`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<Fideiussione>>;
      })
    );
  }
  /**
   * Finds Fideiussioni by idProgetto and FiltroRicercaFideiussione
   * @param body undefined
   * @return successful operation
   */
  getFideiussioni(body?: CercaFideiussioniRequest): __Observable<Array<Fideiussione>> {
    return this.getFideiussioniResponse(body).pipe(
      __map(_r => _r.body as Array<Fideiussione>)
    );
  }

  /**
   * Updates Fideiussione
   * @param body undefined
   * @return successful operation
   */
  creaAggiornaFideiussioneResponse(body?: CreaAggiornaFideiussioneRequest): __Observable<__StrictHttpResponse<FideiussioneEsitoGenericoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/fideiussioni`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<FideiussioneEsitoGenericoDTO>;
      })
    );
  }
  /**
   * Updates Fideiussione
   * @param body undefined
   * @return successful operation
   */
  creaAggiornaFideiussione(body?: CreaAggiornaFideiussioneRequest): __Observable<FideiussioneEsitoGenericoDTO> {
    return this.creaAggiornaFideiussioneResponse(body).pipe(
      __map(_r => _r.body as FideiussioneEsitoGenericoDTO)
    );
  }

  /**
   * Finds dettaglio fideiussione
   * @param idFideiussione undefined
   * @return successful operation
   */
  dettaglioFideiussioneResponse(idFideiussione: number): __Observable<__StrictHttpResponse<Fideiussione>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/fideiussioni/${encodeURIComponent(String(idFideiussione))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Fideiussione>;
      })
    );
  }
  /**
   * Finds dettaglio fideiussione
   * @param idFideiussione undefined
   * @return successful operation
   */
  dettaglioFideiussione(idFideiussione: number): __Observable<Fideiussione> {
    return this.dettaglioFideiussioneResponse(idFideiussione).pipe(
      __map(_r => _r.body as Fideiussione)
    );
  }

  /**
   * Deletes Fideiussione by idFideiussione
   * @param idFideiussione undefined
   * @return successful operation
   */
  eliminaFideiussioneResponse(idFideiussione: number): __Observable<__StrictHttpResponse<FideiussioneEsitoGenericoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/fideiussioni/${encodeURIComponent(String(idFideiussione))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<FideiussioneEsitoGenericoDTO>;
      })
    );
  }
  /**
   * Deletes Fideiussione by idFideiussione
   * @param idFideiussione undefined
   * @return successful operation
   */
  eliminaFideiussione(idFideiussione: number): __Observable<FideiussioneEsitoGenericoDTO> {
    return this.eliminaFideiussioneResponse(idFideiussione).pipe(
      __map(_r => _r.body as FideiussioneEsitoGenericoDTO)
    );
  }

  /**
   * Finds Tipo Di Trattamento by idTipoTrattamento(for dettaglio Fideiussione)
   * @param idTipoTrattamento undefined
   * @return successful operation
   */
  getTipoDiTrattamentoResponse(idTipoTrattamento: number): __Observable<__StrictHttpResponse<string>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/fideiussioni/tipiDiTrattamento/${encodeURIComponent(String(idTipoTrattamento))}`,
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
   * Finds Tipo Di Trattamento by idTipoTrattamento(for dettaglio Fideiussione)
   * @param idTipoTrattamento undefined
   * @return successful operation
   */
  getTipoDiTrattamento(idTipoTrattamento: number): __Observable<string> {
    return this.getTipoDiTrattamentoResponse(idTipoTrattamento).pipe(
      __map(_r => _r.body as string)
    );
  }

}

module FideiussioniService {
}

export { FideiussioniService }
