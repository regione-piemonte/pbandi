/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { EsitoGestioneAppalti } from "../commons/dto/esito-gestione-appalti";

@Injectable()
export class Appalti2Service {
    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    eliminazioneAppaltoAbilitata(codiceRuolo: string): Observable<boolean> {
        let url = this.configService.getApiURL() + "/restfacade/applati/eliminazioneAppaltoAbilitata";
        let params = new HttpParams().set("codiceRuolo", codiceRuolo);
        return this.http.get<boolean>(url, { params: params });
    }

    eliminaAppalto(idAppalto: number): Observable<EsitoGestioneAppalti> {
        let url = this.configService.getApiURL() + "/restfacade/applati/eliminaAppalto";
        let params = new HttpParams().set("idAppalto", idAppalto.toString());
        return this.http.get<EsitoGestioneAppalti>(url, { params: params });
    }


}
