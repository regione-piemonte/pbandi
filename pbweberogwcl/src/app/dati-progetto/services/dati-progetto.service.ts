/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { CodiceDescrizione } from "src/app/disimpegni/commons/models/codice-descrizione";
import { EsitoAssociaFilesDTO } from "src/app/erogazione/commons/dto/esito-associa-files-dto";
import { AssociaFilesRequest } from "src/app/erogazione/commons/requests/associa-files-request";
import { EsitoOperazioni } from "src/shared/api/models/esito-operazioni";
import { DatiAggiuntiviDTO } from "../commons/dto/dati-aggiuntivi-dto";
import { DatiProgettoDTO } from "../commons/dto/dati-progetto-dto";
import { DettaglioSoggettoBeneficiarioDTO } from "../commons/dto/dettaglio-soggetto-beneficiario-dto";
import { DettaglioSoggettoProgettoDTO } from "../commons/dto/dettaglio-soggetto-progetto-dto";
import { EmailBeneficiarioPF } from "../commons/dto/email-beneficiario-pf";
import { FileDTO } from "../commons/dto/file-dto";
import { SedeProgettoDTO } from "../commons/dto/sede-progetto-dto";
import { SoggettoProgettoDTO } from "../commons/dto/soggetto-progetto-dto";
import { Comune } from "../commons/models/comune";
import { Recapiti } from "../commons/models/recapiti";
import { RequestCambiaStatoSoggettoProgetto } from "../commons/models/request-cambia-stato-soggetto-progetto";
import { RequestSalvaCup } from "../commons/models/request-salva-cup";
import { RequestSalvaRecapito } from "../commons/models/request-salva-recapito";
import { RequestSalvaTitoloProgetto } from "../commons/models/request-salva-titolo-progetto";
import { SedeProgetto } from "../commons/models/sede-progetto";
import { InserisciSedeInterventoRequest } from "../commons/requests/inserisci-sede-intervento-request";
import { ModificaSedeInterventoRequest } from "../commons/requests/modifica-sede-intervento-request";
import { SalvaDatiProgettoRequest } from "../commons/requests/salva-dati-progetto-request";
import { DatiSif } from "../commons/dto/dati-sif";

@Injectable()
export class DatiProgettoService {

  private _tabSoggetti: boolean;

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  get tabSoggetti() {
    return this._tabSoggetti;
  }

  set tabSoggetti(value: boolean) {
    this._tabSoggetti = value;
  }

