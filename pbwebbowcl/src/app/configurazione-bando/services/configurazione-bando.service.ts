/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BandoVo } from '../commons/vo/bando-vo';
import { UserInfoSec } from '../../core/commons/dto/user-info';
import { NormativaVo } from '../commons/vo/normativa-vo';
import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';

@Injectable()
export class ConfigurazioneBandoService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    getNormative(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/findnormative';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<NormativaVo>>(url, { params: params });
    }

    getNormativePost2016(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/findnormativepost2016';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<NormativaVo>>(url, { params: params });
    }

    findBandi(user: UserInfoSec, normativa: string, idLinea: number, nomeBandoLinea: string, titoloBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/ricercabandi';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        // facoltativo
        if (!(normativa == undefined) || (normativa == "")) {
            params = params.set("normativa", normativa)
        }

        // facoltativo
        if (!(idLinea == undefined)) {
            params = params.set("idLineaDiIntervento", idLinea.toString())
        }

        // facoltativo
        if (!(nomeBandoLinea == undefined) || (nomeBandoLinea == "")) {
            params = params.set("nomeBandoLinea", nomeBandoLinea)
        }

        // facoltativo
        if (!(titoloBando == undefined) || (titoloBando == "")) {
            params = params.set("titoloBando", titoloBando)
        }

        return this.http.get<Array<BandoVo>>(url, { params: params });
    }
}