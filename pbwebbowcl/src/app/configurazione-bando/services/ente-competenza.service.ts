/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { EnteDiCompetenzaDTO } from '../commons/vo/ente-di-competenza-DTO';
import { SettoreEnteDTO } from '../commons/vo/settore-ente-DTO';
import { EnteDiCompetenzaRuoloAssociatoDTO } from '../commons/vo/ente-di-competenza-ruolo-associato-DTO';
import { GestioneBackOfficeEsitoGenerico } from '../commons/vo/gestione-back-office-esito-generico-vo';
import { EsitoAssociazioneDTO } from '../commons/vo/esito-associazione-vo';
import { ResponseCodeMessage } from '../../shared/commons/dto/response-code-message-dto';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';

@Injectable()
export class EnteCompetenzaService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    ente(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/entedicompetenza/ente';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<EnteDiCompetenzaDTO>>(url, { params: params });
    }

    settore(user: UserInfoSec, idEnte: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/entedicompetenza/settore';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idEnte", idEnte)

        return this.http.get<Array<SettoreEnteDTO>>(url, { params: params });
    }

    ruoli(user: UserInfoSec, idLineaDiIntervento: string, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/entedicompetenza/ruoli';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());
        params = params.append("progrBandoLineaIntervento", progrBandoLineaIntervento);

        if (!((idLineaDiIntervento == undefined) || (idLineaDiIntervento == "") || (idLineaDiIntervento == "undefined") || (idLineaDiIntervento == null) || (idLineaDiIntervento == "null"))) {
            params = params.append("idLineaDiIntervento", idLineaDiIntervento);
        }

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    entiCompetenzaAssociati(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/entedicompetenza/entidicompetenzaassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<EnteDiCompetenzaRuoloAssociatoDTO>>(url, { params: params });
    }

    eliminaEnteCompetenzaAssociato(user: UserInfoSec, progrBandoLineaEnteComp: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/entedicompetenza/eliminaentedicompetenzaassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaEnteComp", progrBandoLineaEnteComp)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    associaEnteCompetenza(user: UserInfoSec, progrBandoLineaIntervento: string, idEnte: string, idRuoloEnte: string, idSettoreEnte: string, pec: string, 
        mail:string, tipoProgrammazione: string, periodoConservazioneCorrente: number, periodoConservazioneGenerale: number, flagMonitoraggioTempi:number) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/entedicompetenza/associaentedicompetenza';
        let params = new HttpParams();

        if (periodoConservazioneCorrente == null) {
            if (idSettoreEnte == null) {
                params = params
                    .set("idU", user.idUtente.toString())
                    .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
                    .set("idEnte", idEnte)
                    .set("idRuoloEnte", idRuoloEnte)
                    .set("tipoProgrammazione", tipoProgrammazione)

               
            } else {
                params = params
                    .set("idU", user.idUtente.toString())
                    .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
                    .set("idEnte", idEnte)
                    .set("idRuoloEnte", idRuoloEnte)
                    .set("idSettoreEnte", idSettoreEnte)
                    .set("tipoProgrammazione", tipoProgrammazione)

                
            }
        } else {
            if (idSettoreEnte == null) {
                params = params
                    .set("idU", user.idUtente.toString())
                    .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
                    .set("idEnte", idEnte)
                    .set("idRuoloEnte", idRuoloEnte)
                    .set("tipoProgrammazione", tipoProgrammazione)
                    .set("conservazioneCorrente", periodoConservazioneCorrente.toString())
                    .set("conservazioneGenerale", periodoConservazioneGenerale.toString())

                
            } else {
                params = params
                    .set("idU", user.idUtente.toString())
                    .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
                    .set("idEnte", idEnte)
                    .set("idRuoloEnte", idRuoloEnte)
                    .set("idSettoreEnte", idSettoreEnte)
                    .set("tipoProgrammazione", tipoProgrammazione)
                    .set("conservazioneCorrente", periodoConservazioneCorrente.toString())
                    .set("conservazioneGenerale", periodoConservazioneGenerale.toString())

                
            }
        }

        if(mail!=undefined || mail!=null ){
            params = params.set("mail", mail)
        }

        if(pec!=undefined || pec!=null ){
            params = params.set("pec", pec)
        }

        if(flagMonitoraggioTempi!=null){
            switch (flagMonitoraggioTempi) {
                case 1:
                    params =  params.set("flagMonitoraggioTempi", "S");
                    break;
                case 2:
                    params =  params.set("flagMonitoraggioTempi", "N");
                    break;
            
                default:
                    break;
            }
        }

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });

    }

    isruoliobbligatoriassociati(idBandoLinea: string, ruoliAssociati: Array<EnteDiCompetenzaRuoloAssociatoDTO>) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/entedicompetenza/isruoliobbligatoriassociati';

        let params = new HttpParams()
            .set("idBandoLinea", idBandoLinea)

        return this.http.post<ResponseCodeMessage>(url, ruoliAssociati, { params: params });
    }
}