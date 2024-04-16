/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { HandleExceptionService } from "src/app/core/services/handle-exception.service";
import { BandoProgettiVO } from "../commons/vo/bando-progetti-vo";
import { NotificaAlertVO } from "../commons/vo/notifica-alert-vo";
import { NotificheFrequenzeVO } from "../commons/vo/notifiche-frequenze-vo";

@Injectable()
export class NotificheViaMailService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private handleExceptionService: HandleExceptionService
    ) { }

    getMail(): Observable<string> {
        let url = this.configService.getApiURL() + "/restfacade/notificheViaMail/getMail";
        return this.http.get(url, { responseType: 'text' });
    }

    saveMail(email: string) {
        let url = this.configService.getApiURL() + "/restfacade/notificheViaMail/saveMail";
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        return this.http.post<boolean>(url, email, { headers: headers });
    }

    getNotificheFrequenze(): Observable<NotificheFrequenzeVO> {
        let url = this.configService.getApiURL() + "/restfacade/notificheViaMail/getNotificheFrequenze";
        return this.http.get<NotificheFrequenzeVO>(url);
    }

    associateNotificheIstruttore(notificheAlert: Array<NotificaAlertVO>) {
        let url = this.configService.getApiURL() + "/restfacade/notificheViaMail/associateNotificheIstruttore";
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        return this.http.post<boolean>(url, notificheAlert, { headers: headers });
    }

    getBandiProgetti(idSoggettoNotifica: number): Observable<Array<BandoProgettiVO>> {
        let url = this.configService.getApiURL() + "/restfacade/notificheViaMail/getBandiProgetti";
        let params = new HttpParams().set("idSoggettoNotifica", idSoggettoNotifica.toString());
        return this.http.get<Array<BandoProgettiVO>>(url, { params: params });
    }

    associateProgettiToNotifica(notificaAlert: NotificaAlertVO) {
        let url = this.configService.getApiURL() + "/restfacade/notificheViaMail/associateProgettiToNotifica";
        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        return this.http.post<boolean>(url, notificaAlert, { headers: headers });
    }

}
