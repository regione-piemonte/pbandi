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
import {ConfigService} from "../../core/services/config.service";
import {RigaRevocaItem} from "../commons/models/riga-revoca-item";
import {RequestSalvaRevoche} from "../commons/requests/request-salva-revoche";
import {RequestModificaRevoca} from "../commons/requests/request-modifica-revoca";
import {EsitoSalvaRevocaDTO} from "../commons/dto/esito-salva-revoca-dto";
import {CodiceDescrizione} from "../../shared/commons/models/codice-descrizione";
import {RigaModificaRevocaItem} from "../commons/models/riga-modifica-revoca-item";
import {DettaglioRevoca} from "../commons/models/dettaglio-revoca";
import {EsitoOperazioni} from "../../erogazione/commons/models/esito-operazioni";

@Injectable({
  providedIn: 'root',
})
class RevocheService extends __BaseService {
  static readonly getRevochePath = '/revoche';
  static readonly revocaTuttoPath = '/revoche/all';
  static readonly modificaRevocaPath = '/revoche/all';
  static readonly getImportoValidatoTotalePath = '/revoche/importoValidatoTotale';
  static readonly findAndFilterMotivazioniPath = '/revoche/motivazioni';
  static readonly checkPropostaCertificazioneProgettoPath = '/revoche/proposta';
  static readonly getRevochePerModificaPath = '/revoche/riepilogoDati';
  static readonly checkRevochePath = '/revoche/validazione';
  static readonly cancellaRevocaPath = '/revoche/{idRevoca}';
  static readonly getDettaglioRevocaPath = '/revoche/{idRevoca}/dettagglio';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds revoche
   * @param idProgetto undefined
   * @return successful operation
   */
  getRevocheResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<RigaRevocaItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/revoche`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<RigaRevocaItem>>;
      })
    );
  }
  /**
   * Finds revoche
   * @param idProgetto undefined
   * @return successful operation
   */
  getRevoche(idProgetto?: number): __Observable<Array<RigaRevocaItem>> {
    return this.getRevocheResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<RigaRevocaItem>)
    );
  }

  /**
   * Calcola ImportoRevoca per ogni revoca.
   * @param body undefined
   * @return successful operation
   */
  revocaTuttoResponse(body?: RequestSalvaRevoche): __Observable<__StrictHttpResponse<Array<RigaRevocaItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/revoche/importoRevocaNew`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<RigaRevocaItem>>;
      })
    );
  }
  /**
   * Calcola ImportoRevoca per ogni revoca.
   * @param body undefined
   * @return successful operation
   */
  revocaTutto(body?: RequestSalvaRevoche): __Observable<Array<RigaRevocaItem>> {
    return this.revocaTuttoResponse(body).pipe(
      __map(_r => _r.body as Array<RigaRevocaItem>)
    );
  }

  /**
   * Updates revoca.
   * @param body undefined
   * @return successful operation
   */
  modificaRevocaResponse(body?: RequestModificaRevoca): __Observable<__StrictHttpResponse<EsitoSalvaRevocaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/revoche/all`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRevocaDTO>;
      })
    );
  }
  /**
   * Updates revoca.
   * @param body undefined
   * @return successful operation
   */
  modificaRevoca(body?: RequestModificaRevoca): __Observable<EsitoSalvaRevocaDTO> {
    return this.modificaRevocaResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRevocaDTO)
    );
  }

  /**
   * Finds importo validato Totale
   * @param idProgetto undefined
   * @return successful operation
   */
  getImportoValidatoTotaleResponse(idProgetto?: number): __Observable<__StrictHttpResponse<number>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/revoche/importoValidatoTotale`,
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
   * Finds importo validato Totale
   * @param idProgetto undefined
   * @return successful operation
   */
  getImportoValidatoTotale(idProgetto?: number): __Observable<number> {
    return this.getImportoValidatoTotaleResponse(idProgetto).pipe(
      __map(_r => _r.body as number)
    );
  }

  /**
   * Finds motivazioni
   * @param idProgetto undefined
   * @return successful operation
   */
  findAndFilterMotivazioniResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/revoche/motivazioni`,
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
   * Finds motivazioni
   * @param idProgetto undefined
   * @return successful operation
   */
  findAndFilterMotivazioni(idProgetto?: number): __Observable<Array<CodiceDescrizione>> {
    return this.findAndFilterMotivazioniResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Verifica se esiste una proposta di certificazione per il progetto.
   * @param idProgetto undefined
   * @return successful operation
   */
  checkPropostaCertificazioneProgettoResponse(idProgetto?: number): __Observable<__StrictHttpResponse<EsitoOperazioni>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/revoche/proposta`,
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
   * Verifica se esiste una proposta di certificazione per il progetto.
   * @param idProgetto undefined
   * @return successful operation
   */
  checkPropostaCertificazioneProgetto(idProgetto?: number): __Observable<EsitoOperazioni> {
    return this.checkPropostaCertificazioneProgettoResponse(idProgetto).pipe(
      __map(_r => _r.body as EsitoOperazioni)
    );
  }

  /**
   * Finds dettaglio revoca by idRevoca and idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getRevochePerModificaResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<RigaModificaRevocaItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/revoche/riepilogoDati`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<RigaModificaRevocaItem>>;
      })
    );
  }
  /**
   * Finds dettaglio revoca by idRevoca and idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getRevochePerModifica(idProgetto?: number): __Observable<Array<RigaModificaRevocaItem>> {
    return this.getRevochePerModificaResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<RigaModificaRevocaItem>)
    );
  }

  /**
   * Validate revoche.
   * @param body undefined
   * @return successful operation
   */
  checkRevocheResponse(body?: RequestSalvaRevoche): __Observable<__StrictHttpResponse<EsitoSalvaRevocaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/revoche/validazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRevocaDTO>;
      })
    );
  }
  /**
   * Validate revoche.
   * @param body undefined
   * @return successful operation
   */
  checkRevoche(body?: RequestSalvaRevoche): __Observable<EsitoSalvaRevocaDTO> {
    return this.checkRevocheResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRevocaDTO)
    );
  }

  /**
   * Deletes revoca by idRevoca
   * @param idRevoca undefined
   * @return successful operation
   */
  cancellaRevocaResponse(idRevoca: number): __Observable<__StrictHttpResponse<EsitoSalvaRevocaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/revoche/${encodeURIComponent(String(idRevoca))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRevocaDTO>;
      })
    );
  }
  /**
   * Deletes revoca by idRevoca
   * @param idRevoca undefined
   * @return successful operation
   */
  cancellaRevoca(idRevoca: number): __Observable<EsitoSalvaRevocaDTO> {
    return this.cancellaRevocaResponse(idRevoca).pipe(
      __map(_r => _r.body as EsitoSalvaRevocaDTO)
    );
  }

  /**
   * Finds dettaglio revoca by idRevoca and idProgetto
   * @param params The `RevocheService.GetDettaglioRevocaParams` containing the following parameters:
   *
   * - `idRevoca`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  getDettaglioRevocaResponse(params: RevocheService.GetDettaglioRevocaParams): __Observable<__StrictHttpResponse<DettaglioRevoca>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/revoche/${encodeURIComponent(String(params.idRevoca))}/dettagglio`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<DettaglioRevoca>;
      })
    );
  }
  /**
   * Finds dettaglio revoca by idRevoca and idProgetto
   * @param params The `RevocheService.GetDettaglioRevocaParams` containing the following parameters:
   *
   * - `idRevoca`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  getDettaglioRevoca(params: RevocheService.GetDettaglioRevocaParams): __Observable<DettaglioRevoca> {
    return this.getDettaglioRevocaResponse(params).pipe(
      __map(_r => _r.body as DettaglioRevoca)
    );
  }

  /**
   * Saves revoche.
   * @param body undefined
   * @return successful operation
   */
  salvaRevocheResponse(body?: RequestSalvaRevoche): __Observable<__StrictHttpResponse<EsitoSalvaRevocaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/revoche/all`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRevocaDTO>;
      })
    );
  }
  /**
   * Saves revoche.
   * @param body undefined
   * @return successful operation
   */
  salvaRevoche(body?: RequestSalvaRevoche): __Observable<EsitoSalvaRevocaDTO> {
    return this.salvaRevocheResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRevocaDTO)
    );
  }

}

module RevocheService {

  /**
   * Parameters for getDettaglioRevoca
   */
  export interface GetDettaglioRevocaParams {
    idRevoca: number;
    idProgetto?: number;
  }
}

export { RevocheService }
