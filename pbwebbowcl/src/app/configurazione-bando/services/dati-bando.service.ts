/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { IdDescBreveDescEstesaVo } from '../commons/vo/id-desc-breve-desc-estesa-vo';
import { UserInfoSec } from '../../core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { DatiBandoVo } from '../commons/vo/dati-bando-vo';
import { PbandiMateriaVo } from '../commons/vo/pbandi-materia-vo';
import { EsitoSalvaDatiBando } from '../commons/vo/esito-salva-dati-dando-vo';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';
import { Observable } from 'rxjs';
import { NaturaCipeVO } from '../commons/vo/natura-cipe-vo';

@Injectable()
export class DatiBandoService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    settoreCipe(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/settorecipe';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    sottoSettoreCipe(user: UserInfoSec, idSettoreCipe: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/sottoSettorecipe';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSettoreCipe", idSettoreCipe)

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    naturaCipe(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/naturacipe';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<NaturaCipeVO>>(url, { params: params });
    }

    tipologiaAttivazione(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/tipologiadiattivazione';

        if (idLineaDiIntervento == null) {
            let params = new HttpParams()
                .set("idU", user.idUtente.toString())

            return this.http.get<Array<Decodifica>>(url, { params: params });
        } else {
            let params = new HttpParams()
                .set("idU", user.idUtente.toString())
                .set("idLineaDiIntervento", idLineaDiIntervento)

            return this.http.get<Array<Decodifica>>(url, { params: params });
        }
    }

    cercaDatiBando(user: UserInfoSec, titoloBando: string, nomeBandoLinea: string, normativa: string, idLineaDiIntervento: string, idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/cercadatibando';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("titoloBando", titoloBando)
            .set("nomeBandoLinea", nomeBandoLinea)
            .set("normativa", normativa)
            //.set("idLineaDiIntervento", idLineaDiIntervento)
            .set("idBando", idBando)

        return this.http.get<DatiBandoVo>(url, { params: params });
    }

    materiaRiferimento(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/materiadiriferimento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<PbandiMateriaVo>>(url, { params: params });
    }

    codiceIntesaIstituzionale(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/codiceintesaistituzionale';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    tipoOperazione(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/tipooperazione';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    settoreCPT(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/settorecpt';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    salvaDatiBando(req: DatiBandoVo) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datibando/salvaDatiBando';
        return this.http.post<EsitoSalvaDatiBando>(url, req);
    }

    getIdProcessoByProgrBandoLineaIntervento(progrLineaLineaIntervento: number): Observable<number> {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/getIdProcessoByProgrBandoLineaIntervento';
        let params = new HttpParams().set("progrLineaLineaIntervento", progrLineaLineaIntervento.toString());
        return this.http.get<number>(url, { params: params });
    }

}