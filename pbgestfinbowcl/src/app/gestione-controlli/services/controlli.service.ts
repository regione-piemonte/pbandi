/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from "src/app/core/services/handle-exception.service";
import { AttivitaDTO } from "src/app/gestione-crediti/commons/dto/attivita-dto";
import { ControlloLocoVo } from "../commons/controllo-loco-vo";
import { DocumentoIndexVO } from "../commons/documento-index-vo";
import { searchControlliDTO } from "../commons/dto/searchControlliDTO";
import { RichiestaIntegrazioneVo } from "../commons/richiesta-integrazione-vo";
import { RichiestaProrogaVo } from "../commons/richiesta-proroga-vo";
import { EsitoDTO } from "src/app/shared/commons/dto/esito-dto";

@Injectable()
export class ControlliService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService
    ) { }

    getListaSuggest(id: number, search: searchControlliDTO): Observable<Array<AttivitaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/suggess';
        let params = new HttpParams()
            .set("id", id.toString())
        return this.http.post<Array<AttivitaDTO>>(url, search, { params: params });
    }
    getListaBando(idSoggetto: number): Observable<Array<AttivitaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getListaBando';
        let params = new HttpParams()
            .set("idSoggetto", idSoggetto.toString());
        return this.http.get<Array<AttivitaDTO>>(url, { params: params });
    }
    getListaProgetto(idSoggetto: number, progBandoLinea: number): Observable<Array<AttivitaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getListaProgetto';
        let params = new HttpParams()
            .set("idSoggetto", idSoggetto.toString())
            .set("progBandoLinea", progBandoLinea.toString());
        return this.http.get<Array<AttivitaDTO>>(url, { params: params });
    }

    getListaStatoControllo(): Observable<any> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/statoControllo';
        return this.http.get<any>(url);
    }
    getAutoritaControlante(): Observable<any> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/autoritaControllante';
        return this.http.get<any>(url);
    }

    suggerimentoTipoVista(): Observable<any> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/suggerimentoTipoVista';
        return this.http.get<any>(url);
    }

    getElencoControlli(search: searchControlliDTO): Observable<Array<ControlloLocoVo>> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getElencoControlli';
        return this.http.post<Array<ControlloLocoVo>>(url, search, {});
    }
    getElencoAltriControlli(search: searchControlliDTO): Observable<Array<ControlloLocoVo>> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getElencoAltriControlli';
        return this.http.post<Array<ControlloLocoVo>>(url, search, {});
    }
    getInfoControllo(idControllo, idProgetto): Observable<ControlloLocoVo> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getControllo';
        let params = new HttpParams()
            .set("idControllo", idControllo.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<ControlloLocoVo>(url, { params: params });
    }
    getInfoAltroControllo(idControllo, idProgetto): Observable<ControlloLocoVo> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getAltroControllo';
        let params = new HttpParams()
            .set("idControllo", idControllo.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<ControlloLocoVo>(url, { params: params });
    }

    salvaAllegato(idTipoDocumentoIndex: number, idUtente: number, nomeFile: string, file: File, idTarget: number, idProgetto: number, entita: number): Observable<boolean> {

        let url = this.configService.getApiURL() + '/restfacade/controlli/salvaAllegato';
        let formData = new FormData();
        formData.append("file", file, nomeFile);
        formData.append("idUtente", idUtente.toString());
        formData.append("nomeFile", nomeFile);
        formData.append("idTarget", idTarget.toString());
        formData.append("idTipoDocumentoIndex", idTipoDocumentoIndex.toString());
        formData.append("idProgetto", idProgetto.toString());
        formData.append("entita", entita.toString());
        return this.http.post<boolean>(url, formData);
    }

    salvaAllegati(allegati: FormData[]): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/salvaAllegati';
        return this.http.post<boolean>(url, allegati);
    }
    salvaAllegati2(elencoFile: File[], idStatoControllo: number, idProgetto: number, isControlloFinp: boolean, idControllo: number, idRevoca?: number): Observable<boolean> {
        // this.fileLetteraAvvioControllo = this.ElencoFileDaGestire[0];
        // this.fileLetteraAccompagnatoria = this.ElencoFileDaGestire[1];
        // this.fileLetteraEsitoControllo = this.ElencoFileDaGestire[2];
        // this.fileVerbaleControllo = this.ElencoFileDaGestire[3];
        // this.fileCheckListControllo = this.ElencoFileDaGestire[4];
        let url = this.configService.getApiURL() + '/restfacade/controlli/salvaAllegati';
        let formData = new FormData();
        formData.append("idStatoControllo", idStatoControllo.toString());
        formData.append("isControlloFinp", isControlloFinp.toString())
        formData.append("idProgetto", idProgetto.toString());
        formData.append("idControllo", idControllo.toString());

        formData.append("fileVerbaleControllo", elencoFile[3], elencoFile[3].name);
        formData.append("fileCheckListControllo", elencoFile[4], elencoFile[4].name);

        switch (idStatoControllo) {
            case 5:
            case 4:
                formData.append("idRevoca", idRevoca.toString());
                break;
            case 2:
                //formData.append("fileVerbaleControllo", elencoFile[3], elencoFile[3].name);
                formData.append("fileLetteraEsitoControllo", elencoFile[2], elencoFile[2].name);
                break;

            default:
                break;
        }
        return this.http.post<boolean>(url, formData);
    }

    updateControlloFinp(controllo: ControlloLocoVo, idUtente: number, idAttivitaControllo: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/updateControlloFinp';
        let params = new HttpParams()
            .set("idUtente", idUtente.toString())
            .set("idAttivitaControllo", idAttivitaControllo.toString())
        return this.http.post<EsitoDTO>(url, controllo, { params: params });
    }
    chiamaIterAuto002(controllo: ControlloLocoVo, _idTarget: any, idUtente: any, isAvvioControllo: any, isControlloFinp: any, idEntita: any): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/chiamaIterAuto002';
        let formData = new FormData();
        formData.append("idUtente", idUtente.toString());
        formData.append("idControllo", controllo.idControllo.toString());
        formData.append("idTarget", controllo.idControllo.toString());
        formData.append("idProgetto", controllo.idProgetto.toString());
        formData.append("isAvvioControllo", isAvvioControllo.toString());
        formData.append("isControlloFinp", isControlloFinp.toString());
        formData.append("idEntita", idEntita.toString());

        return this.http.post<boolean>(url, formData);
    }
    avviaIterIntegrazione(richiestaIntegr: RichiestaIntegrazioneVo, idUtente: number): Observable<number> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/avviaIterIntegrazione';
        let params = new HttpParams()
            .set("idUtente", idUtente.toString())
        return this.http.post<number>(url, richiestaIntegr, { params: params });
    }
    checkRichiestaIntegrazione(idControllo: number): Observable<RichiestaIntegrazioneVo> {

        let url = this.configService.getApiURL() + '/restfacade/controlli/checkRichiestaIntegrazione';
        let params = new HttpParams()
            .set("idControllo", idControllo.toString());
        return this.http.get<RichiestaIntegrazioneVo>(url, { params: params });
    }
    checkRichiestaIntegrAltroControllo(idControllo: number): Observable<RichiestaIntegrazioneVo> {

        let url = this.configService.getApiURL() + '/restfacade/controlli/checkRichiestaIntegrAltroControllo';
        let params = new HttpParams()
            .set("idControllo", idControllo.toString());
        return this.http.get<RichiestaIntegrazioneVo>(url, { params: params });
    }
    getRichProroga(idRichiestaIntegrazione: number): Observable<RichiestaProrogaVo> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getRichProroga';
        let params = new HttpParams()
            .set("idRichiestaIntegr", idRichiestaIntegrazione.toString());
        return this.http.get<RichiestaProrogaVo>(url, { params: params });
    }
    getElencoRichProroga(idRichiestaIntegrazione: number): Observable<Array<RichiestaProrogaVo>> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getElencoRichProroga';
        let params = new HttpParams()
            .set("idRichiestaIntegr", idRichiestaIntegrazione.toString());
        return this.http.get<Array<RichiestaProrogaVo>>(url, { params: params });
    }
    getRichProrogaAltroControllo(idRichiestaIntegrazione: number): Observable<RichiestaProrogaVo> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getRichProrogaAltroControllo';
        let params = new HttpParams()
            .set("idRichiestaIntegr", idRichiestaIntegrazione.toString());
        return this.http.get<RichiestaProrogaVo>(url, { params: params });
    }

    chiudiRichiestaIntegr(idRichiestaIntegr: any, idUtente: number): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/chiudiRichiestaIntegr';
        let params = new HttpParams()
            .set("idRichiestaIntegr", idRichiestaIntegr.toString()).set("idUtente", idUtente.toString());
        return this.http.get<boolean>(url, { params: params });
    }
    gestisciProroga(proroga: RichiestaProrogaVo, idUtente: number, id: number): Observable<boolean> {

        let url = this.configService.getApiURL() + '/restfacade/controlli/gestisciProroga';
        let params = new HttpParams()
            .set("id", id.toString()).set("idUtente", idUtente.toString());

        return this.http.post<boolean>(url, proroga, { params: params });
    }
    salvaDataNotifica(richiestaIntegr: RichiestaIntegrazioneVo): Observable<boolean> {

        let url = this.configService.getApiURL() + '/restfacade/controlli/salvaDataNotifica';
        let params = new HttpParams()
        return this.http.post<boolean>(url, richiestaIntegr, { params: params });
    }
    salvaAltroControllo(controllo: ControlloLocoVo, idUtente: number, idAttivitaControllo: number): Observable<number> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/salvaAltroControllo';
        let params = new HttpParams()
            .set("idUtente", idUtente.toString())
            .set("idAttivitaControllo", idAttivitaControllo.toString())
        return this.http.post<number>(url, controllo, { params: params });
    }
    updateAltroControllo(controllo: ControlloLocoVo, idUtente: number): Observable<EsitoDTO> {

        let url = this.configService.getApiURL() + '/restfacade/controlli/updateAltroControllo';
        let params = new HttpParams().set("idUtente", idUtente.toString())

        return this.http.post<EsitoDTO>(url, controllo, { params: params });
    }
    getElencoAllegati(idControllo: number, idEntita: number): Observable<Array<DocumentoIndexVO>> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getElencoAllegati';
        let params = new HttpParams().set("idControllo", idControllo.toString())
            .set("idEntita", idEntita.toString());
        return this.http.get<Array<DocumentoIndexVO>>(url, { params: params });
    }
    getElencoAllegatiBeneficiario(idControllo: number, idEntita: number): Observable<Array<DocumentoIndexVO>> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/getElencoAllegatiBeneficiario';
        let params = new HttpParams().set("idTarget", idControllo.toString())
            .set("idEntita", idEntita.toString());
        return this.http.get<Array<DocumentoIndexVO>>(url, { params: params });
    }
    avviaIterEsitoPositivoControllo(idProgetto: number, idTarget: number, isAltriControlli: boolean): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/controlli/avviaIterEsitoPositivoControllo';
        let params = new HttpParams()
            .set("idProgetto", idProgetto.toString())
            .set("idTarget", idTarget.toString())
            .set("isAltriControlli", isAltriControlli.toString())
        return this.http.post<boolean>(url, {}, { params: params });
    }
}