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
import { Decodifica } from '../commons/models/decodifica';
import { DettaglioRevoca } from '../commons/models/dettaglio-revoca';
import { RigaModificaRevocaItem } from '../commons/models/riga-modifica-revoca-item';
import { ModalitaAgevolazioneDTO } from '../commons/dto/modalita-agevolazione-dto';
import { EsitoSalvaRevocaDTO } from '../commons/dto/esito-salva-revoca-dto';
import { RequestModificaDisimpegno } from '../commons/models/request-modifica-disimpegno';
import { Revoca } from '../commons/models/revoca';
import { RequestFindIrregolarita } from '../commons/models/request-find-irregolarita';
import { RequestAssociaIrregolarita } from '../commons/models/request-associa-irregolarita';
import { RigaRevocaItem } from '../commons/models/riga-revoca-item';
import { RequestSalvaRevoche } from '../commons/models/request-salva-revoche';
import { ConfigService } from '../../core/services/config.service';

@Injectable({
  providedIn: 'root',
})
class DisimpegniService extends __BaseService {
  static readonly getAnnoContabilePath = '/disimpegni/annoContabile';
  static readonly getCausaleDisimpegnoPath = '/disimpegni/causaleDisimpegno';
  static readonly findClassRevocaIrregPath = '/disimpegni/classRevocaIrreg';
  static readonly getDettaglioRevocaPath = '/disimpegni/dettaglio';
  static readonly getDisimpegniPath = '/disimpegni/disimpegni';
  static readonly modificaDisimpegnoPath = '/disimpegni/disimpegno';
  static readonly findDsIrregolaritaPath = '/disimpegni/dsIrregolarita';
  static readonly getErogazioniPath = '/disimpegni/erogazioni';
  static readonly checkPropostaCertificazionePath = '/disimpegni/esistaProposta';
  static readonly getImportoValidatoTotalePath = '/disimpegni/importoValidatoTotale';
  static readonly findIrregolaritaPath = '/disimpegni/irregolarita';
  static readonly salvaIrregolaritaPath = '/disimpegni/irregolarita';
  static readonly getModalitaAgevolazionePath = '/disimpegni/modalitaAgevolazione';
  static readonly getModalitaRecuperoPath = '/disimpegni/modalitaRecupero';
  static readonly getMotivazioniPath = '/disimpegni/motivazioni';
  static readonly getRevochePath = '/disimpegni/revoche';
  static readonly salvaDisimpegniPath = '/disimpegni/revoche';
  static readonly eliminaRevocaPath = '/disimpegni/revoche';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * ( NUOVO DISIMPEGNO ) Finds Anno Contabile (init combo Anno Contabile)
   * @return successful operation
   */
  getAnnoContabileResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/annoContabile`,
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
   * ( NUOVO DISIMPEGNO ) Finds Anno Contabile (init combo Anno Contabile)
   * @return successful operation
   */
  getAnnoContabile(): __Observable<Array<CodiceDescrizione>> {
    return this.getAnnoContabileResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * ( NUOVO DISIMPEGNO ) Finds Causale Disimpegno (init combo Causale Disimpegno)
   * @return successful operation
   */
  getCausaleDisimpegnoResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/causaleDisimpegno`,
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
   * ( NUOVO DISIMPEGNO ) Finds Causale Disimpegno (init combo Causale Disimpegno)
   * @return successful operation
   */
  getCausaleDisimpegno(): __Observable<Array<CodiceDescrizione>> {
    return this.getCausaleDisimpegnoResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds class revoca irregolarita
   * @param idProgetto undefined
   * @return successful operation
   */
  findClassRevocaIrregResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<Decodifica>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/classRevocaIrreg`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<Decodifica>>;
      })
    );
  }
  /**
   * Finds class revoca irregolarita
   * @param idProgetto undefined
   * @return successful operation
   */
  findClassRevocaIrreg(idProgetto?: number): __Observable<Array<Decodifica>> {
    return this.findClassRevocaIrregResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<Decodifica>)
    );
  }

  /**
   * ( MODIFICA DISIMPEGNO ) Finds dettaglio revoca by idProgetto and idRevoca)
   * @param params The `DisimpegniService.GetDettaglioRevocaParams` containing the following parameters:
   *
   * - `idRevoca`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  getDettaglioRevocaResponse(params: DisimpegniService.GetDettaglioRevocaParams): __Observable<__StrictHttpResponse<DettaglioRevoca>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.idRevoca != null) __params = __params.set('idRevoca', params.idRevoca.toString());
    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/dettaglio`,
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
   * ( MODIFICA DISIMPEGNO ) Finds dettaglio revoca by idProgetto and idRevoca)
   * @param params The `DisimpegniService.GetDettaglioRevocaParams` containing the following parameters:
   *
   * - `idRevoca`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  getDettaglioRevoca(params: DisimpegniService.GetDettaglioRevocaParams): __Observable<DettaglioRevoca> {
    return this.getDettaglioRevocaResponse(params).pipe(
      __map(_r => _r.body as DettaglioRevoca)
    );
  }

