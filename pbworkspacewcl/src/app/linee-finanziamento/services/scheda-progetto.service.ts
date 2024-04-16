/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { BeneficiarioCspDTO } from "../commons/dto/beneficiario-csp-dto";
import { CodiceDescrizione } from "../commons/dto/codice-descrizione";
import { EsitoDTO } from "../commons/dto/esito-dto";
import { EsitoSoggettoDTO } from "../commons/dto/esito-soggetto-dto";
import { InizializzaComboDTO } from "../commons/dto/inizializza-combo-dto";
import { InizializzaSchedaProgettoDTO } from "../commons/dto/inizializza-scheda-progetto-dto";
import { RapprLegaleCspDTO } from "../commons/dto/rappr-legale-csp-dto";
import { SalvaSchedaProgettoRequest } from "../commons/requests/salva-scheda-progetto-request";

@Injectable()
export class SchedaProgettoService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  inizializzaSchedaProgetto(progrBandoLineaIntervento: number, idProgetto: number, idSoggetto: number, codiceRuolo: string): Observable<InizializzaSchedaProgettoDTO> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/inizializzaSchedaProgetto";
    let params = new HttpParams()
      .set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString())
      .set("idSoggetto", idSoggetto.toString())
      .set("codiceRuolo", codiceRuolo);
    if (idProgetto) {
      params = params.set("idProgetto", idProgetto.toString());
    }
    return this.http.get<InizializzaSchedaProgettoDTO>(url, { params: params });
  }

  inizializzaCombo(progrBandoLineaIntervento: number): Observable<InizializzaComboDTO> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/inizializzaCombo";
    let params = new HttpParams().set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
    return this.http.get<InizializzaComboDTO>(url, { params: params });
  }

  popolaComboAttivitaAteco(idSettoreAttivita: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboAttivitaAteco";
    let params = new HttpParams().set("idSettoreAttivita", idSettoreAttivita.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboObiettivoGeneraleQsn(idListaPrioritaQsn: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboObiettivoGeneraleQsn";
    let params = new HttpParams().set("idListaPrioritaQsn", idListaPrioritaQsn.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboObiettivoSpecificoQsn(idObiettivoGenerale: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboObiettivoSpecificoQsn";
    let params = new HttpParams().set("idObiettivoGenerale", idObiettivoGenerale.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboClassificazioneRA(idObiettivoTematico: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboClassificazioneRA";
    let params = new HttpParams().set("idObiettivoTematico", idObiettivoTematico.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboSottoSettoreCipe(idSettoreCipe: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboSottoSettoreCipe";
    let params = new HttpParams().set("idSettoreCipe", idSettoreCipe.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboCategoriaCipe(idSottoSettoreCipe: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboCategoriaCipe";
    let params = new HttpParams().set("idSottoSettoreCipe", idSottoSettoreCipe.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboTipologiaCipe(idNaturaCipe: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboTipologiaCipe";
    let params = new HttpParams().set("idNaturaCipe", idNaturaCipe.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboRegione(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboRegione";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  popolaComboRegioneNascita(): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboRegioneNascita";
    return this.http.get<Array<CodiceDescrizione>>(url);
  }

  popolaComboProvincia(idRegione: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboProvincia";
    let params = new HttpParams().set("idRegione", idRegione.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboProvinciaNascita(idRegione: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboProvinciaNascita";
    let params = new HttpParams().set("idRegione", idRegione.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboComuneEstero(idNazione: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboComuneEstero";
    let params = new HttpParams().set("idNazione", idNazione.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboComuneEsteroNascita(idNazione: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboComuneEsteroNascita";
    let params = new HttpParams().set("idNazione", idNazione.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboComuneItaliano(idProvincia: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboComuneItaliano";
    let params = new HttpParams().set("idProvincia", idProvincia.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboComuneItalianoNascita(idProvincia: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboComuneItalianoNascita";
    let params = new HttpParams().set("idProvincia", idProvincia.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboDenominazioneSettori(idEnte: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboDenominazioneSettori";
    let params = new HttpParams().set("idEnte", idEnte.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  popolaComboDenominazioneEnteDipUni(idAteneo: number): Observable<Array<CodiceDescrizione>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/popolaComboDenominazioneEnteDipUni";
    let params = new HttpParams().set("idAteneo", idAteneo.toString());
    return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
  }

  ricercaBeneficiarioCsp(codiceFiscale: string): Observable<Array<BeneficiarioCspDTO>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/ricercaBeneficiarioCsp";
    let params = new HttpParams().set("codiceFiscale", codiceFiscale);
    return this.http.get<Array<BeneficiarioCspDTO>>(url, { params: params });
  }

  ricercaRapprLegaleCsp(codiceFiscale: string): Observable<Array<RapprLegaleCspDTO>> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/ricercaRapprLegaleCsp";
    let params = new HttpParams().set("codiceFiscale", codiceFiscale);
    return this.http.get<Array<RapprLegaleCspDTO>>(url, { params: params });
  }

  verificaNumeroDomandaUnico(numDomanda: string, idProgetto: number, idBandoLinea: number): Observable<boolean> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/verificaNumeroDomandaUnico";
    let params = new HttpParams().set("idBandoLinea", idBandoLinea.toString());
    if (idProgetto) {
      params = params.set("idProgetto", idProgetto.toString());
    }
    if (numDomanda) {
      params = params.set("numDomanda", numDomanda);
    }
    return this.http.get<boolean>(url, { params: params });
  }

  verificaCupUnico(cup: string, idProgetto: number): Observable<boolean> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/verificaCupUnico";
    let params = new HttpParams();
    if (idProgetto) {
      params = params.set("idProgetto", idProgetto.toString());
    }
    if (cup) {
      params = params.set("cup", cup);
    }
    return this.http.get<boolean>(url, { params: params });
  }

  salvaSchedaProgetto(request: SalvaSchedaProgettoRequest): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/salvaSchedaProgetto";
    return this.http.post<EsitoDTO>(url, request);
  }

  caricaInfoDirezioneRegionale(idEnteCompetenza: number): Observable<EsitoSoggettoDTO> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/caricaInfoDirezioneRegionale";
    let params = new HttpParams().set("idEnteCompetenza", idEnteCompetenza.toString());
    return this.http.get<EsitoSoggettoDTO>(url, { params: params });
  }

  caricaInfoPubblicaAmministrazione(idEnteCompetenza: number): Observable<EsitoSoggettoDTO> {
    let url = this.configService.getApiURL() + "/restfacade/schedaProgetto/caricaInfoPubblicaAmministrazione";
    let params = new HttpParams().set("idEnteCompetenza", idEnteCompetenza.toString());
    return this.http.get<EsitoSoggettoDTO>(url, { params: params });
  }
}