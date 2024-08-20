/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { QteHtmlDTO } from '../commons/dto/qte-html-dto';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { QteProgettoDTO } from '../commons/dto/qte-progetto-dto';
import { QteFaseDTO } from '../commons/dto/qte-fase-dto';
import { DatiCccDTO } from '../commons/dto/dati-ccc-dto';

@Injectable({
  providedIn: 'root'
})
export class ContoEconomicoWaService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }

  getModelloQte(progrBandoLineaIntervento: number): Observable<QteHtmlDTO> {
    let url = this.configService.getApiURL() + "/restfacade/contoEconomicoWa/modelloQte";
    let params = new HttpParams().set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
    return this.http.get<QteHtmlDTO>(url, { params: params });
  }

  getQteProgetto(idProgetto: number): Observable<QteHtmlDTO> {
    let url = this.configService.getApiURL() + "/restfacade/contoEconomicoWa/qteProgetto";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<QteHtmlDTO>(url, { params: params });
  }

  salvaQteProgetto(request: QteProgettoDTO): Observable<EsitoDTO> {
    let url = this.configService.getApiURL() + "/restfacade/contoEconomicoWa/salvaQteProgetto";
    return this.http.post<EsitoDTO>(url, request);
  }

  getColonneModelloQte(progrBandoLineaIntervento: number): Observable<Array<QteFaseDTO>> {
    let url = this.configService.getApiURL() + "/restfacade/contoEconomicoWa/colonneModelloQte";
    let params = new HttpParams().set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
    return this.http.get<Array<QteFaseDTO>>(url, { params: params });
  }

  getDatiCCC(progrBandoLineaIntervento: number, idProgetto: number): Observable<DatiCccDTO> {
    let url = this.configService.getApiURL() + "/restfacade/contoEconomicoWa/datiCCC";
    let params = new HttpParams().set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString()).set("idProgetto", idProgetto.toString());
    return this.http.get<DatiCccDTO>(url, { params: params });
  }

  salvaCCCDefinitivo(file: File, idProgetto: number, idQtesHtmlProgetto: number): Observable<boolean> {
    let url = this.configService.getApiURL() + "/restfacade/contoEconomicoWa/salvaCCCDefinitivo";
    let formData = new FormData();
    formData.append("idProgetto", idProgetto.toString());
    formData.append("idQtesHtmlProgetto", idQtesHtmlProgetto.toString());
    formData.append("file", file, file.name);
    return this.http.post<boolean>(url, formData);
  }

  getIdDocumentoIndexCCC(idProgetto: number, idQtesHtmlProgetto: number): Observable<number> {
    let url = this.configService.getApiURL() + "/restfacade/contoEconomicoWa/idDocumentoIndexCCC";
    let params = new HttpParams().set("idQtesHtmlProgetto", idQtesHtmlProgetto.toString()).set("idProgetto", idProgetto.toString());
    return this.http.get<number>(url, { params: params });
  }
}
