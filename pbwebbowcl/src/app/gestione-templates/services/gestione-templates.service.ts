/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TemplateDTO } from "src/app/configurazione-bando/commons/vo/template-DTO";
import { Decodifica } from "src/app/shared/commons/dto/decodifica";
import { ConfigService } from "../../core/services/config.service";
import { BandoLinea } from "../commons/dto/bando-linea";

@Injectable()
export class GestioneTemplatesService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  findBandiLinea(): Observable<Array<BandoLinea>> {
    let url = this.configService.getApiURL() + '/restfacade/gestionetemplates/bandiLinea';
    return this.http.get<Array<BandoLinea>>(url);
  }

  findTipiDocumento(): Observable<Array<Decodifica>> {
    let url = this.configService.getApiURL() + '/restfacade/gestionetemplates/tipididocumento';
    return this.http.get<Array<Decodifica>>(url);
  }

  findModelli(progrBandolinea: number, idTipoDocumento: number): Observable<Array<TemplateDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/gestionetemplates/modelli';
    let params = new HttpParams();
    if (progrBandolinea) {
      params = params.set("progrBandolinea", progrBandolinea.toString());
    }
    if (idTipoDocumento) {
      params = params.set("idTipoDocumento", idTipoDocumento.toString());
    }
    return this.http.get<Array<TemplateDTO>>(url, { params: params });
  }

  anteprimaTemplate(progrBandolinea: number, idTipoDocumento: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestionetemplates/anteprimaTemplate';
    let params = new HttpParams();
    if (progrBandolinea) {
      params = params.set("progrBandolinea", progrBandolinea.toString());
    }
    if (idTipoDocumento) {
      params = params.set("idTipoDocumento", idTipoDocumento.toString());
    }
    return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
  }

}
