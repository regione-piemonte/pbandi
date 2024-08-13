/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BandoLineaAssociatoVo } from '../commons/vo/bando-linea-associato-vo';
import { LineaInterventoVo } from '../commons/vo/linea-intevento-vo';
import { UserInfoSec } from '../../core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { LineaInterventoDaModificareVo } from '../commons/vo/linea-intervento-da-modificare-vo';
import { LineaInterventoDaAssociareVo } from '../commons/vo/linea-intervento-da-associare-vo';
import { CommonResponseVo } from '../commons/vo/common-response-vo';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';

@Injectable()
export class LineaInterventoService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    asse(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/asse';

        if (idLineaDiIntervento == null) {
            let params = new HttpParams()
                .set("idU", user.idUtente.toString())

            return this.http.get<Array<LineaInterventoVo>>(url, { params: params });
        } else {
            let params = new HttpParams()
                .set("idU", user.idUtente.toString())
                .set("idLineaDiIntervento", idLineaDiIntervento)

            return this.http.get<Array<LineaInterventoVo>>(url, { params: params });
        }
    }

    misura(user: UserInfoSec, idLineaPadre: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/misura';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idLineaPadre", idLineaPadre)

        return this.http.get<Array<LineaInterventoVo>>(url, { params: params });
    }

    linea(user: UserInfoSec, idLineaPadreMisura: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/linea';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idLineaPadreMisura", idLineaPadreMisura)

        return this.http.get<Array<LineaInterventoVo>>(url, { params: params });
    }

    categoriaCipe(user: UserInfoSec, idSottoSettoreCipe: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/categoriacipe';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSottoSettoreCipe", idSottoSettoreCipe)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    tipologiaCipe(user: UserInfoSec, idNaturaCipe: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/tipologiacipe';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idNaturaCipe", idNaturaCipe)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    obiettivoTematico(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/obiettivotematico';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    classificazioneRA(user: UserInfoSec, idObiettivoTematico: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/classificazionera';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idObiettivoTematico", idObiettivoTematico)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    elencoBandiSif(user: UserInfoSec, progrBandoLinea: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/elencobandisif';

        if (progrBandoLinea == null) {
            let params = new HttpParams()
                .set("idU", user.idUtente.toString())

            return this.http.get<Array<BandoLineaAssociatoVo>>(url, { params: params });
        } else {
            let params = new HttpParams()
                .set("idU", user.idUtente.toString())
                .set("progrBandoLinea", progrBandoLinea)

            return this.http.get<Array<BandoLineaAssociatoVo>>(url, { params: params });
        }
    }

    livelloIstruzione(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/livelloIstruzione';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idLineaDiIntervento", idLineaDiIntervento)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    tipoLocalizzazione(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/tipolocalizzazione';

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

    progettoComplesso(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/progettocomplesso';

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

    met(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/met';

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

    getLineeAssociate(user: UserInfoSec, idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/lineeassociate';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)

        return this.http.get<Array<BandoLineaAssociatoVo>>(url, { params: params });
    }

    modificaLineaIntervento(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/modificalineadiintervento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<LineaInterventoDaModificareVo>(url, { params: params });
    }

    eliminaLineaIntervento(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/eliminalineadiintervento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<CommonResponseVo>(url, { params: params });
    }

    associaLineaIntervento(req: LineaInterventoDaAssociareVo) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/associalineaintervento';
        return this.http.post<CommonResponseVo>(url, req);
    }

    processoInterno(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/processointerno';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    prioritaQSN(user: UserInfoSec, idAsse: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/prioritaqsn';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idAsse", idAsse)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    obiettivoGeneraleQSN(user: UserInfoSec, idPrioritaQsn: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/obiettivogeneraleqsn';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idPrioritaQsn", idPrioritaQsn)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    obiettivoSpecificoQSN(user: UserInfoSec, idObiettivoGenQsn: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/obiettivospecificoqsn';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idObiettivoGenQsn", idObiettivoGenQsn)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    salvaModifiche(req: LineaInterventoDaModificareVo) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/lineadiintervento/salvamodifiche';
        return this.http.post<CommonResponseVo>(url, req);
    }
}