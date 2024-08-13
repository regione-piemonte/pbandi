/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { EsitoAssociazioneDTO } from '../commons/vo/esito-associazione-vo';
import { GestioneBackOfficeEsitoGenerico } from '../commons/vo/gestione-back-office-esito-generico-vo';
import { TipoDiAiutoAssociatoDTO } from '../commons/vo/tipo-di-aiuto-associato-DTO';
import { IdDescBreveDescEstesaVo } from '../commons/vo/id-desc-breve-desc-estesa-vo';
import { AreaScientificaDTO } from '../commons/vo/area-scientifica-DTO';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';

@Injectable()
export class DatiAggiuntiviBandoLineaService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    tipiAiutoDaAssociare(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/tipidiaiutodaassociare';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());

        if (!((idLineaDiIntervento == undefined) || (idLineaDiIntervento == "") || (idLineaDiIntervento == "undefined") || (idLineaDiIntervento == null) || (idLineaDiIntervento == "null"))) {
            params = params.append("idLineaDiIntervento", idLineaDiIntervento);
        }

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    associaTipiAiuto(user: UserInfoSec, progrBandoLineaIntervento: string, idTipiDiAiuto: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/associatipidiaiuto';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idTipiDiAiuto", idTipiDiAiuto)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    eliminaTipiAiuto(user: UserInfoSec, progrBandoLineaIntervento: string, idTipiDiAiuto: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/eliminatipidiaiuto';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idTipiDiAiuto", idTipiDiAiuto)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    findTipiAiutoAssociati(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/findtipidiaiutoassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<TipoDiAiutoAssociatoDTO>>(url, { params: params });
    }

    temaPrioritario(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/temaprioritario';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());

        if (!((idLineaDiIntervento == undefined) || (idLineaDiIntervento == "") || (idLineaDiIntervento == "undefined") || (idLineaDiIntervento == null) || (idLineaDiIntervento == "null"))) {
            params = params.append("idLineaDiIntervento", idLineaDiIntervento);
        }

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    temaPrioritarioAssociato(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/temaprioritarioassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    associaTemaPrioritario(user: UserInfoSec, idTemaPrioritario: string, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/associatemaprioritario';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idTemaPrioritario", idTemaPrioritario)
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    eliminaTemaPrioritarioAssociato(user: UserInfoSec, idTemaPrioritario: string, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/eliminatemaprioritarioassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idTemaPrioritario", idTemaPrioritario)
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    indicatoriQSN(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/indicatoriqsn';


        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());

        if (!((idLineaDiIntervento == undefined) || (idLineaDiIntervento == "") || (idLineaDiIntervento == "undefined") || (idLineaDiIntervento == null) || (idLineaDiIntervento == "null"))) {
            params = params.append("idLineaDiIntervento", idLineaDiIntervento);
        }

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    indicatoriQSNAssociati(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/indicatoriqsnassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    associaIndicatoreQSN(user: UserInfoSec, progrBandoLineaIntervento: string, idIndicatoreQSN: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/associaindicatoreqsn';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idIndicatoreQSN", idIndicatoreQSN)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    eliminaIndicatoreQSNAssociato(user: UserInfoSec, progrBandoLineaIntervento: string, idIndicatoreQSN: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/eliminaindicatoreqsnassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idIndicatoreQSN", idIndicatoreQSN)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    indicatoriRisultatoProgramma(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/indicatoririsultatoprogramma';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    associaIndicatoreRisultatoProgramma(user: UserInfoSec, progrBandoLineaIntervento: string, idIndicatoreRisultatoProgramma: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/associaindicatorerisultatoprogramma';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idIndicatoreRisultatoProgramma", idIndicatoreRisultatoProgramma)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    indicatoriRisultatoProgrammaAssociati(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/indicatoririsultatoprogrammaassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    eliminaIndicatoreRisultatoProgramma(user: UserInfoSec, progrBandoLineaIntervento: string, idIndicatoreRisultatoProgramma: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/eliminaindicatorerisultatoprogramma';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idIndicatoreRisultatoProgramma", idIndicatoreRisultatoProgramma)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    tipoPeriodo(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/tipoperiodo';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    tipoPeriodoAssociato(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/tipoperiodoassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    associaTipoPeriodo(user: UserInfoSec, progrBandoLineaIntervento: string, idTipoPeriodo: string, periodoProgrammazione: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/associatipoperiodo';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idTipoPeriodo", idTipoPeriodo)
            .set("periodoProgrammazione", periodoProgrammazione)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    eliminaTipoPeriodo(user: UserInfoSec, progrBandoLineaIntervento: string, idTipoPeriodo: string, periodoProgrammazione: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/datiaggiuntivi/eliminatipoperiodo';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idTipoPeriodo", idTipoPeriodo)
            .set("periodoProgrammazione", periodoProgrammazione)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    areaScientifica(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/areascientifica/areascientifica';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    areeScientificheAssociate(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/areascientifica/areescientificheassociate';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<AreaScientificaDTO>>(url, { params: params });
    }

    associaAreaScientifica(user: UserInfoSec, progrBandoLineaIntervento: string, idAreaScientifica: string, descrizione: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/areascientifica/associaareascientifica';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idAreaScientifica", idAreaScientifica)
            .set("descrizione", descrizione)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    eliminaAreaScientificaAssociata(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/areascientifica/eliminaareascientificaassociata';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    verificaParolaChiaveActa(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/entedicompetenza/verificaparolachiaveacta';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaEnteCompetenza", progrBandoLineaIntervento)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

}