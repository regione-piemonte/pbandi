/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { FileDTO } from "src/app/dati-progetto/commons/dto/file-dto";
import { EsitoAssociaFilesDTO } from "../commons/dto/esito-associa-files-dto";
import { InizializzaErogazioneDTO } from "../commons/dto/inizializza-erogazione-dto";
import { EsitoOperazioni } from "../commons/models/esito-operazioni";
import { AssociaFilesRequest } from "../commons/requests/associa-files-request";

@Injectable()
export class Erogazione2Service {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  associaAllegatiARichiestaErogazione(request: AssociaFilesRequest): Observable<EsitoAssociaFilesDTO> {
    let url = this.configService.getApiURL() + "/restfacade/erogazione/associaAllegatiARichiestaErogazione";
    return this.http.post<EsitoAssociaFilesDTO>(url, request);
  }

  getFilesAssociatedRichiestaErogazioneByIdProgetto(idProgetto: number): Observable<Array<FileDTO>> {
    let url = this.configService.getApiURL() + "/restfacade/erogazione/getFilesAssociatedRichiestaErogazioneByIdProgetto";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<FileDTO>>(url, { params: params });
  }

  disassociaAllegatiARichiestaErogazione(idProgetto: number, idErogazione: number, idDocumentoIndex: number): Observable<EsitoOperazioni> {
    let url = this.configService.getApiURL() + "/restfacade/erogazione/disassociaAllegatiARichiestaErogazione";
    let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idDocumentoIndex", idDocumentoIndex.toString());
    if (idErogazione) {
      params = params.set("idErogazione", idErogazione.toString());
    }
    return this.http.get<EsitoOperazioni>(url, { params: params });
  }

  inizializzaErogazione(idProgetto: number): Observable<InizializzaErogazioneDTO> {
    let url = this.configService.getApiURL() + "/restfacade/erogazione/inizializzaErogazione";
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<InizializzaErogazioneDTO>(url, { params: params });
  }

}
