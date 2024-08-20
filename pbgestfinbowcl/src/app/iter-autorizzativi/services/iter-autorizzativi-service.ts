/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { Observable } from 'rxjs';
import { SuggStatoIterVO } from '../commons/sugg-stato-iter-vo';
import { ParametroIterAutorizzativoVO } from '../commons/parametro-iter-autorizzativo-vo';
import { ListaIterVO } from '../commons/lista-iter-vo';
import { SuggestIterVO } from '../commons/suggestIter-vo';
import { IterAutorizzativiVO } from '../commons/iter-autorizzativi-vo';
import { DettaglioIterVO } from '../commons/dettaglio-iter-vo';

@Injectable({
  providedIn: 'root'
})
export class IterAutorizzativiService {

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { }

  /* cercaIterAutorizzativi(filtroPropostaRevocaVO: any): Observable<Array<any>> {
     let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/cercaIterAutorizzativi';
     return this.http.post<Array<any>>(url, filtroPropostaRevocaVO);
   } */


  cercaIterAutorizzativi(datiBackend: ParametroIterAutorizzativoVO) : Observable<Array<ListaIterVO>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/cercaIterAutorizzativi';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<Array<ListaIterVO>>(url, datiBackend, { headers })

  }

  suggerimentoIter(datiBackEnd : IterAutorizzativiVO) : Observable<Array<SuggestIterVO>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/suggestIter';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<Array<SuggestIterVO>>(url, datiBackEnd, { headers })
  }

  suggestBeneficiario(value: string): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/suggestBeneficiario';
    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())
    return this.http.get<Array<any>>(url, { params: params });
  }

  suggestBando(value: string, idSoggetto: string): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/suggestBando';
    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())
      .set("idSoggetto", idSoggetto.toString())
    return this.http.get<Array<any>>(url, { params: params });
  }

  suggestProgetto(value: string, idSoggetto: string, idBando: string): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/suggestProgetto';
    let params = new HttpParams()
      .set("value", value.toString().toUpperCase())
      .set("idSoggetto", idSoggetto.toString())
      .set("idBando", idBando.toString())
    return this.http.get<Array<any>>(url, { params: params });
  }

  getStatoIter(): Observable<Array<SuggStatoIterVO>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/getStatoIter';
    let params = new HttpParams()
    return this.http.get<Array<SuggStatoIterVO>>(url, { params: params });
  }

  /* getTendinaBando(): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/getTendinaBando';
    let params = new HttpParams()
    return this.http.get<Array<any>>(url, { params: params });
  }

  getTendinaProgetto(): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/getTendinaProgetto';
    let params = new HttpParams()
    return this.http.get<Array<any>>(url, { params: params });
  }

  getTendinaBeneficiario(): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/getTendinaBeneficiario';
    let params = new HttpParams()
    return this.http.get<Array<any>>(url, { params: params });
  } */

  respingiIter(datiBackend) {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/respingiIter';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<any>(url, datiBackend, { headers })

  }

  autorizzaIter(datiBackend) {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/autorizzaIter';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<any>(url, datiBackend, { headers })

  }

  getAllegatiIter(idWorkFlow: string): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/getAllegatiIter';
    let params = new HttpParams()
      .set("idWorkFlow", idWorkFlow.toString().toUpperCase())
    return this.http.get<Array<any>>(url, { params: params });
  }

  getExcelIterDistinte(idDistinta: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/distinte/esportaDettaglioDistinta';
    let params = new HttpParams()
      .set("idDistinta", idDistinta)
    return this.http.get(url, { responseType: 'arraybuffer', params });
  }

  getExcelIterDichiarazioneDiSpesa(idDichiarazioneDiSpesa: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/reportDettaglioDocumentoDiSpesa';
    let params = new HttpParams()
      .set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa)
    return this.http.get(url, { responseType: 'arraybuffer', params });
  }


  reportDettaglioDocumentoDiSpesa(idDichiarazioneDiSpesa: number) {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/reportDettaglioDocumentoDiSpesa';
    let params = new HttpParams().set("idDichiarazioneDiSpesa", idDichiarazioneDiSpesa.toString());
    return this.http.get(url, { params: params, responseType: 'blob', observe: 'response' });
    /* return this.http.get(url, { params: params, responseType: 'blob' }).pipe(map(
        (res) => {
            return new Blob([res]);
        })); */
  }

  /* respingiIter(datiBackEnd ,idUtente): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/archiviaPropostaRevoca';
    let params = new HttpParams()
    .set("idUtente", idUtente.toString());
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<any>(url, datiBackEnd, { params: params });
  } */

  postAllegato(record, file: File): Observable<any> {
    let formData = new FormData();
    formData.set("id_domanda", record.id_domanda + '');
    formData.set("id_work_flow", record.id_work_flow + '');
    formData.set("id_provvedimento", record.id_provvedimento + '');
    formData.set("id_azione", record.id_azione + '');
    formData.set("file", file, file.name);
    return this.http.post<any>(`${this.configService.getApiURL()}/restfacade/iterAutorizzativi/postAllegato`, formData);
  }

  getDettaglioIter(idWorkFlow: string): Observable<Array<DettaglioIterVO>> {
    let url = this.configService.getApiURL() + '/restfacade/iterAutorizzativi/getDettaglioIter';
    let params = new HttpParams()
      .set("idWorkFlow", idWorkFlow.toString().toUpperCase())
    return this.http.get<Array<DettaglioIterVO>>(url, { params: params });
  }




}
