import { Observable } from 'rxjs';
import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { InizializzaSpesaValidataDTO } from '../commons/dto/inizializza-spesa-validata-dto';
import { CercaDocumentiSpesaValidataRequest } from '../commons/requests/cerca-documenti-spesa-validata-request';
import { DocumentoDiSpesa } from 'src/app/rendicontazione/commons/dto/documento-di-spesa';
import { DichiarazioneSpesaValidataDTO } from '../commons/dto/dichiarazione-spesa-validata-dto';
import { InizializzaModificaSpesaValidataDTO } from '../commons/dto/inizializza-modifica-spesa-validata-dto';
import { VoceDiSpesaDTO } from 'src/app/rendicontazione/commons/dto/voce-di-spesa-dto';
import { SalvaSpesaValidataRequest } from '../commons/requests/salva-spesa-validata-request';
import { EsitoSalvaSpesaValidataDTO } from '../commons/dto/esito-salva-spesa-validata-dto';
import { ConfermaSalvaSpesaValidataRequest } from '../commons/requests/conferma-salva-spesa-validata-request';
import { RettificaVoceItem } from '../commons/dto/rettifica-voce-item';
import { MensilitaProgettoDTO } from '../commons/dto/mensilita-progetto-dto';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { RilievoDocSpesaDTO } from '../commons/dto/rilievo-doc-spesa-dto';

