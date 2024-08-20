/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { RigaTabellaAffidamenti } from '../commons/dto/affidamento';
import { map } from 'rxjs/operators'
import { NormativaAffidamentoDTO } from '../commons/dto/normativa-affidamento-dto';
import { TipoAffidamentoDTO } from '../commons/dto/tipo-affidamento-dt';
import { TipologiaAppaltoDTO } from '../commons/dto/tipologia-appalto-dto';
import { MotiveAssenzaDTO } from '../commons/dto/motive-assenza-dto';
import { TipologiaAggiudicazioneDTO } from '../commons/dto/tipologia-aggiudicazione-dto';
import { EsitoOperazioni } from 'src/app/shared/commons/dto/esito-operazioni';
import { SalvaAffidamentoRequest } from '../commons/requests/salva-affidamento-request';
import { AffidamentoDTO } from '../commons/dto/affidamento-dto';
import { FornitoreDTO } from 'src/app/shared/commons/dto/fornitore-dto';
import { EsitoGestioneAffidamenti } from 'src/app/shared/commons/dto/esito-gestione-affidamenti';
import { EsitoSalvaModuloCheckListHtml } from 'src/app/shared/commons/dto/esito-modulo-checkList';
import { CodiceDescrizioneDTO } from 'src/app/shared/commons/dto/codice-descrizione-dto';
import { SalvaVarianteRequest } from '../commons/requests/salva-variante-request';
import { SalvaFornitoreRequest } from '../commons/requests/salva-fornitore-request';
import { VerificaAffidamentoRequest } from '../commons/requests/verifica-affidamento-request';
import { DocumentoAllegato } from '../commons/dto/documento-allegato';
import { NotificaDTO } from '../commons/dto/notifica-dto';
import { AffidamentoCheckListDTO } from '../commons/dto/affidamento-checklist-dto';
import { ChecklistHtmlDTO } from '../commons/dto/checklisthtml-dto';
import { EsitoLockCheckListDTO } from '../commons/dto/esito-lock-checklist-dto';
import { SalvaCheckListAffidamentoDocumentaleHtmlRequest } from '../commons/requests/salva-checkList-affidamentoDocumentaleHtml-request';
import { EntitaDTO } from '../commons/dto/entita-dto';
import { AssociateFileRequest } from '../commons/requests/associate-file-request';
import { SalvaCheckListAffidamentoDocumentaleBozzaHtmlRequest } from '../commons/requests/salva-checkList-affidamentoDocumentaleHtml-bozza-request';
import { RespingiAffidamentoRequest } from '../commons/requests/respingi-affidamento-request';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { DocumentoAllegatoDTO } from '../commons/dto/documento-allegato-dto';
import { EsitoOperazioneDTO } from '../commons/dto/esito-operazione-dto';

