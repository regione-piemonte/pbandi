/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { SchedaClienteMain } from '../commons/dto/scheda-cliente-main';
import { BehaviorSubject, Observable } from 'rxjs';
import { SaveSchedaCliente } from '../commons/dto/save-scheda-cliente.all';


@Injectable({
  providedIn: 'root'
})
export class ModBenResponseService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
  ) { }



  getSchedaCliente(idProgetto,idModalitaAgevolazione) {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getSchedaCliente';
    let params = new HttpParams()
    .set("idProgetto", idProgetto.toString())
    .set("idModalitaAgevolazione", idModalitaAgevolazione);
    return this.http.get<SchedaClienteMain>(url, { params: params })
  }

  /*getListBanche() {
    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getListBanche';

    //let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("idUtente", idUtente);

    return this.http.get<Array<string>>(url)
  }*/

  //newSchedaCliente: SaveSchedaCliente;
  jsonModifiche: string;
  setSchedaCliente(newSchedaCliente: SaveSchedaCliente, idModalitaAgevolazione: any, idProgetto: string, flagStatoAzienda: boolean = false) {

    console.log("after: ", newSchedaCliente);

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/setSchedaCliente';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.jsonModifiche = JSON.stringify(newSchedaCliente);
    let params = new HttpParams().set("idModalitaAgevolazione", idModalitaAgevolazione.toString()).set("flagStatoAzienda", flagStatoAzienda.toString()).set("idProgetto", idProgetto.toString());

    return this.http.post<String>(url, this.jsonModifiche,  {headers: headers, params: params})
  }

  getSuggestion(value: string) {

    let url = this.configService.getApiURL() + '/restfacade/ricercaBeneficiariCrediti/getListBanche';

    let params = new HttpParams().set("value", value.toString().toUpperCase());

    return this.http.get<any>(url, { params: params });
  }

}
