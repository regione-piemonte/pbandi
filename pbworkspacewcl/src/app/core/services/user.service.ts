/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { BeneficiarioSec } from 'src/app/core/commons/vo/beneficiario-vo';
import { AvvisoUtenteDTO } from '../commons/vo/avviso-utente-dto';
import { UserInfoSec } from '../commons/vo/user-info';
import { ConfigService } from './config.service';
import { HandleExceptionService } from './handle-exception.service';

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

  accediSubject = new Subject<UserInfoSec>();
  accedi() {
    let url = this.configService.getApiURL() + '/restfacade/utente/accedi';
    this.http.post<UserInfoSec>(url, this.user).subscribe(data => {
      if (data) {
        this.getInfoUtente();
        this.accediSubject.next(data);

      }
    }, err => {
      this.handleExceptionService.handleBlockingError(err);
    });
  }

  getBeneficiariByDenom(denominazione: string): Observable<Array<BeneficiarioSec>> {
    let url = this.configService.getApiURL() + '/restfacade/utente/beneficiari';
    let params = new HttpParams().set("denominazione", denominazione);
    return this.http.get<Array<BeneficiarioSec>>(url, { params: params });
  }

  salvaBeneficiarioSelezionatoSubject = new Subject<void>();
  salvaBeneficiarioSelezionato(beneficiario: BeneficiarioSec) {
    let url = this.configService.getApiURL() + '/restfacade/utente/salvaBeneficiarioSelezionato';
    this.http.post<void>(url, beneficiario).subscribe(data => {
      this.getInfoUtente();
      this.salvaBeneficiarioSelezionatoSubject.next(data);
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

  avvisi(): Observable<Array<AvvisoUtenteDTO>> {
    let url = this.configService.getApiURL() + '/restfacade/utente/avvisi';
    return this.http.get<Array<AvvisoUtenteDTO>>(url);
  }

  isRegolaApplicabileForBandoLinea(idBandoLinea: number, codiceRegola: string): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/isRegolaApplicabileForBandoLinea';
    let params = new HttpParams().set("idBandoLinea", idBandoLinea.toString()).set("codiceRegola", codiceRegola);
    return this.http.get<boolean>(url, { params: params });
  }

  isBandoSif(progBandoLineaIntervento: number): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/isBandoSif';
    let params = new HttpParams().set("progBandoLineaIntervento", progBandoLineaIntervento.toString());
    return this.http.get<boolean>(url, { params: params })
  }

  isCostanteFinpiemonte(): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/isCostanteFinpiemonte';
    return this.http.get<boolean>(url);
  }

  isBandoCompetenzaFinpiemonte(progBandoLineaIntervento: number): Observable<boolean> {
    let url = this.configService.getApiURL() + '/restfacade/utente/bandoIsEnteCompetenzaFinpiemonte';
    let params = new HttpParams().set("progBandoLineaIntervento", progBandoLineaIntervento.toString());
    return this.http.get<boolean>(url, { params: params });
  }

}