@Injectable()
export class AffidamentiService {
    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    getElencoAffidamenti(idProgetto: number): Observable<Array<RigaTabellaAffidamenti>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamenti";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<Array<RigaTabellaAffidamenti>>(url, { params: params });
    }

    getCodiceProgetto(idProgetto: number) {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/progetto";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get(url, { params: params, responseType: 'text' });
    }

    getNormativeAffidamento(): Observable<Array<NormativaAffidamentoDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento/normative";
        return this.http.get<Array<NormativaAffidamentoDTO>>(url);
    }

    getTipologieAffidamento(): Observable<Array<TipoAffidamentoDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento/tipologie";
        return this.http.get<Array<TipoAffidamentoDTO>>(url);
    }

    getTipologieAppalto(): Observable<Array<TipologiaAppaltoDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/appalto/tipologie";
        return this.http.get<Array<TipologiaAppaltoDTO>>(url);
    }

    getTipologieProcedureAggiudicazione(idProgetto: number): Observable<Array<TipologiaAggiudicazioneDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/aggiudicazione/tipologie";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<Array<TipologiaAggiudicazioneDTO>>(url, { params: params });
    }

    getMotiveAssenza(): Observable<Array<MotiveAssenzaDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/aggiudicazione/motiveAssenza";
        return this.http.get<Array<MotiveAssenzaDTO>>(url);
    }

    salvaAffidamento(request: SalvaAffidamentoRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento";
        return this.http.post<EsitoOperazioni>(url, request);
    }

    getAffidamento(idAppalto: number): Observable<AffidamentoDTO> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento";
        let params = new HttpParams().set("idAppalto", idAppalto.toString());
        return this.http.get<AffidamentoDTO>(url, { params: params });
    }

    getTipologieVarianti(): Observable<Array<CodiceDescrizioneDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/varianti/tipologie";
        return this.http.get<Array<CodiceDescrizioneDTO>>(url);
    }

    salvaVariante(request: SalvaVarianteRequest): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento/variante";
        return this.http.post<EsitoGestioneAffidamenti>(url, request);
    }

    getFornitoriAssociabili(): Observable<Array<FornitoreDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/fornitori";
        return this.http.get<Array<FornitoreDTO>>(url);
    }

    getRuoli(): Observable<Array<CodiceDescrizioneDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/ruoli";
        return this.http.get<Array<CodiceDescrizioneDTO>>(url);
    }

    salvaFornitore(request: SalvaFornitoreRequest): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento/fornitore";
        return this.http.post<EsitoGestioneAffidamenti>(url, request);
    }

    salvaSubcontratto(request: SalvaFornitoreRequest): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento/subcontratto";
        return this.http.post<EsitoGestioneAffidamenti>(url, request);
    }

    cancellaVariante(idVariante: number): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/affidamento/varianti/${idVariante}`;
        return this.http.delete<EsitoGestioneAffidamenti>(url);
    }

    cancellaFornitore(idFornitore: number, idAppalto: number, idTipoPercettore: number): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/affidamento/fornitori/${idFornitore}`;
        let params = new HttpParams().set("idAppalto", idAppalto.toString()).set("idTipoPercettore", idTipoPercettore.toString());
        return this.http.delete<EsitoGestioneAffidamenti>(url, { params: params });
    }

    cancellaSubcontratto(idSubcontrattoAffidamento: number): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/affidamento/subcontratto/${idSubcontrattoAffidamento}`;
        return this.http.delete<EsitoGestioneAffidamenti>(url);
    }

    getAllegati(idAppalto: number): Observable<Array<DocumentoAllegato>> {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/affidamenti/${idAppalto}/allegati`;
        return this.http.get<Array<DocumentoAllegato>>(url);
    }

    dissociateFileAffidamento(idDocumentoIndex: number, idProgetto: number, idAppalto: number): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/affidamento/allegati/${idDocumentoIndex}`;
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idAppalto", idAppalto.toString());
        return this.http.delete<EsitoOperazioni>(url, { params: params });
    }

    associateFileAffidamento(request: AssociateFileRequest): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + '/restfacade/affidamenti/affidamento/allegati';
        return this.http.post<EsitoOperazioni>(url, request);
    }

    inviaInVerifica(request: VerificaAffidamentoRequest): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento/verifica";
        return this.http.post<EsitoGestioneAffidamenti>(url, request);
    }

    cancellaAffidamento(idAppalto: number): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/affidamenti/${idAppalto}`;
        return this.http.delete<EsitoGestioneAffidamenti>(url);
    }

    getChecklistAssociatedAffidamento(idAppalto: number): Observable<Array<DocumentoAllegato>> {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/affidamenti/${idAppalto}/checkList`;
        return this.http.get<Array<DocumentoAllegato>>(url);
    }

    getAllAffidamentoChecklist(): Observable<Array<AffidamentoCheckListDTO>> {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/affidamentoChecklist`;
        return this.http.get<Array<AffidamentoCheckListDTO>>(url);
    }

    getNotifiche(idAppalto: number): Observable<Array<NotificaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/affidamenti/notifiche';
        let params = new HttpParams().set("idAppalto", idAppalto.toString());
        return this.http.get<Array<NotificaDTO>>(url, { params: params });
    }

    respingiAffidamento(request: RespingiAffidamentoRequest): Observable<EsitoGestioneAffidamenti> {
        let url = this.configService.getApiURL() + "/restfacade/affidamenti/affidamento/respingi";
        return this.http.post<EsitoGestioneAffidamenti>(url, request);
    }

    getModelloCheckListAffidamentoHtml(idProgetto: number, idAffidamento: number, soggettoControllore: string, codTipoChecklist: string): Observable<ChecklistHtmlDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/modelloAffidamentoHtml";
        let params = new HttpParams().set("idProgetto", idProgetto.toString())
            .set("idAffidamento", idAffidamento.toString())
            .set("soggettoControllore", soggettoControllore.toString())
            .set("codTipoChecklist", codTipoChecklist.toString());

        return this.http.get<ChecklistHtmlDTO>(url, { params: params });
    }

    salvaLockCheckListValidazione(idProgetto: number, idAffidamento: number): Observable<EsitoLockCheckListDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/salvaLockCheckListValidazione";
        let params = new HttpParams().set("idProgetto", idProgetto.toString())
            .set("idAffidamento", idAffidamento.toString());

        return this.http.get<EsitoLockCheckListDTO>(url, { params: params });
    }

    // TODO PK : da rivedere
    salvaCheckListAffidamentoDocumentaleHtml(request: SalvaCheckListAffidamentoDocumentaleHtmlRequest): Observable<EsitoSalvaModuloCheckListHtml> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/salvaCheckListAffidamentoDocumentaleHtml";
        let formData = new FormData();
        if (request.checklistHtmlDTO.allegati?.length > 0) {
            for (let i = 0; i < request.checklistHtmlDTO.allegati.length; i++) {
                formData.append("file", request.checklistHtmlDTO.allegati[i], request.checklistHtmlDTO.allegati[i].name);
            }
        }
        formData.append("checkListbytesVerbale", request.checklistHtmlDTO.bytesVerbale);
        formData.append("checkListcodStatoTipoDocIndex", request.checklistHtmlDTO.codStatoTipoDocIndex);
        formData.append("checkListcontentHtml", request.checklistHtmlDTO.contentHtml);
        formData.append("checkListfasiHtml", request.checklistHtmlDTO.fasiHtml);
        formData.append("checkListidChecklist", request.checklistHtmlDTO.idChecklist.toString());
        if (request.checklistHtmlDTO.idDocumentoIndex != null) {
            formData.append("checkListidDocumentoIndex", request.checklistHtmlDTO.idDocumentoIndex.toString());
        }
        formData.append("checkListidProgetto", request.checklistHtmlDTO.idProgetto.toString());
        formData.append("checkListsoggettoControllore", request.checklistHtmlDTO.soggettoControllore);
        formData.append("codStatoTipoDocIndex", request.codStatoTipoDocIndex);
        formData.append("codTipoChecklist", request.codTipoChecklist);
        formData.append("idAffidamento", request.idAffidamento.toString());
        formData.append("idProgetto", request.idProgetto.toString());
        formData.append("isRichiestaIntegrazione", request.isRichiestaIntegrazione.toString());
        formData.append("noteRichiestaIntegrazione", request.noteRichiestaIntegrazione);
        return this.http.post<EsitoSalvaModuloCheckListHtml>(url, formData);
    }

    // per salvare le checklist in loco "bozza" // TODO PK vedere se rinominare metodo
    salvaCheckListAffidamentoDocumentaleHtmlBozza(request: SalvaCheckListAffidamentoDocumentaleBozzaHtmlRequest): Observable<EsitoSalvaModuloCheckListHtml> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/salvaCheckListAffidamentoDocumentaleHtmlBozza";

        //console.log("AffidamentiService salvaCheckListAffidamentoDocumentaleHtmlBozza html="+request.checklistHtmlBozzaDTO.contentHtml)
        let formData = new FormData();

        formData.append("checkListCodStatoTipoDocIndex", request.checklistHtmlBozzaDTO.codStatoTipoDocIndex);
        formData.append("checkListContentHtml", request.checklistHtmlBozzaDTO.contentHtml);
        formData.append("checkListFasiHtml", request.checklistHtmlBozzaDTO.fasiHtml);
        formData.append("checkListIdChecklist", request.checklistHtmlBozzaDTO.idChecklist.toString());
        if (request.checklistHtmlBozzaDTO.idDocumentoIndex != null) {
            formData.append("checkListIdDocumentoIndex", request.checklistHtmlBozzaDTO.idDocumentoIndex.toString());
        }
        formData.append("checkListIdProgetto", request.checklistHtmlBozzaDTO.idProgetto.toString());
        formData.append("checkListSoggettoControllore", request.checklistHtmlBozzaDTO.soggettoControllore);
        formData.append("checkListBytesVerbale", request.checklistHtmlBozzaDTO.bytesVerbale);

        for (var i = 0; i < request.checklistHtmlBozzaDTO.allegati.length; i++) {
            formData.append("file", request.checklistHtmlBozzaDTO.allegati[i], request.checklistHtmlBozzaDTO.allegati[i].name);
        }

        formData.append("idProgetto", request.idProgetto.toString());
        formData.append("codStatoTipoDocIndex", request.codStatoTipoDocIndex);
        formData.append("idAffidamento", request.idAffidamento.toString());
        formData.append("isRichiestaIntegrazione", request.isRichiestaIntegrazione.toString());
        formData.append("noteRichiestaIntegrazione", request.noteRichiestaIntegrazione);
        formData.append("codTipoChecklist", request.codTipoChecklist);

        return this.http.post<EsitoSalvaModuloCheckListHtml>(url, formData);

        // setta il charset della post
        // let headers = new HttpHeaders().set('Charset', 'UTF-8');
        // return this.http.post<EsitoSalvaModuloCheckListHtml>(url, formData, {headers});
    }

    // per salvare le checklist in loco "salva definitivo"
    salvaCheckListAffidamentoInLocoHtml(request: SalvaCheckListAffidamentoDocumentaleHtmlRequest, userInfo: UserInfoSec): Observable<EsitoSalvaModuloCheckListHtml> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/salvaCheckListAffidamentoInLocoHtml";

        let formData = new FormData();
        formData.append("idUtente", userInfo.idUtente.toString());
        formData.append("idIride", userInfo.idIride);
        for (var i = 0; i < request.checklistHtmlDTO.allegati.length; i++) {
            formData.append("file", request.checklistHtmlDTO.allegati[i], request.checklistHtmlDTO.allegati[i].name);
        }
        formData.append("checkListbytesVerbale", request.checklistHtmlDTO.bytesVerbale);
        formData.append("checkListcodStatoTipoDocIndex", request.checklistHtmlDTO.codStatoTipoDocIndex);
        formData.append("checkListcontentHtml", request.checklistHtmlDTO.contentHtml);
        formData.append("checkListfasiHtml", request.checklistHtmlDTO.fasiHtml);
        formData.append("checkListidChecklist", request.checklistHtmlDTO.idChecklist.toString());
        if (request.checklistHtmlDTO.idDocumentoIndex != null) {
            formData.append("checkListidDocumentoIndex", request.checklistHtmlDTO.idDocumentoIndex.toString());
        }
        formData.append("checkListidProgetto", request.checklistHtmlDTO.idProgetto.toString());
        formData.append("checkListsoggettoControllore", request.checklistHtmlDTO.soggettoControllore);
        formData.append("codStatoTipoDocIndex", request.codStatoTipoDocIndex);
        formData.append("codTipoChecklist", request.codTipoChecklist);
        formData.append("idAffidamento", request.idAffidamento.toString());
        formData.append("idProgetto", request.idProgetto.toString());
        formData.append("isRichiestaIntegrazione", request.isRichiestaIntegrazione.toString());
        formData.append("noteRichiestaIntegrazione", request.noteRichiestaIntegrazione);
        return this.http.post<EsitoSalvaModuloCheckListHtml>(url, formData);
    }

    leggiStatoChecklist(idEntita: number, idTarget: number, idTipoDocIndexDoc: number, idProgetto: number) {
        let url = this.configService.getApiURL() + "/restfacade/checklist/leggiStatoChecklist";
        let params = new HttpParams().set("idEntita", idEntita.toString())
            .set("idTarget", idTarget.toString())
            .set("idTipoDocIndexDoc", idTipoDocIndexDoc.toString())
            .set("idProgetto", idProgetto.toString());
        return this.http.get(url, { params: params, responseType: 'text' });
    }

    findEntita(nomeEntita: string) {
        let url = this.configService.getApiURL() + "/restfacade/checklist/findEntita";
        let params = new HttpParams().set("nomeEntita", nomeEntita.toString());
        return this.http.get<EntitaDTO>(url, { params: params });
    }

    allegatiVerbaleChecklist(idDocumentoIndex: number): Observable<Array<DocumentoAllegatoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/affidamenti/allegatiVerbaleChecklist';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<Array<DocumentoAllegatoDTO>>(url, { params: params });
    }

    //PBSERVIZIT
    allegaFilesChecklist(codTipoDocumento: string, idDocumentoIndexChecklist: number, idProgetto: number,
        files: File[]): Observable<EsitoOperazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist-common/allegaFilesChecklist";
        let formData = new FormData();
        formData.append("codTipoDocumento", codTipoDocumento);
        formData.append("idDocumentoIndexChecklist", idDocumentoIndexChecklist.toString());
        formData.append("idProgetto", idProgetto.toString());
        for (let i = 0; i < files.length; i++) {
            formData.append("file", files[i], files[i].name);
        }
        return this.http.post<EsitoOperazioneDTO>(url, formData);
    }
}