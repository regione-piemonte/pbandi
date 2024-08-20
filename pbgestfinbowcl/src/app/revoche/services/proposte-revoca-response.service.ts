/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { Observable } from 'rxjs';
import { PropostaRevocaVO } from '../commons/proposte-revoca-dto/proposta-revoca-vo';
import { CausaleBloccoSuggestVO } from '../commons/proposte-revoca-dto/causale-blocco-suggest-vo';
import { InfoRevocaVO } from '../commons/proposte-revoca-dto/info-revoca-vo';
import { FiltroPropostaRevocaVO } from '../commons/proposte-revoca-dto/filtro-proposta-revoca-vo';
import { AutoritaControllanteVO } from '../commons/proposte-revoca-dto/autorita-controllante-vo';
import { ImportiRevocaVO } from '../commons/proposte-revoca-dto/importi-revoca-vo';
import { DenominazioneSuggestVO } from '../commons/denominazione-suggest-vo';

@Injectable({
  providedIn: 'root'
})
export class ProposteRevocaResponseService {

  constructor(
    private http: HttpClient,
    private configService: ConfigService
    ) { }


  /**********************************
  ******** PROPOSTA REVOCA  *********
  **********************************/

  cerca(filtroPropostaRevocaVO: FiltroPropostaRevocaVO): Observable<Array<PropostaRevocaVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getProposteRevoche';

    return this.http.post<Array<PropostaRevocaVO>>(url, filtroPropostaRevocaVO);
  }


 /**********************************
  ******** MODIFICA REVOCA  *********
  **********************************/

  getInfoRevoca(idGestioneRevoca: string, idSoggetto: string, idDomanda: string): Observable<InfoRevocaVO> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getInfoRevoche';
    let params = new HttpParams()
    .set("id_gestione_revoca", idGestioneRevoca.toString().toUpperCase())
    .set("id_soggetto", idSoggetto.toString().toUpperCase())
    .set("id_domanda", idDomanda.toString().toUpperCase())
    return this.http.get<InfoRevocaVO>(url, { params: params });
  }

  getImportiRevoche(idGestioneRevoca: string): Observable<Array<ImportiRevocaVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getImportiRevoche';
    let params = new HttpParams()
    .set("id_gestione_revoca", idGestioneRevoca.toString().toUpperCase())
    return this.http.get<Array<ImportiRevocaVO>>(url, { params: params });
  }

  getNotaRevoche(idGestioneRevoca: string): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getNotaRevoche';
    let params = new HttpParams()
    .set("id_gestione_revoca", idGestioneRevoca.toString().toUpperCase())
    return this.http.get(url, { params: params });
  }

  updateNotaRevoca(note, idGestioneRevoca): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/updateNotaRevoche';
    let params = new HttpParams()
    .set("id_gestione_revoca", idGestioneRevoca.toString());
    return this.http.put<any>(url, note, { params });
  }

  archiviaRevoca(archivioRevocaVO ,idUtente): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/archiviaPropostaRevoca';
    let params = new HttpParams()
    .set("idUtente", idUtente.toString());
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<any>(url, archivioRevocaVO, { params: params });
  }

  creaBozzaProcedimentoRevoca( idGestioneRevoca,idUtente): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/creaBozzaProcedimentoRevoca';
    let formData = new FormData();
        formData.set("idGestioneRevoca", idGestioneRevoca);
        formData.set("idUtente", idUtente);
    return this.http.post<any>(url, formData);
  }

  creaBozzaProvvedimentoRevoca( idGestioneRevoca,idUtente): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/creaBozzaProvvedimentoRevoca';
    let formData = new FormData();
        formData.set("idGestioneRevoca", idGestioneRevoca);
        formData.set("idUtente", idUtente);
    return this.http.post<any>(url, formData);
  }

  getDatiList(cod_ateco:string) : Observable<any>{
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/getDatiList';
    let params = new HttpParams()
    .set('cod_ateco', cod_ateco);
    return this.http.get(url, {
      params: params,
    });
  }


  /**********************************
  ********  NUOVA  REVOCA  *********
  **********************************/

  nuovaPropostaRevoca(datiBackEnd: any): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/creaPropostaRevoca';
    //let headers = new HttpHeaders().set('Content-Type', 'application/json');
    /*
    let params = new HttpParams()
    .set("numeroRevoca", datiBackEnd.numeroRevoca.toString())
    .set("idProgetto", datiBackEnd.idProgetto.toString())
    .set("idCausaleBlocco", datiBackEnd.idCausaleBlocco.toString())
    .set("idAutoritaControllante", datiBackEnd.idAutoritaControllante?.toString())
    .set("dataPropostaRevoca", datiBackEnd.dataPropostaRevoca.toString());
    */
    return this.http.post<any>(url, datiBackEnd);
  }

  getNumeroPropostaRevoche(): Observable<number> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/newNumeroRevoca';
    let params = new HttpParams()
    return this.http.get<number>(url, { params: params });
  }

  getCauseProposteRevoca() : Observable<Array<DenominazioneSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestAllCauseRevoche';
    let params = new HttpParams()
    return this.http.get<Array<DenominazioneSuggestVO>>(url, { params: params });
  }

  getAutoritaControllante() : Observable<Array<DenominazioneSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestAllAutoritaControllanti';
    let params = new HttpParams()
    return this.http.get<Array<DenominazioneSuggestVO>>(url, { params: params });
  }

}