  isLineaPORFESR1420(idProgetto: number): Observable<boolean> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/lineaInter";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<boolean>(url, { params: params });
  }

  getDatiProgetto(idProgetto: number): Observable<DatiProgettoDTO> {
    let url = this.configService.getApiURL() + `/restfacade/datiProgetto/${idProgetto}`;
    return this.http.get<DatiProgettoDTO>(url);
  }

  salvaDatiProgetto(request: SalvaDatiProgettoRequest): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/";
    return this.http.post<EsitoOperazioni>(url, request);
  }

  /*  DATI SIF  */
  
  salvaDatiSif(idProgetto:number, request: DatiSif): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + `/restfacade/datiProgetto/salvaDatiSif/${idProgetto}`;
    return this.http.post<EsitoOperazioni>(url, request);
  }

  /*  DATI AGGIUNTIVI  */

  getDatiAggiuntivi(idProgetto: number): Observable<DatiAggiuntiviDTO> {
    let url = this.configService.getApiURL() + `/restfacade/datiProgetto/datiAggiuntivi/${idProgetto}`;
    return this.http.get<DatiAggiuntiviDTO>(url);
  }

  /*  ALLEGATI  */

  getFilesAssociatedProgetto(idProgetto: number): Observable<Array<FileDTO>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/allegati";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<FileDTO>>(url, { params: params });
  }

  disassociaAllegatoProgetto(idDocumentoIndex: number, idProgetto: number): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/disassociaAllegatoProgetto";
    let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString()).set("idProgetto", idProgetto.toString());
    return this.http.get<EsitoOperazioni>(url, { params: params });
  }

  associaAllegatiAProgetto(request: AssociaFilesRequest): Observable<EsitoAssociaFilesDTO> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/associaAllegatiAProgetto";
    return this.http.post<EsitoAssociaFilesDTO>(url, request);
  }

  /*  SEDI  */

  getAllSediProgetto(idProgetto: number, codiceRuolo: string): Observable<Array<SedeProgetto>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/sedi";
    let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("codiceRuolo", codiceRuolo);
    return this.http.get<Array<SedeProgetto>>(url, { params: params });
  }

  getDettaglioSedeProgetto(idSede: number, idProgetto: number): Observable<SedeProgettoDTO> {
    let url = this.configService.getApiURL() + `/restfacade/datiProgetto/sedi/${idSede}/${idProgetto}/dettaglio`;
    return this.http.get<SedeProgettoDTO>(url);
  }

  modificaSedeIntervento(request: ModificaSedeInterventoRequest): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/sede";
    return this.http.put<EsitoOperazioni>(url, request);
  }

  inserisciSedeInterventoProgetto(request: InserisciSedeInterventoRequest): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/sede";
    return this.http.post<EsitoOperazioni>(url, request);
  }

  cancellaSedeInterventoProgetto(progrSoggettoProgettoSede: number): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + `/restfacade/datiProgetto/sedi/${progrSoggettoProgettoSede}`;
    return this.http.delete<EsitoOperazioni>(url);
  }

  /*  SOGGETTI  */

  getSoggettiProgetto(idU: number, idProgetto: number, codiceRuolo: string): Observable<Array<SoggettoProgettoDTO>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/soggetti";
    let params = new HttpParams()
      .set("idU", idU.toString())
      .set("idProgetto", idProgetto.toString())
      .set("codiceRuolo", codiceRuolo);
    return this.http.get<Array<SoggettoProgettoDTO>>(url, { params: params });
  }

  richiestaAccessoNegata(idU: number, progrSoggettoProgetto: number, progrSoggettiCorrelati: number): Observable<number> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/richiestaAccessoNegata";
    let params = new HttpParams()
      .set("idU", idU.toString())
      .set("progrSoggettoProgetto", progrSoggettoProgetto.toString());
    if (progrSoggettiCorrelati) {
      params = params.set("progrSoggettiCorrelati", progrSoggettiCorrelati.toString())
    }
    return this.http.get<number>(url, { params: params });
  }

  cambiaStatoSoggettoProgetto(idU: number, request: RequestCambiaStatoSoggettoProgetto): Observable<boolean> {
    console.log("dati-progetto cambiaStatoSoggettoProgetto request=" + request);
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/soggetto/stato";
    let params = new HttpParams().set("idU", idU.toString());
    return this.http.put<boolean>(url, request, { params: params });
  }

  disabilitaSoggettoPermanentemente(idSoggetto: number, idProgetto: number): Observable<number> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/soggetto/disabilitatoPermanentemente";
    let params = new HttpParams().set("idSoggetto", idSoggetto.toString()).set("idProgetto", idProgetto.toString());
    return this.http.get<number>(url, { params: params });
  }

  getDettaglioSoggettoProgetto(idU: number, progrSoggettoProgetto: number, idTipoSoggettoCorrelato: number, progrSoggettiCorrelati: number, codiceFiscale: string)
    : Observable<DettaglioSoggettoProgettoDTO> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/soggetti/dettaglio";
    let params = new HttpParams()
      .set("idU", idU.toString())
      .set("progrSoggettoProgetto", progrSoggettoProgetto.toString())
      .set("codiceFiscale", codiceFiscale);
    if (idTipoSoggettoCorrelato) {
      params = params.set("idTipoSoggettoCorrelato", idTipoSoggettoCorrelato.toString())
    }
    if (progrSoggettiCorrelati) {
      params = params.set("progrSoggettiCorrelati", progrSoggettiCorrelati.toString())
    }
    return this.http.get<DettaglioSoggettoProgettoDTO>(url, { params: params });
  }

  getDettaglioSoggettoBeneficiario(idU: number, idProgetto: number, codiceRuolo: string): Observable<DettaglioSoggettoBeneficiarioDTO> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/soggettiBeneficiari/dettaglio";
    let params = new HttpParams()
      .set("idU", idU.toString())
      .set("idProgetto", idProgetto.toString())
      .set("codiceRuolo", codiceRuolo);
    return this.http.get<DettaglioSoggettoBeneficiarioDTO>(url, { params: params });
  }

  getTipiSoggettiCorrelati(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/ruoli";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getSedeLegale(idProgetto: number) {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/sedeLegale";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get(url, { params: params, responseType: 'text' });
  }

  getRecapito(idProgetto: number): Observable<Recapiti> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/recapito";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Recapiti>(url, { params: params });
  }

  salvaRecapito(idU: number, request: RequestSalvaRecapito): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/recapito";
    let params = new HttpParams().set("idU", idU.toString());
    return this.http.put<EsitoOperazioni>(url, request, { params: params });
  }

  salvaRecapitoPec(idU: number, request: RequestSalvaRecapito): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/recapitoPec";
    let params = new HttpParams().set("idU", idU.toString());
    return this.http.put<EsitoOperazioni>(url, request, { params: params });
  }

  saveCup(request: RequestSalvaCup): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/cup";
    return this.http.put<EsitoOperazioni>(url, request);
  }

  saveTitoloProgetto(request: RequestSalvaTitoloProgetto): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/titoloProgetto";
    return this.http.put<EsitoOperazioni>(url, request);
  }

  salvaDettaglioSoggettoProgetto(idU: number, request: DettaglioSoggettoProgettoDTO): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/soggetti/dettaglio";
    let params = new HttpParams().set("idU", idU.toString());
    return this.http.put<EsitoOperazioni>(url, request, { params: params });
  }

  salvaSoggettoBeneficiario(request: DettaglioSoggettoBeneficiarioDTO): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/soggettiBeneficiari/dettaglio";
    return this.http.put<EsitoOperazioni>(url, request);
  }

  isFinpiemonte(idProgetto: number): Observable<boolean> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/isFinpiemonte";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<boolean>(url, { params: params });
  }

  leggiEmailBeneficiarioPF(idProgetto: number, idSoggettoBen: number): Observable<EmailBeneficiarioPF> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/leggiEmailBeneficiarioPF";
    let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idSoggettoBen", idSoggettoBen.toString());
    return this.http.get<EmailBeneficiarioPF>(url, { params: params });
  }

  salvaEmailBeneficiarioPF(idProgetto: number, idSoggettoBen: number, email: string): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/salvaEmailBeneficiarioPF";
    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idSoggettoBen", idSoggettoBen.toString())
      .set("email", email);
    return this.http.get<EsitoOperazioni>(url, { params: params });
  }

  /*  COMBO */

  getSettoreAttivita(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/settoreAttivita";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getAttivitaAteco(idSettoreAttivita: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/attivitaAteco";
    let params = new HttpParams().set("idSettoreAttivita", idSettoreAttivita.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getTipoOperazione(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/tipoOperazione";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getStrumentoAttuativo(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/strumentoAttuativo";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getSettoreCpt(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/settoreCpt";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getTemaPrioritario(idProgetto: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/temaPrioritario";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getIndicatoreRisultatoProgramma(idProgetto: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/indicatoreRisultatoProgramma";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getIndicatoreQsn(idProgetto: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/indicatoreQsn";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getTipoAiuto(idProgetto: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/tipoAiuto";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getStrumentoProgrammazione(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/tipoStrumentoProgrammazione";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getDimensioneTerritoriale(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/dimensioneTerritoriale";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getProgettoComplesso(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/ProgettoComplesso";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getSettoreCipe(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/settoreCipe";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getSottoSettoreCipe(idSettoreCipe: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/sottoSettoreCipe";
    let params = new HttpParams().set("idSettoreCipe", idSettoreCipe.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getCategoriaCipe(idSottoSettoreCipe: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/categoriaCipe";
    let params = new HttpParams().set("idSottoSettoreCipe", idSottoSettoreCipe.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getNaturaCipe(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/naturaCipe";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getTipologiaCipe(idNaturaCipe: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/tipologiaCipe";
    let params = new HttpParams().set("idNaturaCipe", idNaturaCipe.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getTipoProceduraOriginaria(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/tipoProceduraOriginaria";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getNazioni(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/nazioni";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getRegioni(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/regioni";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getProvince(idRegione?: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/province";
    let params = new HttpParams();
    if (idRegione) {
      params = params.set("idRegione", idRegione.toString());
    }
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getComuniEsterni(idNazione: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/comuniEsterni";
    let params = new HttpParams().set("idNazione", idNazione.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getComuni(idProvincia: number): Observable<Array<Comune>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/comuni";
    let params = new HttpParams().set("idProvincia", idProvincia.toString());
    return this.http.get<Array<Comune>>(url, { params: params });
  }

  /*  COMBO 1420  */

  getObiettivoTematico(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/1420/obiettivoTematico";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getClassificazioneRA(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/1420/classificazioneRA";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getGrandeProgetto(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/1420/grandeProgetto";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  getPrioritaQsn(idLineaInterventoAsse: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/1420/prioritaQsn";
    let params = new HttpParams().set("idLineaInterventoAsse", idLineaInterventoAsse.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getObiettivoGeneraleQsn(idPriorityQsn: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/1420/obiettivoGeneraleQsn";
    let params = new HttpParams().set("idPriorityQsn", idPriorityQsn.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getObiettivoSpecificoQsn(idObiettivoGenerale: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/1420/obiettivoSpecificoQsn";
    let params = new HttpParams().set("idObiettivoGenerale", idObiettivoGenerale.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  getDimensioneTerritoriale1420(idProgetto: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/datiProgetto/1420/dimensioneTerritoriale";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }
}
