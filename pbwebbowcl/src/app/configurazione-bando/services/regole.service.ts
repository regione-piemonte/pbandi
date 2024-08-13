/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { IdDescBreveDescEstesaVo } from '../commons/vo/id-desc-breve-desc-estesa-vo';
import { RegolaAssociataDTO } from '../commons/vo/regola-associata-DTO';
import { EsitoAssociazioneDTO } from '../commons/vo/esito-associazione-vo';
import { GestioneBackOfficeEsitoGenerico } from '../commons/vo/gestione-back-office-esito-generico-vo';

@Injectable()
export class RegoleService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }
    
    cercaRegole(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/regole/cercaregole';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    regoleAssociate(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/regole/regoleassociate';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<RegolaAssociataDTO>>(url, { params: params });
    }

    associaRegola(user: UserInfoSec, progrBandoLineaIntervento: string, idRegola: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/regole/associaregola';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idRegola", idRegola)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    eliminaRegola(user: UserInfoSec, progrBandoLineaIntervento: string, idRegola: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/regole/eliminaregola';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idRegola", idRegola)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }
}