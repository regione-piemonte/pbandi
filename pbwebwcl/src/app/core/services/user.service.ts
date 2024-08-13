/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { InizializzaHomeSec } from './../commons/dto/inizializza-home';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { HandleExceptionService } from 'src/app/core/services/handle-exception.service';

@Injectable()
export class UserService {

  constructor(
    private configService: ConfigService,
    private http: HttpClient,
    private handleExceptionService: HandleExceptionService
  ) { }

  user: UserInfoSec;
  private userInfo = new BehaviorSubject<UserInfoSec>(null);
  userInfo$: Observable<UserInfoSec> = this.userInfo.asObservable();

  home(idPrj: string, idSg: string, idSgBen: string, idU: string, role: string, taskIdentity: string, taskName: string, wkfAction: string) {
    let url = this.configService.getApiURL() + '/restfacade/inizializzazione/inizializzaHome';

    let params = new HttpParams()
      .set("idSg", idSg)
      .set("idSgBen", idSgBen)
      .set("idU", idU)
      .set("role", role)
      .set("taskIdentity", taskIdentity)
      .set("idPrj", idPrj)
      .set("taskName", taskName)
      .set("wkfAction", wkfAction);

    return this.http.get<InizializzaHomeSec>(url, { params: params }).subscribe(data => {
      if (data) {
        this.user = data.userInfoSec;
        this.userInfo.next(data.userInfoSec);
      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  home2(idSg: string, idSgBen: string, idU: string, role: string) {
    let url = this.configService.getApiURL() + '/restfacade/inizializzazione/inizializzaHome2';
    let params = new HttpParams()
      .set("idSg", idSg)
      .set("idSgBen", idSgBen)
      .set("idU", idU)
      .set("role", role)

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

  isRegolaApplicabileForBandoLinea(idBandoLinea: number, codiceRegola: string): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/isRegolaApplicabileForBandoLinea';
    let params = new HttpParams().set("idBandoLinea", idBandoLinea.toString()).set("codiceRegola", codiceRegola);
    return this.http.get<boolean>(url, { params: params });
  }

  isCostanteFinpiemonte(): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/isCostanteFinpiemonte';
    return this.http.get<boolean>(url);
  }

  isBandoSif(progBandoLineaIntervento: number): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/isBandoSif';
    let params = new HttpParams().set("progBandoLineaIntervento", progBandoLineaIntervento.toString());
    return this.http.get<boolean>(url, { params: params })
  }

  isBandoCompetenzaFinpiemonte(progBandoLineaIntervento: number): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/bandoIsEnteCompetenzaFinpiemonte';
    let params = new HttpParams().set("progBandoLineaIntervento", progBandoLineaIntervento.toString());
    return this.http.get<boolean>(url, { params: params });
  }
}
