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

import { EsitoSalvaIrregolaritaDTO } from '../commons/dto/esito-salva-irregolarita-dto';
import { CodiceDescrizione } from '../commons/models/codice-descrizione';
import { ChecklistRettificaForfettariaDTO } from '../commons/dto/checklist-rettifica-forfettaria-dto';
import { Irregolarita } from '../commons/models/irregolarita';
import { FiltroRicercaIrregolarita } from '../commons/models/filtro-ricerca-irregolarita';
import { RequestSalvaIrregolarita } from '../commons/models/request-salva-irregolarita';
import { RequestModificaIrregolarita } from '../commons/models/request-modifica-irregolarita';
import { EsitoOperazioni } from '../commons/models/esito-operazioni';
import { EsitoSalvaRettificaForfettariaDTO } from '../commons/dto/esito-salva-rettifica-forfettaria-dto';
import { RettificaForfettariaDTO } from '../commons/dto/rettifica-forfettaria-dto';
import { RettificaForfettaria } from '../commons/models/rettifica-forfettaria';
import { ConfigService } from '../../core/services/config.service';

@Injectable({
  providedIn: 'root',
})
class RegistroControlliService extends __BaseService {
  static readonly cancellaIrregolaritaRegolarePath = '/registroControlli/cancellazioneEsitiRegolari/{idIrregolarita}';
  static readonly cancellaIrregolaritaPath = '/registroControlli/cancellazioneIrregolarita/{idIrregolarita}';
  static readonly cancellaIrregolaritaProvvisoriaPath = '/registroControlli/cancellazioneIrregolaritaProvv/{idIrregolarita}';
  static readonly getCategAnagraficaPath = '/registroControlli/categAnagrafica';
  static readonly getChecklistRettifichePath = '/registroControlli/checkListRettifiche';
  static readonly getDataCampionePath = '/registroControlli/dataCampione';
  static readonly getDettaglioEsitoRegolarePath = '/registroControlli/dettaglioEsitiRegolari/{idEsitoRegolare}';
  static readonly getDettaglioIrregolaritaPath = '/registroControlli/dettaglioIrregolarita/{idIrregolarita}';
  static readonly getContenutoDocumentoByIdPath = '/registroControlli/documenti/{idDocumentoIndex}/contenuto';
  static readonly getEsitiRegolariPath = '/registroControlli/esitiRegolari';
  static readonly inserisciIrregolaritaRegolarePath = '/registroControlli/inserimentoEsitoRegolare';
  static readonly inserisciIrregolaritaPath = '/registroControlli/inserimentoIrregolarita';
  static readonly inserisciIrregolaritaProvvisoriaPath = '/registroControlli/inserimentoIrregolaritaProvv';
  static readonly getIrregolaritaPath = '/registroControlli/irregolarita';
  static readonly modificaEsitoRegolarePath = '/registroControlli/modificaEsitiRegolari';
  static readonly modificaIrregolaritaPath = '/registroControlli/modificaIrregolarita';
  static readonly modificaIrregolaritaProvvisoriaPath = '/registroControlli/modificaIrregolaritaProvvisoria';
  static readonly getPeriodiPath = '/registroControlli/periodi';
  static readonly esistePropostaCertificazioneApertaPath = '/registroControlli/propostaAperta';
  static readonly salvaRettificaForfettariaPath = '/registroControlli/rettifica';
  static readonly getRettifichePath = '/registroControlli/rettifiche';
  static readonly eliminaRettificaForfettariaPath = '/registroControlli/rettificheForfettarie/{idRettificaForfett}';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  cancellaIrregolaritaRegolareResponse(idIrregolarita: number): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/registroControlli/cancellazioneEsitiRegolari/${encodeURIComponent(String(idIrregolarita))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  cancellaIrregolaritaRegolare(idIrregolarita: number): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.cancellaIrregolaritaRegolareResponse(idIrregolarita).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }

  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  cancellaIrregolaritaResponse(idIrregolarita: number): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/registroControlli/cancellazioneIrregolarita/${encodeURIComponent(String(idIrregolarita))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  cancellaIrregolarita(idIrregolarita: number): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.cancellaIrregolaritaResponse(idIrregolarita).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }

  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  cancellaIrregolaritaProvvisoriaResponse(idIrregolarita: number): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/registroControlli/cancellazioneIrregolaritaProvv/${encodeURIComponent(String(idIrregolarita))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  cancellaIrregolaritaProvvisoria(idIrregolarita: number): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.cancellaIrregolaritaProvvisoriaResponse(idIrregolarita).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }

