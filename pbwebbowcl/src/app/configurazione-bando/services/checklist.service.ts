/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { LineaInterventoVo } from '../commons/vo/linea-intevento-vo';
import { CheckListBandoLineaDTO } from '../commons/vo/check-list-bando-linea-DTO';
import { EsitoAssociazioneDTO } from '../commons/vo/esito-associazione-vo';
import { GestioneBackOfficeEsitoGenerico } from '../commons/vo/gestione-back-office-esito-generico-vo';
import { CheclistDTO } from '../commons/vo/checlist-DTO';

@Injectable()
export class CheckListService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    checklistAssociate(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/checklist/checklistassociate';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<CheckListBandoLineaDTO>>(url, { params: params });
    }

    checklistAssociabili(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/checklist/checklistassociabili';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<CheckListBandoLineaDTO>>(url, { params: params });
    }

    associaChecklist(req: CheclistDTO) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/checklist/associachecklist';

        return this.http.post<EsitoAssociazioneDTO>(url, req);
    }

    eliminaChecklist(user: UserInfoSec, progrBandoLineaIntervento: string, idTipoDocumentoIndex: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/checklist/eliminachecklist';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idTipoDocumentoIndex", idTipoDocumentoIndex)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }
}