@Injectable()
export class GestioneSpesaValidataService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        @Inject(DOCUMENT) private document: any
    ) { }

    inizializzaSpesaValidata(idProgetto: number, isFP: boolean): Observable<InizializzaSpesaValidataDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/inizializzaSpesaValidata';
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("isFP", isFP ? "S" : "N");
        return this.http.get<InizializzaSpesaValidataDTO>(url, { params: params });
    }

    inizializzaModificaSpesaValidata(idProgetto: number, idBandoLinea: number, idDocumentoDiSpesa: number, numDichiarazione: string, codiceRuolo: string): Observable<InizializzaModificaSpesaValidataDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/inizializzaModificaSpesaValidata';
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idBandoLinea", idBandoLinea.toString())
            .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
            .set("numDichiarazione", numDichiarazione)
            .set("codiceRuolo", codiceRuolo);
        return this.http.get<InizializzaModificaSpesaValidataDTO>(url, { params: params });
    }

    cercaDocumentiSpesaValidata(request: CercaDocumentiSpesaValidataRequest): Observable<Array<DocumentoDiSpesa>> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/cercaDocumentiSpesaValidata';
        return this.http.post<Array<DocumentoDiSpesa>>(url, request);
    }

    recuperaMensilitaProgetto(idProgetto: number): Observable<Array<MensilitaProgettoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/recuperaMensilitaProgetto';
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<Array<MensilitaProgettoDTO>>(url, { params: params });
    }

    validaMensilitaProgetto(mensilitaProgettoDTOs: Array<MensilitaProgettoDTO>): Observable<EsitoSalvaSpesaValidataDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/validaMensilitaProgetto';
        return this.http.post<EsitoSalvaSpesaValidataDTO>(url, mensilitaProgettoDTOs);
    }

    reportDettaglioDocumentoDiSpesa(idProgetto: number) {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/reportDettaglioDocumentoDiSpesa';
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    dichiarazioniDocumentoDiSpesa(idDocumentoDiSpesa: number, idProgetto: number): Observable<Array<DichiarazioneSpesaValidataDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/dichiarazioniDocumentoDiSpesa';
        let params = new HttpParams().set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<Array<DichiarazioneSpesaValidataDTO>>(url, { params: params });
    }

    voceDiSpesa(idQuotaParte: number, idDocumentoDiSpesa: number, idProgetto: number): Observable<VoceDiSpesaDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/voceDiSpesa';
        let params = new HttpParams()
            .set("idQuotaParte", idQuotaParte.toString())
            .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
            .set("idProgetto", idProgetto.toString());
        return this.http.get<VoceDiSpesaDTO>(url, { params: params });
    }

    salvaSpesaValidata(request: SalvaSpesaValidataRequest): Observable<EsitoSalvaSpesaValidataDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/salvaSpesaValidata';
        return this.http.post<EsitoSalvaSpesaValidataDTO>(url, request);
    }

    confermaSalvaSpesaValidata(request: ConfermaSalvaSpesaValidataRequest): Observable<EsitoSalvaSpesaValidataDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/confermaSalvaSpesaValidata';
        return this.http.post<EsitoSalvaSpesaValidataDTO>(url, request);
    }

    dettaglioRettifiche(progQuotaParte: number): Observable<Array<RettificaVoceItem>> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/dettaglioRettifiche';
        let params = new HttpParams().set("progQuotaParte", progQuotaParte.toString());
        return this.http.get<Array<RettificaVoceItem>>(url, { params: params });
    }

    sospendiDocumentoSpesaValid(idDichiarazioneDiSpesa: number, idDocumentoDiSpesa: number, idProgetto: number, noteValidazione: string): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/sospendiDocumentoSpesaValid';
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
            .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
        if (noteValidazione) {
            params = params.set("noteValidazione", noteValidazione);
        }
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    annullaSospendiDocumentoSpesaValid(idDichiarazioneDiSpesa: number, idDocumentoDiSpesa: number, idProgetto: number, noteValidazione: string): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/annullaSospendiDocumentoSpesaValid';
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
            .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
        if (noteValidazione) {
            params = params.set("noteValidazione", noteValidazione);
        }
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    salvaRilievoDS(idDichiarazioneDiSpesa: number, note: string): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/salvaRilievoDS';
        let params = new HttpParams().set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString()).set("note", note);
        return this.http.put<EsitoDTO>(url, { body: null }, { params: params });
    }

    setNullaDaRilevare(idDichiarazioneDiSpesa: number, idProgetto: number, idDocumentiConRilievo: number[]): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/setNullaDaRilevare';
        let params = new HttpParams().set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString()).set("idProgetto", idProgetto.toString());
        return this.http.put<EsitoDTO>(url, idDocumentiConRilievo, { params: params });
    }

    chiudiRilievi(idDichiarazioneDiSpesa: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/chiudiRilievi';
        let params = new HttpParams().set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
        return this.http.put<EsitoDTO>(url, { body: null }, { params: params });
    }

    getRilievoDocSpesa(idDocumentoDiSpesa: number, idProgetto: number, idDichiarazioneDiSpesa: number): Observable<RilievoDocSpesaDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/rilievoDocSpesa';
        let params = new HttpParams()
            .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
            .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString())
            .set("idProgetto", idProgetto.toString());
        return this.http.get<RilievoDocSpesaDTO>(url, { params: params });
    }

    salvaRilievoDocSpesa(idDocumentoDiSpesa: number, idProgetto: number, note: string): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/salvaRilievoDocSpesa';
        let params = new HttpParams()
            .set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString())
            .set("idProgetto", idProgetto.toString())
            .set("note", note);
        return this.http.put<EsitoDTO>(url, { body: null }, { params: params });
    }

    deleteRilievoDS(idDichiarazioneDiSpesa: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/deleteRilievoDS';
        let params = new HttpParams().set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
        return this.http.put<EsitoDTO>(url, { body: null }, { params: params });
    }

    deleteRilievoDocSpesa(idDocumentoDiSpesa: number, idProgetto: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/deleteRilievoDocSpesa';
        let params = new HttpParams().set("idDocumentoDiSpesa", idDocumentoDiSpesa.toString()).set("idProgetto", idProgetto.toString());
        return this.http.put<EsitoDTO>(url, { body: null }, { params: params });
    }

    confermaValiditaRilievi(idDichiarazioneDiSpesa: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/spesaValidata/confermaValiditaRilievi';
        let params = new HttpParams().set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
        return this.http.put<EsitoDTO>(url, { body: null }, { params: params });
    }
}