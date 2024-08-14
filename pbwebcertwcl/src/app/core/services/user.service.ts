/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

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

    home(idSg: string, idSgBen: string, idU: string, role: string) {
        let url = this.configService.getApiURL() + '/restfacade/certificazione/home';

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
}