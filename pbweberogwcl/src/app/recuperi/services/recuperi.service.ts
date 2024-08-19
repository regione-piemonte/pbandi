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
import { ConfigService } from "../../core/services/config.service";
import { RigaRecuperoItem } from "../commons/models/riga-recupero-item";
import { RequestSalvaRecuperi } from "../commons/requests/request-salva-recuperi";
import { RequestModificaRecupero } from "../commons/requests/request-modifica-recupero";
import { EsitoSalvaRecuperoDTO } from "../commons/dto/esito-salva-recupero-dto";
import { CodiceDescrizione } from "../../shared/commons/models/codice-descrizione";
import { DettaglioRecupero } from "../commons/models/dettaglio-recupero";
import { EsitoOperazioni } from "../../shared/api/models/esito-operazioni";
import { RigaModificaRecuperoItem } from "../commons/models/riga-modifica-recupero-item";

@Injectable({
  providedIn: 'root',
})
class RecuperiService extends __BaseService {
  static readonly getRecuperiPath = '/recuperi';
  static readonly modificaRecuperoPath = '/recuperi/all';
  static readonly getAnnoContabiliPath = '/recuperi/annoContabili';
  static readonly getDettaglioRecuperoPath = '/recuperi/dettaglio';
  static readonly getProcessoPath = '/recuperi/processo';
  static readonly checkPropostaCertificazioneProgettoPath = '/recuperi/proposta';
  static readonly isRecuperoAccessibilePath = '/recuperi/regolaBR28';
  static readonly getRecuperiPerModificaPath = '/recuperi/riepilogoDati';
  static readonly getTipologieRecuperiPath = '/recuperi/tipologieRecuperi';
  static readonly checkRecuperiPath = '/recuperi/validazione';
  static readonly cancellaRecuperoPath = '/recuperi/{idRecupero}';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds recuperi.
   * @param idProgetto undefined
   * @return successful operation
   */
  getRecuperiResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<RigaRecuperoItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/recuperi`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<RigaRecuperoItem>>;
      })
    );
  }
  /**
   * Finds recuperi.
   * @param idProgetto undefined
   * @return successful operation
   */
  getRecuperi(idProgetto?: number): __Observable<Array<RigaRecuperoItem>> {
    return this.getRecuperiResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<RigaRecuperoItem>)
    );
  }

  /**
   * Updates recupero.
   * @param body undefined
   * @return successful operation
   */
  modificaRecuperoResponse(body?: RequestModificaRecupero): __Observable<__StrictHttpResponse<EsitoSalvaRecuperoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/recuperi/all`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRecuperoDTO>;
      })
    );
  }
  /**
   * Updates recupero.
   * @param body undefined
   * @return successful operation
   */
  modificaRecupero(body?: RequestModificaRecupero): __Observable<EsitoSalvaRecuperoDTO> {
    return this.modificaRecuperoResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRecuperoDTO)
    );
  }

  /**
   * Finds Anno Contabili (for processo 2).
   * @return successful operation
   */
  getAnnoContabiliResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/recuperi/annoContabili`,
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
   * Finds Anno Contabili (for processo 2).
   * @return successful operation
   */
  getAnnoContabili(): __Observable<Array<CodiceDescrizione>> {
    return this.getAnnoContabiliResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds dettaglio recupero by idProgetto and idRecupero
   * @param params The `RecuperiService.GetDettaglioRecuperoParams` containing the following parameters:
   *
   * - `idRecupero`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  getDettaglioRecuperoResponse(params: RecuperiService.GetDettaglioRecuperoParams): __Observable<__StrictHttpResponse<DettaglioRecupero>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.idRecupero != null) __params = __params.set('idRecupero', params.idRecupero.toString());
    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/recuperi/dettaglio`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<DettaglioRecupero>;
      })
    );
  }
  /**
   * Finds dettaglio recupero by idProgetto and idRecupero
   * @param params The `RecuperiService.GetDettaglioRecuperoParams` containing the following parameters:
   *
   * - `idRecupero`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  getDettaglioRecupero(params: RecuperiService.GetDettaglioRecuperoParams): __Observable<DettaglioRecupero> {
    return this.getDettaglioRecuperoResponse(params).pipe(
      __map(_r => _r.body as DettaglioRecupero)
    );
  }

  /**
   * Finds processo , if processo = 2 => nuova programmazione( enable disable combo anno contabile).
   * @param idProgetto undefined
   * @return successful operation
   */
  getProcessoResponse(idProgetto?: number): __Observable<__StrictHttpResponse<number>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/recuperi/processo`,
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
   * Finds processo , if processo = 2 => nuova programmazione( enable disable combo anno contabile).
   * @param idProgetto undefined
   * @return successful operation
   */
  getProcesso(idProgetto?: number): __Observable<number> {
    return this.getProcessoResponse(idProgetto).pipe(
      __map(_r => _r.body as number)
    );
  }

  /**
   * Verifica se esiste una proposta di certificazione per il progetto.
   * @param idProgetto undefined
   * @return successful operation
   */
  checkPropostaCertificazioneProgettoResponse(idProgetto?: number): __Observable<__StrictHttpResponse<boolean>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/recuperi/proposta`,
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
   * Verifica se esiste una proposta di certificazione per il progetto.
   * @param idProgetto undefined
   * @return successful operation
   */
  checkPropostaCertificazioneProgetto(idProgetto?: number): __Observable<boolean> {
    return this.checkPropostaCertificazioneProgettoResponse(idProgetto).pipe(
      __map(_r => _r.body as boolean)
    );
  }

  /**
   * Verifica se è associata la regola BR28 per il progetto.
   * @param idProgetto undefined
   * @return successful operation
   */
  isRecuperoAccessibileResponse(idProgetto?: number): __Observable<__StrictHttpResponse<EsitoOperazioni>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/recuperi/regolaBR28`,
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
   * Verifica se è associata la regola BR28 per il progetto.
   * @param idProgetto undefined
   * @return successful operation
   */
  isRecuperoAccessibile(idProgetto?: number): __Observable<EsitoOperazioni> {
    return this.isRecuperoAccessibileResponse(idProgetto).pipe(
      __map(_r => _r.body as EsitoOperazioni)
    );
  }

  /**
   * Finds riepilogo recupero.
   * @param idProgetto undefined
   * @return successful operation
   */
  getRecuperiPerModificaResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<RigaModificaRecuperoItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/recuperi/riepilogoDati`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<RigaModificaRecuperoItem>>;
      })
    );
  }
  /**
   * Finds riepilogo recupero.
   * @param idProgetto undefined
   * @return successful operation
   */
  getRecuperiPerModifica(idProgetto?: number): __Observable<Array<RigaModificaRecuperoItem>> {
    return this.getRecuperiPerModificaResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<RigaModificaRecuperoItem>)
    );
  }

  /**
   * Finds Tipologie recuperi.
   * @return successful operation
   */
  getTipologieRecuperiResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/recuperi/tipologieRecuperi`,
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
   * Finds Tipologie recuperi.
   * @return successful operation
   */
  getTipologieRecuperi(): __Observable<Array<CodiceDescrizione>> {
    return this.getTipologieRecuperiResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Validate recuperi.
   * @param body undefined
   * @return successful operation
   */
  checkRecuperiResponse(body?: RequestSalvaRecuperi): __Observable<__StrictHttpResponse<EsitoSalvaRecuperoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/recuperi/validazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRecuperoDTO>;
      })
    );
  }
  /**
   * Validate recuperi.
   * @param body undefined
   * @return successful operation
   */
  checkRecuperi(body?: RequestSalvaRecuperi): __Observable<EsitoSalvaRecuperoDTO> {
    return this.checkRecuperiResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRecuperoDTO)
    );
  }

  /**
   * Deletes recupero by idRecupero
   * @param idRecupero undefined
   * @return successful operation
   */
  cancellaRecuperoResponse(idRecupero: number): __Observable<__StrictHttpResponse<EsitoSalvaRecuperoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/recuperi/${encodeURIComponent(String(idRecupero))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRecuperoDTO>;
      })
    );
  }
  /**
   * Deletes recupero by idRecupero
   * @param idRecupero undefined
   * @return successful operation
   */
  cancellaRecupero(idRecupero: number): __Observable<EsitoSalvaRecuperoDTO> {
    return this.cancellaRecuperoResponse(idRecupero).pipe(
      __map(_r => _r.body as EsitoSalvaRecuperoDTO)
    );
  }

  /**
   * Saves recuperi.
   * @param body undefined
   * @return successful operation
   */
  salvaRecuperiResponse(body?: RequestSalvaRecuperi): __Observable<__StrictHttpResponse<EsitoSalvaRecuperoDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/recuperi/all`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRecuperoDTO>;
      })
    );
  }
  /**
   * Saves recuperi.
   * @param body undefined
   * @return successful operation
   */
  salvaRecuperi(body?: RequestSalvaRecuperi): __Observable<EsitoSalvaRecuperoDTO> {
    return this.salvaRecuperiResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRecuperoDTO)
    );
  }

}

module RecuperiService {

  /**
   * Parameters for getDettaglioRecupero
   */
  export interface GetDettaglioRecuperoParams {
    idRecupero?: number;
    idProgetto?: number;
  }
}

export { RecuperiService }
