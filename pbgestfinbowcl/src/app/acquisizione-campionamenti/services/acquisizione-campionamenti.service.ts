/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AnyARecord } from 'dns';
import { Observable } from 'rxjs';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';
import { SearchAcqProgVO } from '../commons/searchAcqProgVO';

@Injectable()
export class AcquisizioneCampionamentiService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService
  ) { }


  getNormative(): Observable<Array<any>> {
    let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/getNormative';
    let params = new HttpParams().set("normativa", "");
    return this.http.get<Array<any>>(url, { params: params });
  }
  acquisisciProgetti(filtro: SearchAcqProgVO): Observable<any> {
    let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/acquisisciProgetti';
    let params = new HttpParams().set("normativa", "");
    return this.http.post<any>(url, filtro, {});
  }
  confermaAcquisizione(elencoProgetti: any, filtro: SearchAcqProgVO, idUtente: number): Observable<any> {
    filtro.progettiConfermati= elencoProgetti;
    let url = this.configService.getApiURL() + '/restfacade/nuovoCampionamento/confermaProgettiAcquisiti';
    let params = new HttpParams().set("idUtente",idUtente.toString() )
    return this.http.post<any>(url, filtro, {params:params});
  }
}
