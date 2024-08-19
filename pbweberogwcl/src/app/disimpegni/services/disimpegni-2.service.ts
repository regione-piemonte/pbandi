/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { ConfigService } from "src/app/core/services/config.service";
import { EsitoOperazioni } from "src/shared/api/models/esito-operazioni";

@Injectable()
export class Disimpegni2Service {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    checkPropostaCertificazione(idProgetto: number): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + "/restfacade/disimpegni/esistaProposta";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<EsitoOperazioni>(url, { params: params });
    }

    getRevocaConIrregolarita(idRevoca: number): Observable<EsitoOperazioni> {
        let url = this.configService.getApiURL() + "/restfacade/disimpegni/revocaConIrregolarita";
        let params = new HttpParams().set("idRevoca", idRevoca.toString());
        return this.http.get<EsitoOperazioni>(url, { params: params });
    }

}
