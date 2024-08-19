/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { BaseService as __BaseService } from '../../shared/base-service';
import { ApiConfiguration as __Configuration } from '../../shared/api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../../shared/strict-http-response';
import { Observable, Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { EsitoErogazioneDTO } from '../commons/dto/esito-erogazione-dto';
import { EsitoControllaDati } from '../commons/models/esito-controlla-dati';
import { ControllaDatiRequest } from '../commons/requests/controlla-dati-request';
import { CodiceDescrizioneCausale } from '../commons/models/codice-descrizione-causale';
import { CodiceDescrizione } from '../commons/models/codice-descrizione';
import { DatiCalcolati } from '../commons/models/dati-calcolati';
import { GetDatiCalcolatiRequest } from '../commons/requests/get-dati-calcolati-request';
import { Erogazione } from '../commons/models/erogazione';
import { SalvaErogazioneRequest } from '../commons/requests/salva-erogazione-request';
import { EsitoOperazioni } from '../commons/models/esito-operazioni';
import { ModificaErogazioneRequest } from '../commons/requests/modifica-erogazione-request';
import { EsitoRichiestaErogazioneDTO } from '../commons/dto/esito-richiesta-erogazione-dto';
import { EsitoReportRichiestaErogazioneDTO } from '../commons/dto/esito-report-richiesta-erogazione-dto';
import { CreaRichiestaErogazioneRequest } from '../commons/requests/crea-richiesta-erogazione-request';
import { RappresentanteLegaleDTO } from '../commons/dto/rappresentante-legale-dto';
import { ConfigService } from '../../core/services/config.service';

@Injectable({
  providedIn: 'root',
})
class ErogazioneService extends __BaseService {
  static readonly getErogazionePath = '/erogazione';
  static readonly controllaDatiOnSelectCausaleErogazionePath = '/erogazione/causale/dati';
  static readonly getCausaliErogazioniPath = '/erogazione/causali';
  static readonly getAllCodiciDirezionePath = '/erogazione/codiciDirezione';
  static readonly getDatiCalcolatiPath = '/erogazione/datiCalcolati';
  static readonly getDettaglioErogazionePath = '/erogazione/dettaglioErogazione';
  static readonly inserisciErogazionePath = '/erogazione/erogazione';
  static readonly modificaErogazionePath = '/erogazione/erogazione';
  static readonly eliminaErogazionePath = '/erogazione/erogazione';
  static readonly getAllModalitaAgevolazioneContoEconomicoPath = '/erogazione/modalitaAgevolazione';
  static readonly getAllModalitaErogazionePath = '/erogazione/modalitaErogazione';
  static readonly getDatiRiepilogoRichiestaErogazionePath = '/erogazione/richiesta';
  static readonly creaRichiestaErogazionePath = '/erogazione/richiesta';
  static readonly getDelegatiPath = '/erogazione/richiesta/delegati';
  static readonly getRappresentantiLegaliPath = '/erogazione/richiesta/rappresentantiLegali';

  constructor(
    config: __Configuration,
    configService: ConfigService,
    http: HttpClient
  ) {
    super(config, configService, http);
  }

  /**
   * Finds erogazione by idProgetto
   * @param params The `ErogazioneService.GetErogazioneParams` containing the following parameters:
   *
   * - `idU`:
   *
   * - `idSoggettoBeneficiario`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  getErogazioneResponse(params: ErogazioneService.GetErogazioneParams): __Observable<__StrictHttpResponse<EsitoErogazioneDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.idU != null) __params = __params.set('idU', params.idU.toString());
    if (params.idSoggettoBeneficiario != null) __params = __params.set('idSoggettoBeneficiario', params.idSoggettoBeneficiario.toString());
    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoErogazioneDTO>;
      })
    );
  }
  /**
   * Finds erogazione by idProgetto
   * @param params The `ErogazioneService.GetErogazioneParams` containing the following parameters:
   *
   * - `idU`:
   *
   * - `idSoggettoBeneficiario`:
   *
   * - `idProgetto`:
   *
   * @return successful operation
   */
  getErogazione(params: ErogazioneService.GetErogazioneParams): __Observable<EsitoErogazioneDTO> {
    return this.getErogazioneResponse(params).pipe(
      __map(_r => _r.body as EsitoErogazioneDTO)
    );
  }

  /**
   * Controlla dati on select causale erogazione:  ControllaDatiRequest.importoCalcolato = Erogazione.spesaProgetto.importoAmmessoContributo,ControllaDatiRequest.importoResiduoSpettante = DatiCalcolati.importoResiduoSpettante,ControllaDatiRequest.importoErogazioneEffettiva = DatiCalcolati.importoErogazioneEffettiva.
   * @param body undefined
   * @return successful operation
   */
  controllaDatiOnSelectCausaleErogazioneResponse(body?: ControllaDatiRequest): __Observable<__StrictHttpResponse<EsitoControllaDati>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/erogazione/causale/dati`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoControllaDati>;
      })
    );
  }
  /**
   * Controlla dati on select causale erogazione:  ControllaDatiRequest.importoCalcolato = Erogazione.spesaProgetto.importoAmmessoContributo,ControllaDatiRequest.importoResiduoSpettante = DatiCalcolati.importoResiduoSpettante,ControllaDatiRequest.importoErogazioneEffettiva = DatiCalcolati.importoErogazioneEffettiva.
   * @param body undefined
   * @return successful operation
   */
  controllaDatiOnSelectCausaleErogazione(body?: ControllaDatiRequest): __Observable<EsitoControllaDati> {
    return this.controllaDatiOnSelectCausaleErogazioneResponse(body).pipe(
      __map(_r => _r.body as EsitoControllaDati)
    );
  }

  /**
   * Finds erogazione by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getCausaliErogazioniResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<CodiceDescrizioneCausale>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione/causali`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<CodiceDescrizioneCausale>>;
      })
    );
  }
  /**
   * Finds erogazione by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getCausaliErogazioni(idProgetto?: number): __Observable<Array<CodiceDescrizioneCausale>> {
    return this.getCausaliErogazioniResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<CodiceDescrizioneCausale>)
    );
  }

  /**
   * Finds All Codici Direzione
   * @return successful operation
   */
  getAllCodiciDirezioneResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione/codiciDirezione`,
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
   * Finds All Codici Direzione
   * @return successful operation
   */
  getAllCodiciDirezione(): __Observable<Array<CodiceDescrizione>> {
    return this.getAllCodiciDirezioneResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds dati calcolati
   * @param body undefined
   * @return successful operation
   */
  getDatiCalcolatiResponse(body?: GetDatiCalcolatiRequest): __Observable<__StrictHttpResponse<DatiCalcolati>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/erogazione/datiCalcolati`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<DatiCalcolati>;
      })
    );
  }
  /**
   * Finds dati calcolati
   * @param body undefined
   * @return successful operation
   */
  getDatiCalcolati(body?: GetDatiCalcolatiRequest): __Observable<DatiCalcolati> {
    return this.getDatiCalcolatiResponse(body).pipe(
      __map(_r => _r.body as DatiCalcolati)
    );
  }

  /**
   * Finds dettaglioErogazione
   * @param params The `ErogazioneService.GetDettaglioErogazioneParams` containing the following parameters:
   *
   * - `idProgetto`:
   *
   * - `idErogazione`:
   *
   * @return successful operation
   */
  getDettaglioErogazioneResponse(params: ErogazioneService.GetDettaglioErogazioneParams): __Observable<__StrictHttpResponse<Erogazione>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    if (params.idErogazione != null) __params = __params.set('idErogazione', params.idErogazione.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione/dettaglioErogazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Erogazione>;
      })
    );
  }
  /**
   * Finds dettaglioErogazione
   * @param params The `ErogazioneService.GetDettaglioErogazioneParams` containing the following parameters:
   *
   * - `idProgetto`:
   *
   * - `idErogazione`:
   *
   * @return successful operation
   */
  getDettaglioErogazione(params: ErogazioneService.GetDettaglioErogazioneParams): __Observable<Erogazione> {
    return this.getDettaglioErogazioneResponse(params).pipe(
      __map(_r => _r.body as Erogazione)
    );
  }

  /**
   * Creates Erogazione
   * @param body undefined
   * @return successful operation
   */
  inserisciErogazioneResponse(body?: SalvaErogazioneRequest): __Observable<__StrictHttpResponse<EsitoErogazioneDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/erogazione/erogazione`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoErogazioneDTO>;
      })
    );
  }
  /**
   * Creates Erogazione
   * @param body undefined
   * @return successful operation
   */
  inserisciErogazione(body?: SalvaErogazioneRequest): __Observable<EsitoErogazioneDTO> {
    return this.inserisciErogazioneResponse(body).pipe(
      __map(_r => _r.body as EsitoErogazioneDTO)
    );
  }

  /**
   * Deletes Erogazione by idErogazione
   * @param body undefined
   * @return successful operation
   */
  modificaErogazioneResponse(body?: ModificaErogazioneRequest): __Observable<__StrictHttpResponse<EsitoOperazioni>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/restfacade/erogazione/erogazione`,
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
   * Deletes Erogazione by idErogazione
   * @param body undefined
   * @return successful operation
   */
  modificaErogazione(body?: ModificaErogazioneRequest): __Observable<EsitoOperazioni> {
    return this.modificaErogazioneResponse(body).pipe(
      __map(_r => _r.body as EsitoOperazioni)
    );
  }

  /**
   * Deletes Erogazione by idErogazione
   * @param idErogazione undefined
   * @return successful operation
   */
  eliminaErogazioneResponse(idErogazione?: number): __Observable<__StrictHttpResponse<EsitoOperazioni>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idErogazione != null) __params = __params.set('idErogazione', idErogazione.toString());
    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/restfacade/erogazione/erogazione`,
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
   * Deletes Erogazione by idErogazione
   * @param idErogazione undefined
   * @return successful operation
   */
  eliminaErogazione(idErogazione?: number): __Observable<EsitoOperazioni> {
    return this.eliminaErogazioneResponse(idErogazione).pipe(
      __map(_r => _r.body as EsitoOperazioni)
    );
  }

  /**
   * Finds All Modalita Agevolazione Conto Economico by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getAllModalitaAgevolazioneContoEconomicoResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione/modalitaAgevolazione`,
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
   * Finds All Modalita Agevolazione Conto Economico by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getAllModalitaAgevolazioneContoEconomico(idProgetto?: number): __Observable<Array<CodiceDescrizione>> {
    return this.getAllModalitaAgevolazioneContoEconomicoResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  getModalitaAgevolazioneDaVisualizzare(idModalitaAgevolazione: number):  Observable<any>{
    let url = this.rootUrl + '/restfacade/erogazione/getModalitaAgevolazioneDaVisualizzare';
    let params = new HttpParams().set("idModalitaAgevolazione", idModalitaAgevolazione.toString());
    return this.http.get<String>(url, { params: params });
  }

  /**
   * Finds All Modalita Erogazione
   * @return successful operation
   */
  getAllModalitaErogazioneResponse(): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione/modalitaErogazione`,
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
   * Finds All Modalita Erogazione
   * @return successful operation
   */
  getAllModalitaErogazione(): __Observable<Array<CodiceDescrizione>> {
    return this.getAllModalitaErogazioneResponse().pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds Dati Riepilogo Richiesta Erogazione per l'attivit?? avvio lavori/Richiesta erogazione acconto
   * @param params The `ErogazioneService.GetDatiRiepilogoRichiestaErogazioneParams` containing the following parameters:
   *
   * - `idProgetto`:
   *
   * - `codCausale`:
   *
   * @return successful operation
   */
  getDatiRiepilogoRichiestaErogazioneResponse(params: ErogazioneService.GetDatiRiepilogoRichiestaErogazioneParams): __Observable<__StrictHttpResponse<EsitoRichiestaErogazioneDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.idProgetto != null) __params = __params.set('idProgetto', params.idProgetto.toString());
    if (params.codCausale != null) __params = __params.set('codCausale', params.codCausale.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione/richiesta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoRichiestaErogazioneDTO>;
      })
    );
  }
  /**
   * Finds Dati Riepilogo Richiesta Erogazione per l'attivit?? avvio lavori/Richiesta erogazione acconto
   * @param params The `ErogazioneService.GetDatiRiepilogoRichiestaErogazioneParams` containing the following parameters:
   *
   * - `idProgetto`:
   *
   * - `codCausale`:
   *
   * @return successful operation
   */
  getDatiRiepilogoRichiestaErogazione(params: ErogazioneService.GetDatiRiepilogoRichiestaErogazioneParams): __Observable<EsitoRichiestaErogazioneDTO> {
    return this.getDatiRiepilogoRichiestaErogazioneResponse(params).pipe(
      __map(_r => _r.body as EsitoRichiestaErogazioneDTO)
    );
  }

  /**
   * crea richiesta erogazione
   * @param body undefined
   * @return successful operation
   */
  creaRichiestaErogazioneResponse(body?: CreaRichiestaErogazioneRequest): __Observable<__StrictHttpResponse<EsitoReportRichiestaErogazioneDTO>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = body;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/restfacade/erogazione/richiesta`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<EsitoReportRichiestaErogazioneDTO>;
      })
    );
  }
  /**
   * crea richiesta erogazione
   * @param body undefined
   * @return successful operation
   */
  creaRichiestaErogazione(body?: CreaRichiestaErogazioneRequest): __Observable<EsitoReportRichiestaErogazioneDTO> {
    return this.creaRichiestaErogazioneResponse(body).pipe(
      __map(_r => _r.body as EsitoReportRichiestaErogazioneDTO)
    );
  }

  /**
   * Finds delegati by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getDelegatiResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<CodiceDescrizione>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione/richiesta/delegati`,
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
   * Finds delegati by idProgetto
   * @param idProgetto undefined
   * @return successful operation
   */
  getDelegati(idProgetto?: number): __Observable<Array<CodiceDescrizione>> {
    return this.getDelegatiResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<CodiceDescrizione>)
    );
  }

  /**
   * Finds Rappresentanti Legali per l'attivit?? avvio lavori/Richiesta erogazione acconto
   * @param idProgetto undefined
   * @return successful operation
   */
  getRappresentantiLegaliResponse(idProgetto?: number): __Observable<__StrictHttpResponse<Array<RappresentanteLegaleDTO>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (idProgetto != null) __params = __params.set('idProgetto', idProgetto.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/restfacade/erogazione/richiesta/rappresentantiLegali`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<RappresentanteLegaleDTO>>;
      })
    );
  }
  /**
   * Finds Rappresentanti Legali per l'attivit?? avvio lavori/Richiesta erogazione acconto
   * @param idProgetto undefined
   * @return successful operation
   */
  getRappresentantiLegali(idProgetto?: number): __Observable<Array<RappresentanteLegaleDTO>> {
    return this.getRappresentantiLegaliResponse(idProgetto).pipe(
      __map(_r => _r.body as Array<RappresentanteLegaleDTO>)
    );
  }
}

module ErogazioneService {

  /**
   * Parameters for getErogazione
   */
  export interface GetErogazioneParams {
    idU?: number;
    idSoggettoBeneficiario?: number;
    idProgetto?: number;
  }

  /**
   * Parameters for getDettaglioErogazione
   */
  export interface GetDettaglioErogazioneParams {
    idProgetto?: number;
    idErogazione?: number;
  }

  /**
   * Parameters for getDatiRiepilogoRichiestaErogazione
   */
  export interface GetDatiRiepilogoRichiestaErogazioneParams {
    idProgetto?: number;
    codCausale?: string;
  }
}

export { ErogazioneService }
