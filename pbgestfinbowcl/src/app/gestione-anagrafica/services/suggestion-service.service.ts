/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Injectable({
  providedIn: 'root'
})
export class SuggestionService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
  ) { }

  getSuggestionIdSoggetto(idSoggetto: any) {

    let url = this.configService.getApiURL() + '/restfacade/suggestion/getIdSoggetto';

    let params = new HttpParams().set("idSoggetto", idSoggetto.toString())

    return this.http.get<any>(url, { params: params });

  }


  getSuggestionCF(cf: string) {

    let url = this.configService.getApiURL() + '/restfacade/suggestion/getCodiceFiscale';

    let params = new HttpParams().set("codiceFiscale", cf.toString().toUpperCase());

    return this.http.get<any>(url, { params: params });

  }


  getSuggestionPIva(pIva: string) {

    let url = this.configService.getApiURL() + '/restfacade/suggestion/getPartitaIva';

    let params = new HttpParams().set("partitaIva", pIva.toString());

    return this.http.get<any>(url, { params: params });

  }

  getSuggestionDenominazione(denominazione: string) {

    let url = this.configService.getApiURL() + '/restfacade/suggestion/getDenominazione';

    let params = new HttpParams().set("denominazione", denominazione.toString().toUpperCase());

    return this.http.get<any>(url, { params: params });

  }

  getSuggestionCognome(denominazione: string) {

    let url = this.configService.getApiURL() + '/restfacade/suggestion/getCognome';

    let params = new HttpParams().set("cognome", denominazione.toString().toUpperCase());

    return this.http.get<any>(url, { params: params });

  }


  getSuggestionNumeroDomanda(numeroDomanda: string) {

    let url = this.configService.getApiURL() + '/restfacade/suggestion/getIdDomanda';

    let params = new HttpParams().set("numeroDomanda", numeroDomanda.toString());

    return this.http.get<any>(url, { params: params });

  }


  getSuggestionCodiceProgetto(codProgetto: string) {

    let url = this.configService.getApiURL() + '/restfacade/suggestion/getIdProgetto';

    let params = new HttpParams().set("codProgetto", codProgetto.toString());

    return this.http.get<any>(url, { params: params });

  }

  getAutofill(idSoggetto: number, tipoSoggetto: string, idDomanda:  any,  idProgetto: any) {

    // se non passo niente fra id_domanda e idProgetto mi va a fare l'autofill prendendo l'ultima domanda. 
    let url = this.configService.getApiURL() + '/restfacade/suggestion/getAutofill';

    let params = new HttpParams()
      .set("idSoggetto", idSoggetto.toString())
      .set("tipoSoggetto", tipoSoggetto.toString())
      .set("idDomanda", idDomanda? idDomanda.toString(): 0)
      .set("idProgetto",idProgetto? idProgetto.toString(): 0)

    return this.http.get<any>(url, { params: params });

  }


  cercaSoggetti(cf?, idSoggetto?, pIva?, denominazione?, idDomanda?, idProgetto?, nome?, cognome?, descTipoSogg?) {

    if (descTipoSogg == "EG") {
      let url = this.configService.getApiURL() + '/restfacade/cerca/getBeneficiarioEg';
      let params = new HttpParams()
        .set("cf", cf)
        .set("idSoggetto", idSoggetto)
        .set("pIva", pIva)
        .set("denominazione", denominazione)
        .set("idDomanda", idDomanda)
        .set("idProgetto", (idProgetto!=null) ? idProgetto.toString() : 0)

      return this.http.get<any>(url, { params: params });
    } else {
      let url = this.configService.getApiURL() + '/restfacade/cerca/getBeneficiarioPf';
      let params = new HttpParams()
        .set("cf", cf)
        .set("idSoggetto", idSoggetto)
        .set("idDomanda", idDomanda)
        .set("idProgetto", (idProgetto!=null) ? idProgetto.toString() : 0)
        .set("nome", nome)
        .set("cognome", cognome)
        
      return this.http.get<any>(url, { params: params });

    }
  }
}
