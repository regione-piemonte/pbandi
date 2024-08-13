/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { UserInfoSec } from '../../core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { VoceDiSpesaAssociataDTO } from '../commons/vo/voce-di-spesa-associata-vo';
import { IdDescBreveDescEstesaVo } from '../commons/vo/id-desc-breve-desc-estesa-vo';
import { IdDescBreveDescEstesa2DTO } from '../commons/vo/id-desc-breve-desc-estesa2-vo';
import { CommonResponseVo } from '../commons/vo/common-response-vo';

@Injectable()
export class VociSpesaService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    macroVoceSpesa(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/vocidispesa/vocedispesa';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<VoceDiSpesaAssociataDTO>>(url, { params: params });
    }

    voceSpesaMonitoraggio(user: UserInfoSec, idTipoOperazione: string, idNaturaCipe: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/vocidispesa/vocedispesaMonitoraggio';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            //.set("idTipoOperazione", idTipoOperazione)
            .set("idNaturaCipe", idNaturaCipe)

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    microVoceSpesa(user: UserInfoSec, idVoceDiSpesaPadre: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/vocidispesa/sottovoce';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idVoceDiSpesaPadre", idVoceDiSpesaPadre)

        return this.http.get<Array<VoceDiSpesaAssociataDTO>>(url, { params: params });
    }

    vociAssociate(user: UserInfoSec, idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/vocidispesa/vociassociate';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)

        return this.http.get<Array<IdDescBreveDescEstesa2DTO>>(url, { params: params });
    }

    eliminaVoceAssociata(user: UserInfoSec, idVoceDiSpesa: string, idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/vocidispesa/eliminavoceassociata';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idVoceDiSpesa", idVoceDiSpesa)
            .set("idBando", idBando)

        return this.http.get<CommonResponseVo>(url, { params: params });
    }

    associaVoceAssociata(user: UserInfoSec, idBando: string, idVoceDiSpesa: string, descVoceDiSpesa: string, idSottovoce: string,
        descSottovoce: string, idVoceDiSpesaMonit: string, codTipoVoceDiSpesaMacro: string, codTipoVoceDiSpesaMicro: string, flagSpeseGestioneMacro: string, flagSpeseGestioneMicro: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/vocidispesa/associavocedispesa';
        let params = new HttpParams().set("idU", user.idUtente.toString()).set("idBando", idBando);
        if (idVoceDiSpesa) {
            params = params.set("idVoceDiSpesa", idVoceDiSpesa);
        }
        if (descVoceDiSpesa) {
            params = params.set("descVoceDiSpesa", descVoceDiSpesa);
        }
        if (idSottovoce) {
            params = params.set("idSottovoce", idSottovoce);
        }
        if (descSottovoce) {
            params = params.set("descSottovoce", descSottovoce);
        }
        if (idVoceDiSpesaMonit) {
            params = params.set("idVoceDiSpesaMonit", idVoceDiSpesaMonit);
        }
        if (codTipoVoceDiSpesaMacro) {
            params = params.set("codTipoVoceDiSpesaMacro", codTipoVoceDiSpesaMacro);
        }
        if (codTipoVoceDiSpesaMicro) {
            params = params.set("codTipoVoceDiSpesaMicro", codTipoVoceDiSpesaMicro);
        }
        if (flagSpeseGestioneMacro) {
            params = params.set("flagSpeseGestioneMacro", flagSpeseGestioneMacro);
        }
        if (flagSpeseGestioneMicro) {
            params = params.set("flagSpeseGestioneMicro", flagSpeseGestioneMicro);
        }
        return this.http.get<CommonResponseVo>(url, { params: params });

    }
}