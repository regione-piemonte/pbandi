/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { EsitoOperazioni } from "src/shared/api/models/esito-operazioni";
import { ResponseInizializzaSoppressioniDTO } from "../commons/dto/response-inizializza-soppressioni-dto";
import { Soppressione } from "../commons/requests/soppressione";

@Injectable()
export class SoppressioneService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  inizializzaSoppressioni(idProgetto: number): Observable<ResponseInizializzaSoppressioniDTO> {
    let url = this.configService.getApiURL() + "/restfacade/recuperi/inizializzaSoppressioni";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<ResponseInizializzaSoppressioniDTO>(url, { params: params });
  }

  salvaSoppressione(request: Soppressione): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/recuperi/salvaSoppressione";
    return this.http.post<EsitoOperazioni>(url, request);
  }
}