  /**
   * Finds disimpegni
   * @param body undefined
   * @return successful operation
   */
  getDisimpegniResponse(body?: Array<ModalitaAgevolazioneDTO>): __Observable<__StrictHttpResponse<Array<RigaModificaRevocaItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/disimpegni/disimpegni`,
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
   * Finds disimpegni
   * @param body undefined
   * @return successful operation
   */
  getDisimpegni(body?: Array<ModalitaAgevolazioneDTO>): __Observable<Array<RigaModificaRevocaItem>> {
    return this.getDisimpegniResponse(body).pipe(
      __map(_r => _r.body as Array<RigaModificaRevocaItem>)
    );
  }

  /**
   * Updates disimpegno
   * @param body undefined
   * @return successful operation
   */
  modificaDisimpegnoResponse(body?: RequestModificaDisimpegno): __Observable<__StrictHttpResponse<EsitoSalvaRevocaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/disimpegni/disimpegno`,
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
   * Updates disimpegno
   * @param body undefined
   * @return successful operation
   */
  modificaDisimpegno(body?: RequestModificaDisimpegno): __Observable<EsitoSalvaRevocaDTO> {
    return this.modificaDisimpegnoResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRevocaDTO)
    );
  }

  /**
   * Finds Ds irregolarita
   * @param idProgetto undefined
   * @return successful operation
   */
  findDsIrregolaritaResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<number>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/dsIrregolarita`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<number>>;
      })
    );
  }
  /**
   * Finds Ds irregolarita
   * @param idProgetto undefined
   * @return successful operation
   */
  findDsIrregolarita(idProgetto?: number): __Observable<Array<number>> {
    return this.findDsIrregolaritaResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<number>)
    );
  }

  /**
   * Finds erogazioni
   * @param body undefined
   * @return successful operation
   */
  getErogazioniResponse(body?: Array<ModalitaAgevolazioneDTO>): __Observable<__StrictHttpResponse<Array<RigaModificaRevocaItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/disimpegni/erogazioni`,
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
   * Finds erogazioni
   * @param body undefined
   * @return successful operation
   */
  getErogazioni(body?: Array<ModalitaAgevolazioneDTO>): __Observable<Array<RigaModificaRevocaItem>> {
    return this.getErogazioniResponse(body).pipe(
      __map(_r => _r.body as Array<RigaModificaRevocaItem>)
    );
  }

  /**
   * ( NUOVO DISIMPEGNO ) Finds Importo Validato Totale
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
      this.rootUrl + `/restfacade/disimpegni/importoValidatoTotale`,
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
   * ( NUOVO DISIMPEGNO ) Finds Importo Validato Totale
   * @param idProgetto undefined
   * @return successful operation
   */
  getImportoValidatoTotale(idProgetto?: number): __Observable<number> {
    return this.getImportoValidatoTotaleResponse(idProgetto).pipe(
      __map(_r => _r.body as number)
    );
  }

  /**
   * Finds irregolarita
   * @param body undefined
   * @return successful operation
   */
  findIrregolaritaResponse(body?: RequestFindIrregolarita): __Observable<__StrictHttpResponse<Revoca>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/disimpegni/irregolarita`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Revoca>;
      })
    );
  }
  /**
   * Finds irregolarita
   * @param body undefined
   * @return successful operation
   */
  findIrregolarita(body?: RequestFindIrregolarita): __Observable<Revoca> {
    return this.findIrregolaritaResponse(body).pipe(
      __map(_r => _r.body as Revoca)
    );
  }

  /**
   * Saves Irregolarita
   * @param body undefined
   * @return successful operation
   */
  salvaIrregolaritaResponse(body?: RequestAssociaIrregolarita): __Observable<__StrictHttpResponse<EsitoSalvaRevocaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/disimpegni/irregolarita`,
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
   * Saves Irregolarita
   * @param body undefined
   * @return successful operation
   */
  salvaIrregolarita(body?: RequestAssociaIrregolarita): __Observable<EsitoSalvaRevocaDTO> {
    return this.salvaIrregolaritaResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRevocaDTO)
    );
  }

  /**
   * Finds modalita agevolazione per disimpegni
   * @param idProgetto undefined
   * @return successful operation
   */
  getModalitaAgevolazioneResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<ModalitaAgevolazioneDTO>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/modalitaAgevolazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<ModalitaAgevolazioneDTO>>;
      })
    );
  }
  /**
   * Finds modalita agevolazione per disimpegni
   * @param idProgetto undefined
   * @return successful operation
   */
  getModalitaAgevolazione(idProgetto?: number): __Observable<Array<ModalitaAgevolazioneDTO>> {
    return this.getModalitaAgevolazioneResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<ModalitaAgevolazioneDTO>)
    );
  }

  /**
   * ( NUOVO DISIMPEGNO ) Finds Modalita Recupero (init combo Modalita Recupero)
   * @return successful operation
   */
  getModalitaRecuperoResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/modalitaRecupero`,
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
   * ( NUOVO DISIMPEGNO ) Finds Modalita Recupero (init combo Modalita Recupero)
   * @return successful operation
   */
  getModalitaRecupero(): __Observable<Array<CodiceDescrizione>> {
    return this.getModalitaRecuperoResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * ( NUOVO DISIMPEGNO ) Finds Motivazioni (init combo motivazioni)
   * @param idProgetto undefined
   * @return successful operation
   */
  getMotivazioniResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/motivazioni`,
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
   * ( NUOVO DISIMPEGNO ) Finds Motivazioni (init combo motivazioni)
   * @param idProgetto undefined
   * @return successful operation
   */
  getMotivazioni(idProgetto?: number): __Observable<Array<CodiceDescrizione>> {
    return this.getMotivazioniResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * ( NUOVO DISIMPEGNO ) Finds revoche by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getRevocheResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<RigaRevocaItem>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/disimpegni/revoche`,
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
   * ( NUOVO DISIMPEGNO ) Finds revoche by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getRevoche(idProgetto?: number): __Observable<Array<RigaRevocaItem>> {
    return this.getRevocheResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<RigaRevocaItem>)
    );
  }

  /**
   * Saves dismpegni
   * @param body undefined
   * @return successful operation
   */
  salvaDisimpegniResponse(body?: RequestSalvaRevoche): __Observable<__StrictHttpResponse<EsitoSalvaRevocaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/disimpegni/revoche`,
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
   * Saves dismpegni
   * @param body undefined
   * @return successful operation
   */
  salvaDisimpegni(body?: RequestSalvaRevoche): __Observable<EsitoSalvaRevocaDTO> {
    return this.salvaDisimpegniResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRevocaDTO)
    );
  }

  /**
   * Deletes revoca by idRevoca and idProgetto
   * @param params The `DisimpegniService.EliminaRevocaParams` containing the following parameters:
   *
   * - `idRevoca`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  eliminaRevocaResponse(params: DisimpegniService.EliminaRevocaParams): __Observable<__StrictHttpResponse<EsitoSalvaRevocaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.idRevoca != null) __params = __params.set('idRevoca', params.idRevoca.toString());
    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/disimpegni/revoche`,
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
   * Deletes revoca by idRevoca and idProgetto
   * @param params The `DisimpegniService.EliminaRevocaParams` containing the following parameters:
   *
   * - `idRevoca`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  eliminaRevoca(params: DisimpegniService.EliminaRevocaParams): __Observable<EsitoSalvaRevocaDTO> {
    return this.eliminaRevocaResponse(params).pipe(
      __map(_r => _r.body as EsitoSalvaRevocaDTO)
    );
  }
}

module DisimpegniService {

  /**
   * Parameters for getDettaglioRevoca
   */
  export interface GetDettaglioRevocaParams {
    idRevoca?: number;
    idProgetto?: number;
  }

  /**
   * Parameters for eliminaRevoca
   */
  export interface EliminaRevocaParams {
    idRevoca?: number;
    idProgetto?: number;
  }
}

export { DisimpegniService }
