/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from "src/app/core/services/handle-exception.service";
import { EsitoOperazioni } from "src/shared/api/models/esito-operazioni";
import { CreaComunicazioneRinunciaRequest } from "../commons/dto/crea-comunicazione-rinuncia-request";
import { ResponseCreaCommunicazioneRinuncia } from "../commons/dto/response-crea-communicazione-rinuncia";

@Injectable()
export class Rinuncia2Service {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService
    ) { }

    creaComunicazioneRinuncia(request: CreaComunicazioneRinunciaRequest): Observable<any> { //EsitoOperazioni | ResponseCreaCommunicazioneRinuncia
        let url = this.configService.getApiURL() + "/restfacade/rinuncia/comunicazioneRinuncia";
        return this.http.post<any>(url, request);
    }

    salvaInvioCartaceo(idDocumentoIndex: number, invioCartaceo: string): Observable<boolean> { //invio cartaceo: boolean "true" o "false"
        let url = this.configService.getApiURL() + '/restfacade/rinuncia/salvaInvioCartaceo';
        let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString()).set('invioCartaceo', invioCartaceo.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    salvaFileFirmato(idDocumentoIndex: number, fileFirmato: File): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/rinuncia/salvaFileFirmato';
        let formData = new FormData();
        formData.append("idDocumentoIndex", idDocumentoIndex.toString());
        formData.append("fileFirmato", fileFirmato, fileFirmato.name);

        return this.http.post<boolean>(url, formData);
    }

    salvaFileFirmaAutografa(idDocumentoIndex: number, fileFirmato: File): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/rinuncia/salvaFileFirmaAutografa';
        let formData = new FormData();
        formData.append("idDocumentoIndex", idDocumentoIndex.toString());
        formData.append("fileFirmaAutografa", fileFirmato, fileFirmato.name);

        return this.http.post<boolean>(url, formData);
    }

    //verificaFirmaDigitale e verificaFirmaDigitaleReturn, sono uguali, ma nel secondo bisogna aspettare il valore di ritorno

    verificaFirmaDigitale(idDocumentoIndex: number): void {
        let url = this.configService.getApiURL() + '/restfacade/rinuncia/verificaFirmaDigitale';
        let params = new HttpParams().set('idDocumentoIndex', idDocumentoIndex.toString());
        this.http.get<boolean>(url, { params: params }).subscribe(data => { }, err => {
            if (err.name !== "TimeoutError") {//ignoro l'errore di timeout
                this.handleExceptionService.handleNotBlockingError(err);
            }
        });
        //non ho bisogno del valore di ritorno di questo servizio
        //non devo mostare l'errore di timeout in quanto il servizio è molto lungo
    }


    getIsRegolaApplicabileForProgetto(idProgetto: number, codiceRegola: string) {
        let url =  this.configService.getApiURL() + '/restfacade/rinuncia/getIsRegolaApplicabileForProgetto';
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("codiceRegola", codiceRegola);
        return this.http.get<boolean>(url, { params: params });
      }


}
