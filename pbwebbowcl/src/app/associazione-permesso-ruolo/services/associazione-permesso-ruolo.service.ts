/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PermessoDTO } from '../commons/dto/permesso-dto';
import { TipoAnagraficaDTO } from '../commons/dto/tipo-anagrafica-dto';
import { ResponseCodeMessage } from 'src/app/shared/commons/dto/response-code-message-dto';

@Injectable()
export class AssociazionePermessoRuoloService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    cercaPermessi(descrizione: string, codice: string, idUtente: number): Observable<Array<PermessoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercapermessi';
        let params = new HttpParams().set("idU", idUtente.toString());
        if (descrizione) {
            params = params.set("descrizione", descrizione);
        }
        if (codice) {
            params = params.set("codice", codice);
        }
        return this.http.get<Array<PermessoDTO>>(url, { params: params });
    }

    cercaRuoliDaAssociare(codice: string, idUtente: number): Observable<Array<TipoAnagraficaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercaruolidaassociare';
        let params = new HttpParams().set("codice", codice).set("idU", idUtente.toString());
        return this.http.get<Array<TipoAnagraficaDTO>>(url, { params: params });
    }

    cercaRuoliAssociati(codice: string, idUtente: number): Observable<Array<TipoAnagraficaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercaruoliassociati';
        let params = new HttpParams().set("codice", codice).set("idU", idUtente.toString());
        return this.http.get<Array<TipoAnagraficaDTO>>(url, { params: params });
    }

    associaRuoli(codice: string, idUtente: number, daAssociare: Array<TipoAnagraficaDTO>): Observable<ResponseCodeMessage> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/associaruoli';
        let params = new HttpParams().set("codice", codice).set("idU", idUtente.toString());
        return this.http.post<ResponseCodeMessage>(url, daAssociare, { params: params });
    }

    disassociaRuoli(codice: string, idUtente: number, associati: Array<TipoAnagraficaDTO>): Observable<ResponseCodeMessage> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/disassociaruoli';
        let params = new HttpParams().set("codice", codice).set("idU", idUtente.toString());
        return this.http.post<ResponseCodeMessage>(url, associati, { params: params });
    }
}
