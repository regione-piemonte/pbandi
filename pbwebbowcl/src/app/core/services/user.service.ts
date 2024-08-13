/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { UserInfoSec } from './../commons/dto/user-info';
import { DOCUMENT } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ConfigService } from './config.service';
import { HandleExceptionService } from './handle-exception.service';

@Injectable()
export class UserService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService,
    @Inject(DOCUMENT) private document: any
  ) { }

  user: UserInfoSec;
  private userInfo = new BehaviorSubject<UserInfoSec>(null);
  userInfo$: Observable<UserInfoSec> = this.userInfo.asObservable();

  inizializzaAmministrazione(idSg: string, idSgBen: string, idU: string, role: string) {

    let url = this.configService.getApiURL() + '/restfacade/inizializzazione/home';

    let params = new HttpParams()
      .set("idSg", idSg)
      .set("idSgBen", idSgBen)
      .set("idU", idU)
      .set("role", role);
    return this.http.get<UserInfoSec>(url, { params: params }).subscribe(data => {
      if (data) {
        this.user = data;
        this.userInfo.next(data);
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  getInfoUtente() {
    let url = this.configService.getApiURL() + '/restfacade/utente/infoUtente';
    return this.http.get<UserInfoSec>(url).subscribe(data => {
      if (data) {
        this.user = data;
        this.userInfo.next(data);
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  logOut(disableRedirect = false) {
    let url = this.configService.getApiURL() + '/restfacade/utente/SSOLogout';
    this.http.get(url, { responseType: 'text' }).subscribe(result => {
      if (!disableRedirect) {
        window.location.href = this.configService.getSSOLogoutURL();
      }
    }, err => this.handleExceptionService.handleBlockingError(err));
  }

  isCostanteFinpiemonte(): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/isCostanteFinpiemonte';
    return this.http.get<boolean>(url);
  }

  isBandoSiffino(progrBandoLineaIntervento: number, idBando: number): Observable<boolean> {
    //valorizzare o progrBandoLineaIntervento o idBando
    let url = this.configService.getApiURL() + '/restfacade/utente/isBandoSiffino';
    let params = new HttpParams();
    if (progrBandoLineaIntervento) {
      params = params.set("progrBandoLineaIntervento", progrBandoLineaIntervento.toString());
    }
    if (idBando) {
      params = params.set("idBando", idBando.toString());
    }
    return this.http.get<boolean>(url, { params: params });
  }

  isBandoCompetenzaFinpiemonte(progBandoLineaIntervento: number): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/bandoIsEnteCompetenzaFinpiemonte';
    let params = new HttpParams().set("progBandoLineaIntervento", progBandoLineaIntervento.toString());
    return this.http.get<boolean>(url, { params: params });
  }

}
