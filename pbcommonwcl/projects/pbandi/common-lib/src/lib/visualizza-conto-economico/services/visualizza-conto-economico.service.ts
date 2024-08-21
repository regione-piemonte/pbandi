/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ContoEconomicoItem } from '../commons/dto/conto-economico-item';
import { InizializzaVisualizzaContoEconomicoDTO } from '../commons/dto/inizializza-visualizza-conto-economico-dto';

@Injectable()
export class VisualizzaContoEconomicoService {
    constructor(
        private http: HttpClient,
    ) { }

    inizializzaVisualizzaContoEconomico(idProgetto: number, apiURL: string): Observable<InizializzaVisualizzaContoEconomicoDTO> {
        let url = apiURL + '/restfacade/contoEconomico/inizializzaVisualizzaContoEconomico';
        let params = new HttpParams().set("idProgetto", idProgetto.toString());
        return this.http.get<InizializzaVisualizzaContoEconomicoDTO>(url, { params: params });
    }

    aggiornaVisualizzaContoEconomico(idProgetto: number, idPartner: number, tipoRicerca: string, apiURL: string): Observable<Array<ContoEconomicoItem>> {
        let url = apiURL + '/restfacade/contoEconomico/aggiornaVisualizzaContoEconomico';
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("tipoRicerca", tipoRicerca);
        if (idPartner) {
            params = params.set("idPartner", idPartner.toString());
        }
        return this.http.get<Array<ContoEconomicoItem>>(url, { params: params });
    }

}
