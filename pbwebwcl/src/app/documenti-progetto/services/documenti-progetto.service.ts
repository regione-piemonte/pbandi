/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable, timer } from 'rxjs';
import { retry, share, switchMap, takeUntil } from 'rxjs/operators';
import { ConfigService } from 'src/app/core/services/config.service';
import { CodiceDescrizioneDTO } from 'src/app/gestione-spesa-validata/commons/dto/codice-descrizione-dto';
import { DatiIntegrazioneDsDTO } from 'src/app/rendicontazione/commons/dto/dati-integrazione-ds-dto';
import { DocumentoAllegatoDTO } from 'src/app/rendicontazione/commons/dto/documento-allegato-dto';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { Documento } from '../commons/dto/documento';
import { DocumentoIndexDTO } from '../commons/dto/documento-index-dto';
import { DocumentoListResponse } from '../commons/dto/documento-list-response';
import { FiltroRicercaDocumentiDTO } from '../commons/dto/filtro-ricerca-documenti-dto';
import { InizializzaDocumentiDiProgettoDTO } from '../commons/dto/inizializza-documenti-di-progetto-dto';
import { ProgettoBenenficiarioDTO } from '../commons/dto/progetto-beneficiario-dto';
import { AllegatiDichiarazioneDiSpesaDTO } from 'src/app/rendicontazione/commons/dto/allegati-dichiarazione-di-spesa-dto';

@Injectable()
export class DocumentiProgettoService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        @Inject(DOCUMENT) private document: any
    ) { }


    inizializzaDocumentiDiProgetto(codiceRuolo: string): Observable<InizializzaDocumentiDiProgettoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/inizializzaDocumentiDiProgetto';
        let params = new HttpParams().set("codiceRuolo", codiceRuolo);
        return this.http.get<InizializzaDocumentiDiProgettoDTO>(url, { params: params });
    }

    getBeneficiariDocProgettoByDenomOrIdBen(denominazione: string, idBeneficiario: number): Observable<Array<CodiceDescrizioneDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/beneficiari';
        let params = new HttpParams().set("denominazione", denominazione);
        if (idBeneficiario) {
            params = params.set("idBeneficiario", idBeneficiario.toString());
        }
        return this.http.get<Array<CodiceDescrizioneDTO>>(url, { params: params });
    }

    progettiBeneficiario(idSoggettoBeneficiario: number, codiceRuolo: string): Observable<Array<ProgettoBenenficiarioDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/progettiBeneficiario';
        let params = new HttpParams().set("idSoggettoBeneficiario", idSoggettoBeneficiario.toString()).set("codiceRuolo", codiceRuolo);
        return this.http.get<Array<ProgettoBenenficiarioDTO>>(url, { params: params });
    }

    cercaDocumenti(request: FiltroRicercaDocumentiDTO): Observable<Array<DocumentoIndexDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/cercaDocumenti';
        return this.http.post<Array<DocumentoIndexDTO>>(url, request);
    }

    salvaUpload(codiceRuolo: string, idTipoDocIndex: number, idProgetto: number, listaIdCategAnag: string, file: File): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/salvaUpload';
        let formData = new FormData();
        formData.append("codiceRuolo", codiceRuolo);
        formData.append("idProgetto", idProgetto.toString());
        formData.append("idTipoDocIndex", idTipoDocIndex.toString());
        formData.append("listaIdCategAnag", listaIdCategAnag);
        formData.append("file", file, file.name);
        return this.http.post<boolean>(url, formData);
    }

    codiceStatoDocumentoIndex(idDocumentoIndex: number) {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/codiceStatoDocumentoIndex';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get(url, { params: params, responseType: 'text' });
    }

    allegati(idDocumentoIndex: number, codTipoDocIndex: string): Observable<Array<DocumentoAllegatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/allegati';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString()).set("codTipoDocIndex", codTipoDocIndex);
        return this.http.get<Array<DocumentoAllegatoDTO>>(url, { params: params });
    }

    allegatiIntegrazioniDS(idDichiarazioneSpesa: number, codTipoDocIndex: string): Observable<Array<DatiIntegrazioneDsDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/allegatiIntegrazioniDS';
        let params = new HttpParams().set("idDichiarazioneSpesa", idDichiarazioneSpesa.toString()).set("codTipoDocIndex", codTipoDocIndex);
        return this.http.get<Array<DatiIntegrazioneDsDTO>>(url, { params: params });
    }

    allegatiVerbaleChecklist(idChecklist: number, codTipoDocIndex: string, idDocumentoIndex: number): Observable<Array<DocumentoAllegatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/allegatiVerbaleChecklist';
        let params = new HttpParams()
            .set("idChecklist", idChecklist.toString())
            .set("codTipoDocIndex", codTipoDocIndex)
            .set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<Array<DocumentoAllegatoDTO>>(url, { params: params });
    }

    getAllegatiChecklist(idChecklist: number, codTipoDocIndex: string, idDocumentoIndex: number): Observable<Array<DocumentoAllegatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/getAllegatiChecklist';
        let params = new HttpParams()
            .set("idChecklist", idChecklist.toString())
            .set("codTipoDocIndex", codTipoDocIndex)
            .set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<any>(url, { params: params });
    }


    /*
    allegatiVerbaleChecklist(idDocumentoIndex: number): Observable<Array<DocumentoAllegatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/allegatiVerbaleChecklist';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<Array<DocumentoAllegatoDTO>>(url, { params: params });
        
    }*/

    allegatiVerbaleChecklistAffidamento(idDocumentoIndex: number, codTipoDocIndex: string): Observable<Array<DocumentoAllegatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/allegatiVerbaleChecklistAffidamento';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString()).set("codTipoDocIndex", codTipoDocIndex);
        return this.http.get<Array<DocumentoAllegatoDTO>>(url, { params: params });
    }

    cancellaFileConVisibilita(idDocumentoIndex: Number): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/cancellaFileConVisibilita';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    salvaDocumentoACTA(idProgetto: number, indiceClassificazioneEsteso: string): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/salvaDocumentoACTA';
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("indiceClassificazioneEsteso", indiceClassificazioneEsteso);
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    leggiFileActa(idDocumentoIndex: number) {
        let url = this.configService.getApiURL() + "/restfacade/documentiDiProgetto/leggiFileActa";
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    cancellaRecordDocumentoIndex(idDocumentoIndex: Number): Observable<boolean> { //per tipo documento "ACTA"
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/cancellaRecordDocumentoIndex';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    getDocumentoListFindom(idDomanda: string): Observable<DocumentoListResponse> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/documentoList';
        let params = new HttpParams().set("idDomanda", idDomanda);
        return this.http.get<DocumentoListResponse>(url, { params: params });
    }

    downloadDocumentoFindom(idDocumentoIndex: number, fonteDocumento: string) {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/documento';
        let params = new HttpParams()
            .set("idDocumento", idDocumentoIndex.toString())
            .set("fonteDocumento", fonteDocumento);
        return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    }

    getAllegatiByTipoDoc(idProgetto: number, idTarget: number, descBreveTipoDoc: string): Observable<Array<AllegatiDichiarazioneDiSpesaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/documentiDiProgetto/allegatiByTipoDoc';
        let params = new HttpParams()
            .set('idProgetto', idProgetto.toString())
            .set('idTarget', idTarget.toString())
            .set('descBreveTipoDoc', descBreveTipoDoc);
        return this.http.get<Array<AllegatiDichiarazioneDiSpesaDTO>>(url, { params: params });
    }
}
