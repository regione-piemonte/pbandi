/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { ResponseCodeMessage } from 'src/app/shared/commons/dto/response-code-message-dto';
import { BoStorageDocumentoIndexDTO } from '../commons/dto/bo-storage-documento-index-dto';

@Injectable({
  providedIn: 'root'
})
export class BoStorageService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
  ) { }

  getTipiDocIndex(): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/boStorage/getTipiDocIndex';
    return this.http.get<any>(url);
  }

  getProgettiByDesc(descrizione: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/boStorage/getProgettiByDesc';
    let params = new HttpParams().set("descrizione", descrizione);
    return this.http.get<any>(url, { params: params });
  }

  ricercaDocumenti(nomeFile: string, idTipoDocumentoIndex: number, idProgetto: number): Observable<Array<BoStorageDocumentoIndexDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/boStorage/ricercaDocumenti';
    let params = new HttpParams();
    if (nomeFile) {
      params = params.set("nomeFile", nomeFile);
    }
    if (idTipoDocumentoIndex) {
      params = params.set("idTipoDocumentoIndex", idTipoDocumentoIndex.toString());
    }
    if (idProgetto) {
      params = params.set("idProgetto", idProgetto?.toString());
    }
    return this.http.get<Array<BoStorageDocumentoIndexDTO>>(url, { params: params });
  }

  sostituisciDocumento(file: File, descrizioneBreveTipoDocIndex: string, idDocumentoIndex: number, flagFirmato: boolean, flagMarcato: boolean, flagFirmaAutografa: boolean)
    : Observable<ResponseCodeMessage> {
    let url = this.configService.getApiURL() + '/restfacade/boStorage/sostituisciDocumento';
    let formData = new FormData();
    formData.append("descrizioneBreveTipoDocIndex", descrizioneBreveTipoDocIndex);
    formData.append("idDocumentoIndex", idDocumentoIndex.toString());
    formData.append("flagFirmato", flagFirmato.toString());
    formData.append("flagMarcato", flagMarcato.toString());
    formData.append("flagFirmaAutografa", flagFirmaAutografa.toString());
    formData.append("file", file, file.name);
    return this.http.post<ResponseCodeMessage>(url, formData);

  }
}
