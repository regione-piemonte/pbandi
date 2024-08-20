/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { ConfigService } from 'src/app/core/services/config.service';
import { AllegatiContestazioniVO } from '../commons/dto/allegatiContestazione';
import { ContestazioniVO } from '../commons/dto/contestazioni';


@Injectable({
  providedIn: 'root'
})
export class ContestazioniService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient) { }


   getContestazioni(idProgetto) : Observable <Array<ContestazioniVO>>  {
      let url = this.configService.getApiURL() + '/restfacade/contestazioni/getContestazioni';
      let params = new HttpParams().set('idProgetto', idProgetto);
      return this.http.get<Array<ContestazioniVO>>(url, { params: params });
  } 


  getCodiceProgetto(idProgetto) {
    let url = this.configService.getApiURL() + '/restfacade/contestazioni/getCodiceProgetto';
    let params = new HttpParams().set('idProgetto', idProgetto);
    return this.http.get<any[]>(url, { params: params });
} 



 /*  getRecupProvRevoca(idProgetto){
    let url = this.configService.getApiURL() + '/restfacade/contestazioni/getRecupProvRevoca';
      let params = new HttpParams().set('idProgetto', idProgetto);
      return this.http.get<any[]>(url, { params: params });
  } */

  


  getAllegati(idContestazione) {
    let url = this.configService.getApiURL() + '/restfacade/contestazioni/getAllegati';
    let params = new HttpParams().set('idContestazione', idContestazione.toString());
    return this.http.get<any[]>(url, { params: params });
  }

 
 deleteAllegato(idFileEntita:number){
  let url = this.configService.getApiURL() + `/restfacade/contestazioni/deleteAllegato/${idFileEntita}`;
  return this.http.post<any>(url,idFileEntita)
  
}


  inserisciLetteraAllegato(datiBackend:AllegatiContestazioniVO[],idContestazione:number){
    let url = this.configService.getApiURL() + `/restfacade/contestazioni/inserisciLetteraAllegato/${idContestazione}`;
    return this.http.post<any>(url,datiBackend)
    
  }

  inserisciContestazione(idGestioneRevoca:number){
    let url = this.configService.getApiURL() + `/restfacade/contestazioni/inserisciContestazione/${idGestioneRevoca}`;
    return this.http.post<any>(url,idGestioneRevoca)
    
  }


 /*  aggiornaContestazione(datiBackend){
    let url = this.configService.getApiURL() + '/restfacade/contestazioni/aggiornaContestazione';
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post<any>(url,datiBackend,{headers})
    
  } */

  inviaContestazione(idContestazione:number){
    let url = this.configService.getApiURL() + `/restfacade/contestazioni/inviaContestazione/${idContestazione}`;
    return this.http.post<any>(url,idContestazione)
    
  }

  

 

  eliminaContestazione(idContestazione:number){
    let url = this.configService.getApiURL() + `/restfacade/contestazioni/eliminaContestazione/${idContestazione}`;
    return this.http.post<any>(url,idContestazione)
    
  }

 
}
