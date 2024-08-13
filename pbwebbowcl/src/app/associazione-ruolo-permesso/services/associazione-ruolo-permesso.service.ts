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
export class AssociazioneRuoloPermessoService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    cercaRuoli(descrizione: string, codice: string, idUtente: number): Observable<Array<TipoAnagraficaDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercaruoli';
        let params = new HttpParams().set("idU", idUtente.toString());
        if (descrizione) {
            params = params.set("descrizione", descrizione);
        }
        if (codice) {
            params = params.set("codice", codice);
        }
        return this.http.get<Array<TipoAnagraficaDTO>>(url, { params: params });
    }

    cercaPermessiDaAssociare(codice: string, idUtente: number): Observable<Array<PermessoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercapermessidaassociare';
        let params = new HttpParams().set("codice", codice).set("idU", idUtente.toString());
        return this.http.get<Array<PermessoDTO>>(url, { params: params });
    }

    cercaPermessiAssociati(codice: string, idUtente: number): Observable<Array<PermessoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/cercapermessiassociati';
        let params = new HttpParams().set("codice", codice).set("idU", idUtente.toString());
        return this.http.get<Array<PermessoDTO>>(url, { params: params });
    }

    associaPermessi(codice: string, idUtente: number, daAssociare: Array<PermessoDTO>): Observable<ResponseCodeMessage> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/associapermessi';
        let params = new HttpParams().set("codice", codice).set("idU", idUtente.toString());
        return this.http.post<ResponseCodeMessage>(url, daAssociare, { params: params });
    }

    disassociaPermessi(codice: string, idUtente: number, associati: Array<PermessoDTO>): Observable<ResponseCodeMessage> {
        let url = this.configService.getApiURL() + '/restfacade/associazione/disassociapermessi';
        let params = new HttpParams().set("codice", codice).set("idU", idUtente.toString());
        return this.http.post<ResponseCodeMessage>(url, associati, { params: params });
    }
}
