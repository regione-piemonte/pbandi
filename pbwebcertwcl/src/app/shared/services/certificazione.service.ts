/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { CreaIntermediaFinaleRequest } from 'src/app/gestione-proposte/commons/requests/crea-intermedia-finale-request';
import { PropostaCertificazioneDTO } from 'src/app/shared/commons/dto/proposta-certificazione-dto';
import { BeneficiarioDTO } from '../commons/dto/beneficiario-dto';
import { CodiceDescrizione } from '../commons/dto/codice-descrizione';
import { DocumentoCertificazioneDTO } from '../commons/dto/documento-certificazione-dto';
import { EsitoGenerazioneReportDTO } from '../../ricerca-documenti/commons/dto/esito-generazione-report-dto';
import { EsitoOperazioni } from '../commons/dto/esito-operazioni';
import { LineaInterventoDTO } from '../commons/dto/linea-intervento-dto';
import { ProgettoDTO } from '../commons/dto/progetto-dto';
import { CancellaAllegatiRequest } from '../commons/requests/cancella-allegati-request';
import { ModificaAllegatiRequest } from '../commons/requests/modifica-allegato-request';
import { PropostaCertifLineaRequest } from '../commons/requests/proposta-certif-linea-request';
import { PropostaCertificazioneAllegatiRequest } from '../commons/requests/proposta-certificazione-allegati-request';
import { ProposteCertificazioneRequest } from '../commons/requests/proposte-certificazione-request';
import { map } from 'rxjs/operators';
import { FiltroRicercaDocumentoDTO } from '../../ricerca-documenti/commons/dto/filtro-ricerca-documento-dto';
import { ReportCampionamentoDTO } from 'src/app/campionamento/commons/dto/report-campionamento-dto';
import { StatoPropostaDTO } from 'src/app/gestione-proposte/commons/dto/stato-proposta-dto';
import { AggiornaStatoRequest } from 'src/app/gestione-proposte/commons/requests/aggiorna-stato-request';
import { BandoLineaDTO } from 'src/app/gestione-proposte/commons/dto/bando-linea-dto';
import { GestisciPropostaRequest } from 'src/app/gestione-proposte/commons/requests/gestisci-proposta-request';
import { ProgettoNuovaCertificazioneDTO } from 'src/app/gestione-proposte/commons/dto/progetto-nuova-certificazione-dto';
import { AggiornaImportoNettoRequest } from 'src/app/gestione-proposte/commons/requests/aggiorna-importo-netto-request';
import { GestisciPropostaIntermediaFinaleRequest } from 'src/app/gestione-proposte/commons/requests/gestisci-proposta-finale-request';
import { ProgettoCertificazioneIntermediaFinaleDTO } from 'src/app/gestione-proposte/commons/dto/progetto-certificazione-intermedia-finale-dto';
import { AggiornaDatiIntermediaFinaleRequest } from 'src/app/gestione-proposte/commons/requests/aggiorna-dati-intermedia-finale-request';
import { CreaPropostaRequest } from 'src/app/crea-proposta/commons/requests/crea-proposta-request';
import { PropostaProgettoDTO } from 'src/app/crea-proposta/commons/dto/proposta-progetto-dto';
import { AmmettiESospendiProgettiRequest } from 'src/app/crea-proposta/commons/requests/ammetti-e-sospendi-progetti-request';
import { AccodaPropostaRequest } from 'src/app/crea-proposta/commons/requests/accoda-proposta-request';
import { LanciaPropostaRequest } from 'src/app/crea-proposta/commons/requests/lancia-proposta-request';
import { InviaReportRequest } from 'src/app/gestione-proposte/commons/requests/invia-report-request';

