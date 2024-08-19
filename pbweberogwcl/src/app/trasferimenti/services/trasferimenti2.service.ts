/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { PbandiDLineaDiInterventoVO } from "../commons/dto/pbandi-d-linea-di-intervento-vo";

@Injectable()
export class Trasferimenti2Service {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  getNormativa(): Observable<Array<PbandiDLineaDiInterventoVO>> {
    let url = this.configService.getApiURL() + "/restfacade/trasferimenti/normativa";
    return this.http.get<Array<PbandiDLineaDiInterventoVO>>(url);
  }

}
