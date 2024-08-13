/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { EsitoAssociazioneDTO } from '../commons/vo/esito-associazione-vo';
import { CostantiBandoLineaDTO } from '../commons/vo/costanti-bando-linea-DTO';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';

@Injectable()
export class CostantiService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    salvaCostanti(user: UserInfoSec, req: CostantiBandoLineaDTO) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/costanti/salvacostanti';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.post<EsitoAssociazioneDTO>(url, req, { params: params });
    }

    tipoOperazione(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/costanti/tipooperazione';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    statoDomanda(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/costanti/statodomanda';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    costantiBandoLinea(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/costanti/costantibandolinea';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<CostantiBandoLineaDTO>(url, { params: params });
    }
}