/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { LinkMappaDTO } from "../commons/dto/link-mappa-dto";

@Injectable()
export class MappeService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  linkMappa(): Observable<LinkMappaDTO> {
    let url = this.configService.getApiURL() + `/restfacade/mappe/linkMappa`;
    return this.http.get<LinkMappaDTO>(url);
  }
}