  /**
   * Finds anno contabili
   * @return successful operation
   */
  getCategAnagraficaResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/categAnagrafica`,
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
   * Finds anno contabili
   * @return successful operation
   */
  getCategAnagrafica(): __Observable<Array<CodiceDescrizione>> {
    return this.getCategAnagraficaResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }


  /**
   * Finds motivi irregolarita
   * @return successful operation
   */
  getMotiviIrregolaritaResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/motivoIrregolarita`,
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
   * Finds anno contabili
   * @return successful operation
   */
  getMotiviIrregolarita(): __Observable<Array<CodiceDescrizione>> {
    return this.getMotiviIrregolaritaResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds Checklist Rettifiche (per popolare la tabella checlist rettifiche)
   * @param idProgetto undefined
   * @return successful operation
   */
  getChecklistRettificheResponse(idProgetto?: number): __Observable<__StrictHttpResponse<ChecklistRettificaForfettariaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/checkListRettifiche`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ChecklistRettificaForfettariaDTO>;
      })
    );
  }
  /**
   * Finds Checklist Rettifiche (per popolare la tabella checlist rettifiche)
   * @param idProgetto undefined
   * @return successful operation
   */
  getChecklistRettifiche(idProgetto?: number): __Observable<ChecklistRettificaForfettariaDTO> {
    return this.getChecklistRettificheResponse(idProgetto).pipe(
      __map(_r => _r.body as ChecklistRettificaForfettariaDTO)
    );
  }

  /**
   * Finds data campione
   * @param params The `RegistroControlliService.GetDataCampioneParams` containing the following parameters:
   *
   * - `tipoControlli`:
   *
   * - `idProgetto`:
   *
   * - `idPeriodo`:
   *
   * - `idCategAnagrafica`:
   *
   * @return successful operation
   */
  getDataCampioneResponse(params: RegistroControlliService.GetDataCampioneParams): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.tipoControlli != null) __params = __params.set('tipoControlli', params.tipoControlli.toString());
    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    if (params.idPeriodo != null) __params = __params.set('idPeriodo', params.idPeriodo.toString());
    if (params.idCategAnagrafica != null) __params = __params.set('idCategAnagrafica', params.idCategAnagrafica.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/dataCampione`,
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
   * Finds data campione
   * @param params The `RegistroControlliService.GetDataCampioneParams` containing the following parameters:
   *
   * - `tipoControlli`:
   *
   * - `idProgetto`:
   *
   * - `idPeriodo`:
   *
   * - `idCategAnagrafica`:
   *
   * @return successful operation
   */
  getDataCampione(params: RegistroControlliService.GetDataCampioneParams): __Observable<Array<CodiceDescrizione>> {
    return this.getDataCampioneResponse(params).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds Dettaglio Esito Regolare by idEsitoRegolare
   * @param idEsitoRegolare undefined
   * @return successful operation
   */
  getDettaglioEsitoRegolareResponse(idEsitoRegolare: number): __Observable<__StrictHttpResponse<Irregolarita>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/dettaglioEsitiRegolari/${encodeURIComponent(String(idEsitoRegolare))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Irregolarita>;
      })
    );
  }
  /**
   * Finds Dettaglio Esito Regolare by idEsitoRegolare
   * @param idEsitoRegolare undefined
   * @return successful operation
   */
  getDettaglioEsitoRegolare(idEsitoRegolare: number): __Observable<Irregolarita> {
    return this.getDettaglioEsitoRegolareResponse(idEsitoRegolare).pipe(
      __map(_r => _r.body as Irregolarita)
    );
  }

  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  getDettaglioIrregolaritaResponse(idIrregolarita: number, idU: number): __Observable<__StrictHttpResponse<Irregolarita>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __params.set('idU', idU.toString())

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/dettaglioIrregolarita/${encodeURIComponent(String(idIrregolarita))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Irregolarita>;
      })
    );
  }
  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  getDettaglioIrregolarita(idIrregolarita: number, idU: number): __Observable<Irregolarita> {
    return this.getDettaglioIrregolaritaResponse(idIrregolarita, idU).pipe(
      __map(_r => _r.body as Irregolarita)
    );
  }

  /**
   * Finds Dettaglio Irregolarita provvisoria by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  getDettaglioIrregolaritaProvvisoriaResponse(idIrregolarita: number, idU: number): __Observable<__StrictHttpResponse<Irregolarita>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __params.set('idU', idU.toString())

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/dettaglioIrregolaritaProvvisoria/${encodeURIComponent(String(idIrregolarita))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Irregolarita>;
      })
    );
  }
  /**
   * Finds Dettaglio Irregolarita provvisoria by idIrregolaritas
   * @param idIrregolarita undefined
   * @return successful operation
   */
  getDettaglioIrregolaritaProvvisoria(idIrregolarita: number, idU: number): __Observable<Irregolarita> {
    return this.getDettaglioIrregolaritaProvvisoriaResponse(idIrregolarita, idU).pipe(
      __map(_r => _r.body as Irregolarita)
    );
  }

  /**
   * Finds Esiti Regolari by FiltroRicercaIrregolarita
   * @param body undefined
   * @return successful operation
   */
  getEsitiRegolariResponse(body?: FiltroRicercaIrregolarita): __Observable<__StrictHttpResponse<Array<Irregolarita>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/registroControlli/esitiRegolari`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<Irregolarita>>;
      })
    );
  }
  /**
   * Finds Esiti Regolari by FiltroRicercaIrregolarita
   * @param body undefined
   * @return successful operation
   */
  getEsitiRegolari(body?: FiltroRicercaIrregolarita): __Observable<Array<Irregolarita>> {
    return this.getEsitiRegolariResponse(body).pipe(
      __map(_r => _r.body as Array<Irregolarita>)
    );
  }

  /**
   * Updates irregolarita regolare
   * @param body undefined
   * @return successful operation
   */
  inserisciIrregolaritaRegolareResponse(body?: RequestSalvaIrregolarita): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/registroControlli/inserimentoEsitoRegolare`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Updates irregolarita regolare
   * @param body undefined
   * @return successful operation
   */
  inserisciIrregolaritaRegolare(body?: RequestSalvaIrregolarita): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.inserisciIrregolaritaRegolareResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }

  /**
   * Updates irregolarita regolare
   * @param body undefined
   * @return successful operation
   */
  inserisciIrregolaritaResponse(body?: RequestSalvaIrregolarita): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/registroControlli/inserimentoIrregolarita`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Updates irregolarita regolare
   * @param body undefined
   * @return successful operation
   */
  inserisciIrregolarita(body?: RequestSalvaIrregolarita): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.inserisciIrregolaritaResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }

  /**
   * Updates irregolarita regolare
   * @param body undefined
   * @return successful operation
   */
  inserisciIrregolaritaProvvisoriaResponse(body?: RequestSalvaIrregolarita): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/registroControlli/inserimentoIrregolaritaProvv`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Updates irregolarita regolare
   * @param body undefined
   * @return successful operation
   */
  inserisciIrregolaritaProvvisoria(body?: RequestSalvaIrregolarita): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.inserisciIrregolaritaProvvisoriaResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }

  /**
   * Finds irregolarita by FiltroRicercaIrregolarita
   * @param body undefined
   * @return successful operation
   */
  getIrregolaritaResponse(body?: FiltroRicercaIrregolarita): __Observable<__StrictHttpResponse<Array<Irregolarita>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/registroControlli/irregolarita`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<Irregolarita>>;
      })
    );
  }
  /**
   * Finds irregolarita by FiltroRicercaIrregolarita
   * @param body undefined
   * @return successful operation
   */
  getIrregolarita(body?: FiltroRicercaIrregolarita): __Observable<Array<Irregolarita>> {
    return this.getIrregolaritaResponse(body).pipe(
      __map(_r => _r.body as Array<Irregolarita>)
    );
  }

  /**
   * Updates irregolarita regolare
   * @param body undefined
   * @return successful operation
   */
  modificaEsitoRegolareResponse(body?: RequestModificaIrregolarita): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/registroControlli/modificaEsitiRegolari`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Updates irregolarita regolare
   * @param body undefined
   * @return successful operation
   */
  modificaEsitoRegolare(body?: RequestModificaIrregolarita): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.modificaEsitoRegolareResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }

  /**
   * Updates irregolarita
   * @param body undefined
   * @return successful operation
   */
  modificaIrregolaritaProvvisoriaResponse(body?: RequestModificaIrregolarita): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/registroControlli/modificaIrregolaritaProvvisoria`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Updates irregolarita
   * @param body undefined
   * @return successful operation
   */
  modificaIrregolaritaProvvisoria(body?: RequestModificaIrregolarita): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.modificaIrregolaritaProvvisoriaResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }

  /**
   * Finds anno contabili
   * @return successful operation
   */
  getPeriodiResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/periodi`,
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
   * Finds anno contabili
   * @return successful operation
   */
  getPeriodi(): __Observable<Array<CodiceDescrizione>> {
    return this.getPeriodiResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Controlla se esiste una proposta certificazione apperta per un progetto
   * @param idProgetto undefined
   * @return successful operation
   */
  esistePropostaCertificazioneApertaResponse(idProgetto?: number): __Observable<__StrictHttpResponse<EsitoOperazioni>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/registroControlli/propostaAperta`,
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
   * Controlla se esiste una proposta certificazione apperta per un progetto
   * @param idProgetto undefined
   * @return successful operation
   */
  esistePropostaCertificazioneAperta(idProgetto?: number): __Observable<EsitoOperazioni> {
    return this.esistePropostaCertificazioneApertaResponse(idProgetto).pipe(
      __map(_r => _r.body as EsitoOperazioni)
    );
  }

  /**
   * Saves Rettifica Forfettaria
   * @param body undefined
   * @return successful operation
   */
  salvaRettificaForfettariaResponse(body?: RettificaForfettariaDTO): __Observable<__StrictHttpResponse<EsitoSalvaRettificaForfettariaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/registroControlli/rettifica`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaRettificaForfettariaDTO>;
      })
    );
  }
  /**
   * Saves Rettifica Forfettaria
   * @param body undefined
   * @return successful operation
   */
  salvaRettificaForfettaria(body?: RettificaForfettariaDTO): __Observable<EsitoSalvaRettificaForfettariaDTO> {
    return this.salvaRettificaForfettariaResponse(body).pipe(
      __map(_r => _r.body as EsitoSalvaRettificaForfettariaDTO)
    );
  }

  /**
   * Finds Rettifiche by FiltroRicercaIrregolarita
   * @param body undefined
   * @return successful operation
   */
  getRettificheResponse(body?: FiltroRicercaIrregolarita): __Observable<__StrictHttpResponse<Array<RettificaForfettaria>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/registroControlli/rettifiche`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<RettificaForfettaria>>;
      })
    );
  }
  /**
   * Finds Rettifiche by FiltroRicercaIrregolarita
   * @param body undefined
   * @return successful operation
   */
  getRettifiche(body?: FiltroRicercaIrregolarita): __Observable<Array<RettificaForfettaria>> {
    return this.getRettificheResponse(body).pipe(
      __map(_r => _r.body as Array<RettificaForfettaria>)
    );
  }

  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idRettificaForfett undefined
   * @return successful operation
   */
  eliminaRettificaForfettariaResponse(idRettificaForfett: number): __Observable<__StrictHttpResponse<EsitoSalvaIrregolaritaDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/registroControlli/rettificheForfettarie/${encodeURIComponent(String(idRettificaForfett))}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoSalvaIrregolaritaDTO>;
      })
    );
  }
  /**
   * Finds Dettaglio Irregolarita by idIrregolaritas
   * @param idRettificaForfett undefined
   * @return successful operation
   */
  eliminaRettificaForfettaria(idRettificaForfett: number): __Observable<EsitoSalvaIrregolaritaDTO> {
    return this.eliminaRettificaForfettariaResponse(idRettificaForfett).pipe(
      __map(_r => _r.body as EsitoSalvaIrregolaritaDTO)
    );
  }
}

module RegistroControlliService {

  /**
   * Parameters for getDataCampione
   */
  export interface GetDataCampioneParams {
    tipoControlli?: string;
    idProgetto?: number;
    idPeriodo?: number;
    idCategAnagrafica?: number;
  }
}

export { RegistroControlliService }
