/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { DenominazioneSuggestVO } from '../commons/denominazione-suggest-vo';
import { FiltroRevocaVO } from '../commons/filtro-revoca-vo';

@Injectable({
  providedIn: 'root'
})
export class SuggestResponseService {

  constructor(
    private http: HttpClient,
    private configService: ConfigService
  ) { }

   /**********************************
  ******** PROPOSTA REVOCA  *********
  **********************************/

  suggestBeneficiario(value: string, idBandoLineaIntervent: string): Observable<Array<DenominazioneSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestBeneficiario';
    if(idBandoLineaIntervent == undefined){
      idBandoLineaIntervent = '-1';
    }
    let params = new HttpParams()
    .set("value", value.toString().toUpperCase())
    .set("progrBandoLineaIntervent", idBandoLineaIntervent)
    return this.http.get<Array<DenominazioneSuggestVO>>(url, { params: params });
  }

  suggestBando(value: string, idSoggetto: string): Observable<Array<DenominazioneSuggestVO>> { //BandoLineaInterventoVO
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestBando';
    if(idSoggetto == undefined){
      idSoggetto = '-1';
    }
    let params = new HttpParams()
    .set("value", value.toString().toUpperCase())
    .set("idSoggetto", idSoggetto)
    return this.http.get<Array<DenominazioneSuggestVO>>(url, { params: params });
  }

  suggestProgetto(value: string, idSoggetto: string, idBando: string): Observable<Array<DenominazioneSuggestVO>> { //ProgettoSuggestVO
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestProgetto';
    let params = new HttpParams()
    .set("value", value.toString().toUpperCase())
    .set("idSoggetto", idSoggetto)
    .set("progrBandoLineaIntervent", idBando)
    return this.http.get<Array<DenominazioneSuggestVO>>(url, { params: params }); //ProgettoSuggestVO
  }

  //mi restituisce tutte le causali blocco suggest che rispettano i filtri che passo come parametro
  /* suggestCausaleBlocco(filtroPropostaRevocaVO: FiltroPropostaRevocaVO): Observable<Array<CausaleBloccoSuggestVO>> {

    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestCausaleBlocco';

    return this.http.post<Array<CausaleBloccoSuggestVO>>(url, filtroPropostaRevocaVO);
  } */

  suggestCausaRevoche(value: FiltroRevocaVO): Observable<Array<DenominazioneSuggestVO>> {

    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestCausaRevoche';

    return this.http.post<Array<DenominazioneSuggestVO>>(url, value);
  }

  /* suggestStatoRevoca(filtroPropostaRevocaVO: FiltroPropostaRevocaVO): Observable<Array<DenominazioneSuggestVO>> {

    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestStatoRevoca';

    return this.http.post<Array<DenominazioneSuggestVO>>(url, filtroPropostaRevocaVO);
  } */

  suggestStatoRevoche(filtroPropostaRevocaVO: FiltroRevocaVO): Observable<Array<DenominazioneSuggestVO>> {

    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestStatoRevoche';

    return this.http.post<Array<DenominazioneSuggestVO>>(url, filtroPropostaRevocaVO);
  }

  /* suggestAttivitaProcedimentoRevoca(filtroProcedimentoRevocaVO: FiltroProcedimentoRevocaVO): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestAttivitaProcedimentoRevoche';
    return this.http.post<Array<SuggestIdDescVO>>(url, filtroProcedimentoRevocaVO);
  } */

  suggestAttivitaRevoche(filtroProcedimentoRevocaVO: FiltroRevocaVO): Observable<Array<DenominazioneSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestAttivitaRevoche';
    return this.http.post<Array<DenominazioneSuggestVO>>(url, filtroProcedimentoRevocaVO);
  }

  /* suggestNumeroProposta(value: string): Observable<Array<string>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestNumeroProposta';
    let params = new HttpParams()
    .set("value", value.toString().toUpperCase())
    return this.http.get<Array<string>>(url, { params: params });
  } */

  suggestNumeroRevoche(value: FiltroRevocaVO): Observable<Array<DenominazioneSuggestVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestNumeroRevoche';
    // let params = new HttpParams()
    //.set("value", value.toString().toUpperCase())
    return this.http.post<Array<DenominazioneSuggestVO>>(url, value);
  }




  /**********************************
  ******** PROCEDIMENTO REVOCA  *********
  **********************************/
/*
  suggestCausaProcedimentoRevoca(filtroProcedimentoRevocaVO: FiltroProcedimentoRevocaVO): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestCausaProcedimentoRevoche';
    return this.http.post<Array<SuggestIdDescVO>>(url, filtroProcedimentoRevocaVO);
  }

  suggestStatoProcedimentoRevoca(filtroProcedimentoRevocaVO: FiltroProcedimentoRevocaVO): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestStatoProcedimentoRevoche';
    return this.http.post<Array<SuggestIdDescVO>>(url, filtroProcedimentoRevocaVO);
  }

  suggestAttivitaProcedimentoRevoca(filtroProcedimentoRevocaVO: FiltroProcedimentoRevocaVO): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestAttivitaProcedimentoRevoche';
    return this.http.post<Array<SuggestIdDescVO>>(url, filtroProcedimentoRevocaVO);
  } */

 /*  //NumeroRevocaSuggestVO
  suggestNumeroProcedimentoRevoca(numeroProcedimento: string): Observable<Array<string>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestNumeroProcedimentoRevoche';
    let params = new HttpParams()
    .set("numeroProcedimento", numeroProcedimento.toString())
    return this.http.get<Array<string>>(url, { params: params });
  } */





  /**********************************
  ******** PROVVEDIMENTO REVOCA  *********
  **********************************/
/*
  suggestCausaProvvedimentoRevoca(filtroProvvedimentoRevocaVO: FiltroProvvedimentoRevocaVO): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestCausaProvvedimentoRevoche';
    return this.http.post<Array<SuggestIdDescVO>>(url, filtroProvvedimentoRevocaVO);
  }

  suggestStatoProvvedimentoRevoca(filtroProvvedimentoRevocaVO: FiltroProvvedimentoRevocaVO): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestStatoProvvedimentoRevoche';
    return this.http.post<Array<SuggestIdDescVO>>(url, filtroProvvedimentoRevocaVO);
  }

  suggestAttivitaProvvedimentoRevoca(filtroProvvedimentoRevocaVO: FiltroProvvedimentoRevocaVO): Observable<Array<SuggestIdDescVO>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestAttivitaProvvedimentoRevoche';
    return this.http.post<Array<SuggestIdDescVO>>(url, filtroProvvedimentoRevocaVO);
  } */
/*
  //NumeroRevocaSuggestVO
  suggestNumeroProvvedimentoRevoca(numeroProvvedimento: string): Observable<Array<string>> {
    let url = this.configService.getApiURL() + '/restfacade/ambitoRevoche/suggestNumeroProvvedimentoRevoche';
    let params = new HttpParams()
    .set("numeroProvvedimento", numeroProvvedimento.toString())
    return this.http.get<Array<string>>(url, { params: params });
  } */

}
