/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AttivitaPregresseDTO } from '../commons/dto/attivita-pregresse-dto';
import { DatiGenerali } from '../commons/dto/dati-generali';

@Injectable()
export class DatiProgettoAttivitaPregresseService {
    constructor(
        private http: HttpClient
    ) { }

    getDatiGenerali(apiURL: string, idProgetto: number): Observable<DatiGenerali> {
        let url = apiURL + "/restfacade/datiProgetto/datiGenerali";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<DatiGenerali>(url, { params: params });
    }

    getAttivitaPregresse(apiURL: string, idProgetto: number): Observable<Array<AttivitaPregresseDTO>> {
        let url = apiURL + "/restfacade/datiProgetto/attivitaPregresse";
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<Array<AttivitaPregresseDTO>>(url, { params: params });
    }
}