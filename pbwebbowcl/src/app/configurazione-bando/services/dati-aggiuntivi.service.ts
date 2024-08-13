/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { UserInfoSec } from '../../core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { IdDescBreveDescEstesaVo } from '../commons/vo/id-desc-breve-desc-estesa-vo';
import { EsitoAssociazioneDTO } from '../commons/vo/esito-associazione-vo';
import { GestioneBackOfficeEsitoGenerico } from '../commons/vo/gestione-back-office-esito-generico-vo';
import { TipoDiTrattamentoAssociatoDTO } from '../commons/vo/tipo-trattamento-associato-vo';
import { SoggettoFinanziatoreAssociatoDTO } from '../commons/vo/soggetto-finanziatore-associato-vo';
import { CausaleDiErogazioneAssociataDTO } from '../commons/vo/causale-erogazione-associata-vo';
import { IdDescBreveDescEstesa2DTO } from '../commons/vo/id-desc-breve-desc-estesa2-vo';
import { CommonResponseVo } from '../commons/vo/common-response-vo';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';
import { DataModalitaAgevolazioneDTO } from '../commons/vo/data-modalita-agevolazione-DTO';
import { Observable } from 'rxjs';

@Injectable()
export class DatiAggiuntiviService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    modalitaAgevolazione(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/modalitadiagevolazione';

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
    modalitaAgevolErogazione(): Observable<Array<Decodifica>> {

        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/modalitaAgevErogazione';
        let params = new HttpParams();
        return this.http.get<Array<Decodifica>>(url, { params: params });
   
    }

    modalitaAssociata(idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/modalitaAssociata';

        let params = new HttpParams()
            .set("idBando", idBando)

        return this.http.get<Array<DataModalitaAgevolazioneDTO>>(url, { params: params });
    }

    associaModalitaAgevolazione(user: UserInfoSec, idBando: string, idModalitaDiAgevolazione: string, importoAgevolato: string, massimoImportoAgevolato: string,
        periodoStabilita: string, importoDaErogare: string, flagLiquidazione: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/associamodalitadiagevolazione';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)
            .set("idModalitaDiAgevolazione", idModalitaDiAgevolazione)

        if (importoAgevolato != undefined)
            params = params.set("importoAgevolato", importoAgevolato)

        if (periodoStabilita != undefined)
            params = params.set("periodoStabilita", periodoStabilita)

        if (importoDaErogare != undefined)
            params = params.set("importoDaErogare", importoDaErogare)

        if (flagLiquidazione != undefined)
            params = params.set("flagLiquidazione", flagLiquidazione)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });

    }

    eliminaModalitaAgevolazioneAssociata(user: UserInfoSec, idBando: string, idModalitaDiAgevolazione: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/eliminaModalitadiagevolazioneAssociata';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)
            .set("idModalitaDiAgevolazione", idModalitaDiAgevolazione)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    tipoTrattamentoAssociato(user: UserInfoSec, idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/tipotrattamentoassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)

        return this.http.get<Array<TipoDiTrattamentoAssociatoDTO>>(url, { params: params });
    }

    associaTipoTrattamento(user: UserInfoSec, idBando: string, idTipoDiTrattamento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/associatipotrattamento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)
            .set("idTipoDiTrattamento", idTipoDiTrattamento)

        return this.http.get<CommonResponseVo>(url, { params: params });
    }

    eliminaTipoTrattamentoAssociato(user: UserInfoSec, idBando: string, idTipoTrattamento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/eliminatipotrattamentoassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)
            .set("idTipoTrattamento", idTipoTrattamento)

        return this.http.get<CommonResponseVo>(url, { params: params });
    }

    tipoTrattamentoAssociatoPredefinito(user: UserInfoSec, idBando: string, idTipoTrattamento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/tipotrattamentoassociatopredefinito';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)
            .set("idTipoTrattamento", idTipoTrattamento)

        return this.http.get<CommonResponseVo>(url, { params: params });
    }

    tipoTrattamento(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/tipotrattamento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    soggettiFinanziatori(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/soggettifinanziatori';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<IdDescBreveDescEstesa2DTO>>(url, { params: params });
    }

    associaSoggettoFinanziatore(user: UserInfoSec, idBando: string, soggettoFinanziatore: string, idSoggettoFinanziatore: string, percentuale: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/associasoggettofinanziatore';

        if (percentuale == undefined) {
            let params = new HttpParams()
                .set("idU", user.idUtente.toString())
                .set("idBando", idBando)
                .set("soggettoFinanziatore", soggettoFinanziatore)
                .set("idSoggettoFinanziatore", idSoggettoFinanziatore)

            return this.http.get<CommonResponseVo>(url, { params: params });
        } else {
            let params = new HttpParams()
                .set("idU", user.idUtente.toString())
                .set("idBando", idBando)
                .set("soggettoFinanziatore", soggettoFinanziatore)
                .set("idSoggettoFinanziatore", idSoggettoFinanziatore)
                .set("percentuale", percentuale)

            return this.http.get<CommonResponseVo>(url, { params: params });
        }
    }

    eliminaSoggettoFinanziatore(user: UserInfoSec, idBando: string, idSoggettoFinanziatore: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/eliminasoggettofinanziatore';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)
            .set("idSoggettoFinanziatore", idSoggettoFinanziatore)

        return this.http.get<CommonResponseVo>(url, { params: params });
    }

    soggettiFinanziatoreAssociato(user: UserInfoSec, idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/soggettifinanziatoreAssociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)

        return this.http.get<Array<SoggettoFinanziatoreAssociatoDTO>>(url, { params: params });
    }

    causaleErogazione(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/causaleerogazione';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    causaleErogazioneAssociata(user: UserInfoSec, idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/causaleerogazioneassociata';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)

        return this.http.get<Array<CausaleDiErogazioneAssociataDTO>>(url, { params: params });
    }

    associaCausale(user: UserInfoSec, req: CausaleDiErogazioneAssociataDTO) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/associacasuale';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.post<CommonResponseVo>(url, req, { params: params });
    }

    eliminaCausaleErogazione(user: UserInfoSec, progrBandoCausaleErogaz: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/eliminacausaleerogazione';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoCausaleErogaz", progrBandoCausaleErogaz)

        return this.http.get<CommonResponseVo>(url, { params: params });
    }

    tipoIndicatore(user: UserInfoSec, idLineaDiIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/tipoindicatore';

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

    indicatore(user: UserInfoSec, idTipoIndicatore: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/indicatore';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idTipoIndicatore", idTipoIndicatore)

        return this.http.get<Array<IdDescBreveDescEstesaVo>>(url, { params: params });
    }

    indicatoreAssociato(user: UserInfoSec, idBando: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/indicatoreassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)

        return this.http.get<Array<IdDescBreveDescEstesa2DTO>>(url, { params: params });
    }

    associaIndicatore(user: UserInfoSec, idBando: string, idIndicatore: string, infoIniziale: string, infoFinale: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/associaindicatore';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)
            .set("idIndicatore", idIndicatore);
        if (infoIniziale?.length > 0) {
            params = params.set("infoIniziale", infoIniziale);
        }
        if (infoFinale?.length > 0) {
            params = params.set("infoFinale", infoFinale);
        }

        return this.http.get<CommonResponseVo>(url, { params: params });
    }

    eliminaIndicatore(user: UserInfoSec, idBando: string, idIndicatore: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurabando/datiaggiuntivi/eliminaindicatore';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBando", idBando)
            .set("idIndicatore", idIndicatore)

        return this.http.get<CommonResponseVo>(url, { params: params });
    }
}