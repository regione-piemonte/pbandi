/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { Observable } from 'rxjs';
import { ChecklistHtmlDTO } from 'src/app/shared/commons/dto/checklisthtml-dto';
import { CodiceDescrizioneDTO } from '@pbandi/common-lib';
import { CheckListItem } from '../commons/dto/CheckListItem';
import { InizializzaGestioneChecklistDTO } from '../commons/dto/inizializza-gestione-checklist-dto';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { EsitoLockCheckListDTO } from '../commons/dto/esito-lock-checklist-dto';
import { EsitoOperazioneDTO } from '../commons/dto/esito-operazione-dto';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { CercaChecklistRequestDTO } from '../commons/dto/cerca-checklist-request-dto';

@Injectable()
export class CheckListService {


    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }


    getModuloCheckList(idProgetto: number): Observable<ChecklistHtmlDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/getModuloCheckList";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());

        console.log('ANG CheckListService getModuloCheckList ,idProgetto =' + idProgetto);

        return this.http.get<ChecklistHtmlDTO>(url, { params: params });
    }

    getCheckListInLocoHtml(idDocumentoIndex: number): Observable<ChecklistHtmlDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/getCheckListInLocoHtml";
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<ChecklistHtmlDTO>(url, { params: params });
    }

    getCheckListValidazioneHtml(idDocumentoIndex: number, idDichiarazione: number, idProgetto: number) {
        let url = this.configService.getApiURL() + "/restfacade/checklist/getCheckListValidazioneHtml";
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString())
            .set("idDichiarazione", idDichiarazione.toString())  //idTarget
            .set("idProgetto", idProgetto.toString());
        return this.http.get<ChecklistHtmlDTO>(url, { params: params });
    }

    /*
     getModelloCheckListAffidamentoHtml(idProgetto: number, idAffidamento: number, soggettoControllore: string, codTipoChecklist: string): Observable<ChecklistHtmlDTO> {
            let url = this.configService.getApiURL() + "/restfacade/checklist/modelloAffidamentoHtml";
            let params = new HttpParams().set("idProgetto", idProgetto.toString())
                                         .set("idAffidamento", idAffidamento.toString())
                                         .set("soggettoControllore", soggettoControllore.toString())
                                         .set("codTipoChecklist", codTipoChecklist.toString());
    
            return this.http.get<ChecklistHtmlDTO>(url, { params: params });
        }
    */

    caricaDichiarazioneDiSpesa(idProgetto: number, isFP: boolean): Observable<Array<CodiceDescrizioneDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/caricaDichiarazioneDiSpesa";
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("isFP", isFP ? "S" : "N");

        console.log('ANG CheckListService caricaDichiarazioneDiSpesa ,idProgetto =' + idProgetto);
        return this.http.get<Array<CodiceDescrizioneDTO>>(url, { params: params });
    }

    caricaStatoSoggetto(idUtente: number, idIride: string): Observable<Array<CodiceDescrizioneDTO>> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/caricaStatoSoggetto";
        let params = new HttpParams().set("idUtente", idUtente.toString())
            .set("idIride", idIride.toString());

        console.log('ANG CheckListService caricaStatoSoggetto ,idUtente =' + idUtente + ', idIride=' + idIride);
        return this.http.get<Array<CodiceDescrizioneDTO>>(url, { params: params });
    }

    cercaChecklist(dichiarazioneSpesa: number, dataControllo: string, soggettoControllo: string,
        stati: string[], tipologia: string, rilevazione: string, versione: string, idProgetto: number): Observable<Array<CheckListItem>> {

        let url = this.configService.getApiURL() + "/restfacade/checklist/cercaChecklist";
        let request = new CercaChecklistRequestDTO(dichiarazioneSpesa?.toString() || null, dataControllo, soggettoControllo, stati, tipologia, rilevazione, versione,
            idProgetto.toString() || null);

        console.log('ANG CheckListService cercaChecklist ,dichiarazioneSpesa =' + dichiarazioneSpesa);
        return this.http.post<Array<CheckListItem>>(url, request);
    }

    inizializzaGestioneChecklist(idProgetto: number): Observable<InizializzaGestioneChecklistDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/inizializzaGestioneChecklist";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaGestioneChecklistDTO>(url, { params: params });
    }

    eliminaChecklist(idDocumentoIndex: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/eliminaChecklist";
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    salvaLockCheckListInLoco(idProgetto: number, idDocumentoIndex: number): Observable<EsitoLockCheckListDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/salvaLockCheckListInLoco";
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idDocumentoIndex", idDocumentoIndex.toString());
        return this.http.get<EsitoLockCheckListDTO>(url, { params: params });
    }

    salvaLockCheckListValidazione(idProgetto: number, idDichiarazione: number): Observable<EsitoLockCheckListDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/salvaLockCheckListValidazione";
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idDichiarazione", idDichiarazione.toString());
        return this.http.get<EsitoLockCheckListDTO>(url, { params: params });
    }

    saveCheckListInLocoHtml(statoCheckList: string, checklistHtml: string, idChecklist: string, idProgetto: number, userInfo: UserInfoSec): Observable<EsitoOperazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/salvaCheckListInLoco";
        let formData = new FormData();
        formData.append("idUtente", userInfo.idUtente.toString());
        formData.append("idIride", userInfo.idIride);

        formData.append("statoChecklist", statoCheckList);
        formData.append("checklistHtml", checklistHtml);
        formData.append("idChecklist", idChecklist);
        formData.append("idProgetto", idProgetto.toString());
        /*
                let params = new HttpParams().set("statoChecklist", statoChecklist)
                                             .set("checklistHtml", checklistHtml)
                                             .set("idChecklist", idChecklist)
                                             .set("idProgetto", idProgetto.toString());
        */
        return this.http.post<EsitoOperazioneDTO>(url, formData); //{ params: params });
    }

    saveCheckListDocumentaleHtml(statoCheckList: string, checklistHtml: string, idChecklist: number, idProgetto: number,
        userInfo: UserInfoSec, files: Array<File>): Observable<EsitoOperazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/saveCheckListDocumentaleHtml";
        let formData = new FormData();
        formData.append("idUtente", userInfo.idUtente.toString());
        formData.append("idIride", userInfo.idIride);

        formData.append("statoChecklist", statoCheckList);
        formData.append("checklistHtml", checklistHtml);
        formData.append("idChecklist", idChecklist.toString());
        formData.append("idProgetto", idProgetto.toString());
        if (files) {
            for (var i = 0; i < files.length; i++) {
                formData.append("file", files[i], files[i].name);
            }
        }

        return this.http.post<EsitoOperazioneDTO>(url, formData);
    }

    saveCheckListDefinitivo(statoCheckList: string, checklistHtml: string, idChecklist: number, idProgetto: number,
        userInfo: UserInfoSec, files: File[]): Observable<EsitoOperazioneDTO> {
        let url = this.configService.getApiURL() + "/restfacade/checklist/saveCheckListDefinitivo";
        let formData = new FormData();
        formData.append("idUtente", userInfo.idUtente.toString());
        formData.append("idIride", userInfo.idIride);

        formData.append("statoChecklist", statoCheckList);
        formData.append("checklistHtml", checklistHtml);
        if (idChecklist == null) {
            //formData.append("idChecklist", null);
        } else {
            formData.append("idChecklist", idChecklist.toString());
        }
        formData.append("idProgetto", idProgetto.toString());

        for (let i = 0; i < files.length; i++) {
            formData.append("file", files[i], files[i].name);
        }
        return this.http.post<EsitoOperazioneDTO>(url, formData);
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