@Injectable()
export class CertificazioneService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        @Inject(DOCUMENT) private document: any
    ) { }

    /**************************** SERVIZI DI CHECKLIST E DICHIARAZIONE FINALE ******************************/
    /******************************************************************************************************/

    findProposteCertificazione(request: ProposteCertificazioneRequest): Observable<Array<PropostaCertificazioneDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposte';
        return this.http.post<Array<PropostaCertificazioneDTO>>(url, request);
    }

    findProposteAggiornamentoStatoCertificazione(request: ProposteCertificazioneRequest): Observable<Array<PropostaCertificazioneDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposte/aggiornamentoStato';
        return this.http.post<Array<PropostaCertificazioneDTO>>(url, request);
    }

    getLineeDiInterventoFromIdLinee(request: PropostaCertifLineaRequest): Observable<Array<LineaInterventoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposte/lineeIntervento';
        return this.http.post<Array<LineaInterventoDTO>>(url, request);
    }

    getProposta(idProposta: number): Observable<PropostaCertificazioneDTO> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta';
        let params = new HttpParams().set("idProposta", idProposta.toString());
        return this.http.get<PropostaCertificazioneDTO>(url, { params: params });
    }

    getAllegatiPropostaCertificazione(request: PropostaCertificazioneAllegatiRequest): Observable<Array<DocumentoCertificazioneDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/allegati';
        return this.http.post<Array<DocumentoCertificazioneDTO>>(url, request);
    }

    cancellaAllegati(request: CancellaAllegatiRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/allegati/deleteList';
        return this.http.post<EsitoOperazioni>(url, request);
    }

    allegaFileProposta(idProposta: number, idProgetto: number, file: File, tipoDocumento: string): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/allegato`;
        let formData = new FormData()
        formData.append("file", file, file.name);
        formData.append("tipoDocumento", tipoDocumento);
        if (idProgetto) {
            formData.append("idProgetto", idProgetto.toString());
        }
        return this.http.post<EsitoOperazioni>(url, formData);
    }

    modificaAllegati(idProposta: number, request: ModificaAllegatiRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/allegato`;
        return this.http.put<EsitoOperazioni>(url, request);
    }

    getAttivitaLineaIntervento(idLineaIntervento: number): Observable<Array<LineaInterventoDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposta/${idLineaIntervento}/attivita`;
        return this.http.get<Array<LineaInterventoDTO>>(url);
    }

    getAllProgetti(idProposta: number, idLineaIntervento: number, nomeBandoLinea: string, idBeneficiario: number, denominazioneBeneficiario: string): Observable<Array<ProgettoDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/progetti`;
        let params = new HttpParams();
        if (idLineaIntervento) {
            params = params.set("idLineaIntervento", idLineaIntervento.toString());
        }
        if (nomeBandoLinea) {
            params = params.set("nomeBandoLinea", nomeBandoLinea);
        }
        if (idBeneficiario) {
            params = params.set("idBeneficiario", idBeneficiario.toString());
        }
        if (denominazioneBeneficiario) {
            params = params.set("denominazioneBeneficiario", denominazioneBeneficiario);
        }
        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    getDettaglioProposta(idDettProposta: number): Observable<PropostaCertificazioneDTO> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idDettProposta}/dettaglio`;
        return this.http.get<PropostaCertificazioneDTO>(url);
    }

    getAllBeneficiari(idProposta: number, idLineaIntervento: number, nomeBandoLinea: string): Observable<Array<BeneficiarioDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/beneficiari`;
        let params = new HttpParams();
        if (idLineaIntervento) {
            params = params.set("idLineaIntervento", idLineaIntervento.toString());
        }
        if (nomeBandoLinea) {
            params = params.set("nomeBandoLinea", nomeBandoLinea);
        }
        return this.http.get<Array<BeneficiarioDTO>>(url, { params: params });
    }

    getProgettiProposta(idProposta: number, idProgetto: number, idLineaIntervento: number, idBeneficiario: number): Observable<Array<PropostaCertificazioneDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/progetti/${idProposta}/proposta`;
        let params = new HttpParams();
        if (idProgetto != null) {
            params = params.set("idProposta", idProposta.toString());
        }
        if (idLineaIntervento != null) {
            params = params.set("idLineaIntervento", idLineaIntervento.toString())
        }
        if (idBeneficiario != null) {
            params = params.set("idBeneficiario", idBeneficiario.toString());
        }
        return this.http.get<Array<PropostaCertificazioneDTO>>(url, { params: params });
    }

    /****************************************** SERVIZI DI GESTIONE PROPOSTE *******************************/
    /******************************************************************************************************/

    creaIntermediaFinale(request: CreaIntermediaFinaleRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/intermediaFinale';
        return this.http.post<EsitoOperazioni>(url, request);
    }

    getAllBandoLineaIntermediaFinale(idProposta: number): Observable<Array<BandoLineaDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/bandoLinea/intermediaFinale`;
        return this.http.get<Array<BandoLineaDTO>>(url);
    }

    getAllBeneficiariIntermediaFinale(idProposta: number, nomeBandoLinea: string): Observable<Array<BeneficiarioDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/beneficiari/intermediaFinale`;
        let params = new HttpParams();
        if (nomeBandoLinea != null) {
            params = params.set("nomeBandoLinea", nomeBandoLinea);
        }
        return this.http.get<Array<BeneficiarioDTO>>(url, { params: params });
    }

    getAllProgettiIntermediaFinale(idProposta: number, nomeBandoLinea: string, denominazioneBeneficiario: string): Observable<Array<ProgettoDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/progetti/intermediaFinale`;
        let params = new HttpParams();
        if (nomeBandoLinea != null) {
            params = params.set("nomeBandoLinea", nomeBandoLinea);
        }
        if (denominazioneBeneficiario != null) {
            params = params.set("denominazioneBeneficiario", denominazioneBeneficiario);
        }
        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    getGestisciPropostaIntermediaFinale(idProposta: number, request: GestisciPropostaIntermediaFinaleRequest): Observable<Array<ProgettoCertificazioneIntermediaFinaleDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/progettiDaGestire/intermediaFinale`;
        return this.http.post<Array<ProgettoCertificazioneIntermediaFinaleDTO>>(url, request);
    }

    modificaProgettiIntermediaFinale(request: AggiornaDatiIntermediaFinaleRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/aggiornamentoProgetti/intermediaFinale';
        return this.http.put<EsitoOperazioni>(url, request);
    }

    getStatiSelezionabili(): Observable<Array<StatoPropostaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/statiSelezionabili';
        return this.http.get<Array<StatoPropostaDTO>>(url);
    }

    aggiornaStatoProposta(request: AggiornaStatoRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/aggiornamentoStato';
        return this.http.put<EsitoOperazioni>(url, request);
    }

    getBandoLineaDaProposta(idProposta: number): Observable<Array<BandoLineaDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/bandoLinea`;
        return this.http.get<Array<BandoLineaDTO>>(url);
    }

    getGestisciProposta(idProposta: number, request: GestisciPropostaRequest): Observable<Array<ProgettoNuovaCertificazioneDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/progettiDaGestire`;
        return this.http.post<Array<ProgettoNuovaCertificazioneDTO>>(url, request);
    }

    aggiornaImportoNetto(request: AggiornaImportoNettoRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/aggiornamentoImporto';
        return this.http.put<EsitoOperazioni>(url, request);
    }

    checkProceduraAggiornamentoTerminata(): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/procedura/aggiornamentoTerminata';
        return this.http.put<EsitoOperazioni>(url, { body: null });
    }

    chiusuraContiPropostaIntermediaFinale(idProposta: number): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/chiusuraConti`;
        return this.http.put<EsitoOperazioni>(url, { body: null });
    }

    inviaReportPostGestione(request: InviaReportRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/report';
        return this.http.post<EsitoOperazioni>(url, request);
    }

    aggiornaDatiIntermediaFinale(idProposta: number) {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/dati`;
        this.http.put(url, { body: null }).subscribe(data => { }, err => { });
        //non ho bisogno del valore di ritorno di questo servizio
        //non devo mostare l'errore di timeout in quanto il servizio è molto lungo
    }

    /****************************************** SERVIZI DI CAMPIONAMENTO ***********************************/
    /******************************************************************************************************/

    getAnniContabili(): Observable<Array<CodiceDescrizione>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/campionamento/annoContabile';
        return this.http.get<Array<CodiceDescrizione>>(url);
    }

    getLineeInterventoNormative(isConsultazione: boolean): Observable<Array<CodiceDescrizione>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/campionamento/normative';
        let params = new HttpParams().set("isConsultazione", isConsultazione.toString());
        return this.http.get<Array<CodiceDescrizione>>(url, { params: params });
    }

    eseguiEstrazioneCampionamento(idNormativa: number): Observable<EsitoGenerazioneReportDTO> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/campionamento/normative/${idNormativa}/estrazione`;
        return this.http.get<EsitoGenerazioneReportDTO>(url);
    }

    getElencoReportCampionamento(idNormativa: string, idAnnoContabile: string): Observable<Array<ReportCampionamentoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/campionamento/normative/elencoReport';
        let params = new HttpParams();
        if (idNormativa) {
            params = params.set("idNormativa", idNormativa);
        }
        if (idAnnoContabile) {
            params = params.set("idAnnoContabile", idAnnoContabile);
        }
        return this.http.get<Array<ReportCampionamentoDTO>>(url, { params: params });
    }

    getContenutoDocumentoById(idDocumentoIndex: number) {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/documenti/${idDocumentoIndex}/contenuto`;
        return this.http.get(url, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res]);
            }));
    }

    /***************************************** SERVIZI DI RICERCA DOCUMENTI*********************************/
    /******************************************************************************************************/

    propostePerRicercaDocumenti(request: ProposteCertificazioneRequest): Observable<Array<PropostaCertificazioneDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/propostePerRicercaDocumenti';
        return this.http.post<Array<PropostaCertificazioneDTO>>(url, request);
    }

    ricercaDocumenti(filtro: FiltroRicercaDocumentoDTO): Observable<Array<DocumentoCertificazioneDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/documenti/ricerca';
        return this.http.post<Array<DocumentoCertificazioneDTO>>(url, filtro);
    }

    /********************************* SERVIZI DI CREA PROPOSTA CERTIFICAZIONE*****************************/
    /******************************************************************************************************/

    creaAnteprimaPropostaCertificazione(request: CreaPropostaRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/anteprima';
        return this.http.post<EsitoOperazioni>(url, request);
    }

    getAllBandoLineaPerAnteprima(idProposta: number): Observable<Array<BandoLineaDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/bandoLinea/anteprima`;
        return this.http.get<Array<BandoLineaDTO>>(url);
    }

    getAllBeneficiariPerAnteprima(idProposta: number, progrBandoLineaIntervento: number): Observable<Array<BeneficiarioDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/beneficiari/anteprima`;
        let params = new HttpParams();
        if (progrBandoLineaIntervento != null) {
            params = params.set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
        }
        return this.http.get<Array<BeneficiarioDTO>>(url, { params: params });
    }

    getAllProgettiPerAnteprima(idProposta: number, progrBandoLineaIntervento: number, idBeneficiario: number): Observable<Array<ProgettoDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/progetti/anteprima`;
        let params = new HttpParams();
        if (progrBandoLineaIntervento != null) {
            params = params.set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
        }
        if (idBeneficiario != null) {
            params = params.set("idBeneficiario", idBeneficiario.toString());
        }
        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    getLineeDiInterventoDisponibili(): Observable<Array<LineaInterventoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/lineeDiIntervento';
        return this.http.get<Array<LineaInterventoDTO>>(url);
    }

    getAnteprimaProposta(idProposta: number, progrBandoLineaIntervento: number, idBeneficiario: number, idProgetto: number, idLineaDiIntervento: number): Observable<Array<PropostaProgettoDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/certificazione/proposte/${idProposta}/anteprima`;
        let params = new HttpParams().set("idLineaDiIntervento", idLineaDiIntervento.toString());
        if (progrBandoLineaIntervento != null) {
            params = params.set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
        }
        if (idBeneficiario != null) {
            params = params.set("idBeneficiario", idBeneficiario.toString());
        }
        if (idProgetto != null) {
            params = params.set("idProgetto", idProgetto.toString());
        }
        return this.http.get<Array<PropostaProgettoDTO>>(url, { params: params });
    }

    sospendiProgettiProposta(request: AmmettiESospendiProgettiRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/progetti/sospensione';
        return this.http.post<EsitoOperazioni>(url, request);
    }

    ammettiProgettiProposta(request: AmmettiESospendiProgettiRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/progetti/ammissioni';
        return this.http.post<EsitoOperazioni>(url, request);
    }

    accodaPropostaCertificazione(request: AccodaPropostaRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/accoda';
        return this.http.post<EsitoOperazioni>(url, request);
    }

    lanciaCreazioneProposta(request: LanciaPropostaRequest): void {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/proposta/lancia';
        this.http.post<void>(url, request).subscribe(data => { }, err => { });
        //non ho bisogno del valore di ritorno di questo servizio
        //non devo mostare l'errore di timeout in quanto il servizio è molto lungo
    }
}
