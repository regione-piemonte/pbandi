/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { ConsoleMonitoraggioFilter } from '../commons/dto/ConsoleMonitoraggioFilter';
import { DatePipe } from '@angular/common';

@Injectable()
export class ConsoleApplicativaService {

  constructor(  private configService: ConfigService,
    private http: HttpClient) { }




    findTipoErrore(): Observable<any> {
      let url = this.configService.getApiURL() + '/restfacade/consoleMonitoriaggioServizi/findErrorMessage';
      //LOG
      console.log("[console applicativa service -- findTipoErrore] \nChiamata avviata \n")
      return this.http.get<any>(url, { params: null });

  }

  findServizi(): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/consoleMonitoriaggioServizi/getServizi';
    //LOG
    console.log("[console applicativa service -- findTipoErrore] \nChiamata avviata \n")
    return this.http.get<any>(url, { params: null });

}



    getMonitoraggio(monitoraggioFilter: ConsoleMonitoraggioFilter):Observable<any> {
      let url = this.configService.getApiURL() + '/restfacade/consoleMonitoriaggioServizi/getEsitoChiamate';
      let params = new HttpParams();
      if(monitoraggioFilter && monitoraggioFilter.dataInserimentoDal && monitoraggioFilter.dataInserimentoAl){
        params = params.set("dataInizio", new DatePipe('it').transform(monitoraggioFilter.dataInserimentoDal, 'dd/MM/yyyy HH:mm:ss'));
        params = params.set("dataFine",new DatePipe('it').transform(monitoraggioFilter.dataInserimentoAl, 'dd/MM/yyyy HH:mm:ss'));
      }
      if(monitoraggioFilter && monitoraggioFilter.codiceErrore){
        params = params.set("codiceErrore",monitoraggioFilter.codiceErrore);
      }
      if(monitoraggioFilter && monitoraggioFilter.idServizio){
        params = params.set("idServizio",monitoraggioFilter.idServizio);
      }


      return this.http.get<any>(url, { params: params });
  }


  getStatusMonitoraggio(monitoraggioFilter: ConsoleMonitoraggioFilter): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/consoleMonitoriaggioServizi/getStatus';
    let params = new HttpParams();
    if(monitoraggioFilter && monitoraggioFilter.dataInserimentoDal && monitoraggioFilter.dataInserimentoAl){
      params = params.set("dataInizio", new DatePipe('it').transform(monitoraggioFilter.dataInserimentoDal, 'dd/MM/yyyy HH:mm:ss'));
      params = params.set("dataFine",new DatePipe('it').transform(monitoraggioFilter.dataInserimentoAl, 'dd/MM/yyyy HH:mm:ss'));
    }
    if(monitoraggioFilter && monitoraggioFilter.codiceErrore){
        params = params.set("codiceErrore",monitoraggioFilter.codiceErrore);
    }

    if(monitoraggioFilter && monitoraggioFilter.idServizio){
      params = params.set("idServizio",monitoraggioFilter.idServizio);
    }
    return this.http.get<any>(url, { params: params });

}
}
