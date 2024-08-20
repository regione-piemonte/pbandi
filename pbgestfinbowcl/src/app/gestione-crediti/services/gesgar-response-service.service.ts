/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { FinanziamentoErogato } from '../commons/dto/finanziamento-erogato';
import { DettaglioFinanziamentoErogato } from '../commons/dto/dettaglio-finanziamento-erogato';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { of } from 'rxjs';
import { GaranziaVO } from 'src/app/gestione-garanzie/commons/garanzia-vo';


@Injectable({
  providedIn: 'root'
})

export class GesGarResponseService {
  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
  ) { }
  /*finEro: FinanziamentoErogato;
  dettFinEro: DettaglioFinanziamentoErogato;
  private finEroInfo = new BehaviorSubject<FinanziamentoErogato>(null);
  finEroInfo$: Observable<FinanziamentoErogato> = this.finEroInfo.asObservable();*/

  /*cercaGaranzie(descrizioneBando = "", codiceProgetto = "", codiceFiscale = "", nag = "", partitaIva = "", denominazioneCognomeNome = "", statoEscussione = "", denominazioneBanca = "") {
    let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/ricercaGaranzie';
    let params = new HttpParams()
      .set("descrizioneBando", (descrizioneBando!=null) ? descrizioneBando.toString():  null)
      .set("codiceProgetto", codiceProgetto.toString())
      .set("codiceFiscale", codiceFiscale.toString())
      .set("nag", nag.toString())
      .set("partitaIva", partitaIva.toString())
      .set("denominazioneCognomeNome", denominazioneCognomeNome.toString())
      .set("statoEscussione", statoEscussione.toString())
      .set("denominazioneBanca", denominazioneBanca.toString()); 

    return this.http.get<Array<FinanziamentoErogato>>(url, { params: params })
  }*/

  /* Non usato da nessuna parte
  cercaDettagliBeneficiari(idProgetto: number) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getVisualizzaDettaglioGaranzia';
    let params = new HttpParams().set("idProgetto", idProgetto.toString());
    return this.http.get<DettaglioFinanziamentoErogato>(url, { params: params });
  }*/

  
  getGaranzia(idProgetto: any, idEscussione: any): Observable <GaranziaVO>  {
    let url = this.configService.getApiURL() + '/restfacade/ricercaGaranzie/getGaranzia';
     let params = new HttpParams()
    .set("idEscussione", idEscussione.toString())
    .set("idProgetto", idProgetto.toString());
    
    
    return this.http.get<any>(url, { params: params });
  }

}