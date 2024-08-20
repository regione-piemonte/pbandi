/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ConfigService } from "src/app/core/services/config.service";
import { FiltroGestioneGaranzie } from "src/app/gestione-garanzie/commons/filtro-gestione-garanzie";
import { GaranziaVO } from '../commons/garanzia-vo';
import { DatiGaranziaVO } from '../commons/dati-garanzia-vo';
import { InfoDettaglioRicercaGaranzieVO } from '../commons/info-dettaglio-ricerca-garanzie-vo';

@Injectable()
export class GestioneGaranzieService {
  constructor(
    private configService: ConfigService,
    private http: HttpClient
  ) { }


  initRicercaGaranzie() {
    let url = this.configService.getApiURL() + '/restfacade/gestioneGaranzie/initRicercaGaranzie';
    return this.http.get<Array<String>>(url);
  }
  
  getSuggestion(value: string, id: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneGaranzie/getSuggestions';
    let params = new HttpParams().set("value", value.toString().toUpperCase()).set("id", id.toString());
    return this.http.get<any>(url, { params: params });
  }

  cercaGaranzie(descrizioneBando = "", codiceProgetto = "", codiceFiscale = "", nag = "", partitaIva = "", denominazioneCognomeNome = "", statoEscussione = "", denominazioneBanca = "") {
    let url = this.configService.getApiURL() + '/restfacade/gestioneGaranzie/ricercaGaranzie';
    let params = new HttpParams()
      .set("descrizioneBando", descrizioneBando.toString())
      .set("codiceProgetto", codiceProgetto.toString())
      .set("codiceFiscale", codiceFiscale.toString())
      .set("ndg", nag.toString())
      .set("partitaIva", partitaIva.toString())
      .set("denominazioneCognomeNome", denominazioneCognomeNome.toString())
      .set("statoEscussione", statoEscussione.toString())
      .set("denominazioneBanca", denominazioneBanca.toString());

    return this.http.get<Array<DatiGaranziaVO>>(url, { params: params })
  }

  getDebitoResiduo(idProgetto: number, idBando: number, idModalitaAgevolazioneOrig: number, idModalitaAgevolazioneRif: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneGaranzie/getDebitoResiduo';

    let params = new HttpParams()
      .set("idProgetto", idProgetto.toString())
      .set("idBando", idBando.toString())
      .set("idModalitaAgevolazioneOrig", idModalitaAgevolazioneOrig.toString())
      .set("idModalitaAgevolazioneRif", idModalitaAgevolazioneRif.toString())

    return this.http.get<any>(url, { params: params });
  }
  

  getDettaglioRevocaBancaria(idProgetto: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneGaranzie/getDettaglioRevocaBancaria';
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<InfoDettaglioRicercaGaranzieVO>(url, { params: params });
  }
  
  getDettaglioAzioniRecuperoBanca(idProgetto: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneGaranzie/getDettaglioAzioneRecuperoBanca';
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<InfoDettaglioRicercaGaranzieVO>>(url, { params: params });
  }
  
  getDettaglioSaldoStralcio(idProgetto: number) {
    let url = this.configService.getApiURL() + '/restfacade/gestioneGaranzie/getDettaglioSaldoStralcio';
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<Array<InfoDettaglioRicercaGaranzieVO>>(url, { params: params });
  }

  
}