/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { CodiceDescrizioneDTO } from 'src/app/gestione-disimpegni/commons/codice-descrizione-vo';
import { AttoDiLiquidazioneVo } from 'src/app/gestione-disimpegni/commons/atto-di-liquidazione-vo';
import { DettaglioLiquidazioneVo } from 'src/app/gestione-disimpegni/commons/dettaglio-liquidazione-vo';
import { RiepilogoAttoDiLiquidazionePerProgetto } from 'src/app/gestione-disimpegni/commons/riepilogo-atto-di-liquidazione-per-progetto-vo';
import { DettaglioAttoDiLiquidazioneVO } from 'src/app/gestione-disimpegni/commons/dettaglio-atto-di-liquidazione-vo';

@Injectable()
export class RicercaAttiService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    caricaBeneficiariByDenomOrIdBen(denominazione: string, idBeneficiario: number) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/attidiliquidazione/caricaBeneficiariByDenomOrIdBen';
        let params = new HttpParams().set("denominazione", denominazione);
        if (idBeneficiario) {
            params = params.set("idBeneficiario", idBeneficiario.toString());
        }

        return this.http.get<Array<CodiceDescrizioneDTO>>(url, { params: params });
    }

    caricaProgettiByIdBen(idBeneficiario: number) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/attidiliquidazione/caricaProgettiByIdBen';

        let params = new HttpParams()
            .set("idBeneficiario", idBeneficiario.toString())

        return this.http.get<Array<CodiceDescrizioneDTO>>(url, { params: params });
    }

    cercaDatiAtti(user: UserInfoSec, idBeneficiario: string, idProgetto: string, annoEsercizio: string, annoImpegno: string, numeroImpegno: string, annoAtto: string, numeroAtto: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/attidiliquidazione/cercadatiatti';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());

        if (!((idBeneficiario == undefined) || (idBeneficiario == "") || (idBeneficiario == "undefined"))) {
            params = params.append("idBeneficiario", idBeneficiario);
        }

        if (!((idProgetto == undefined) || (idProgetto == "") || (idProgetto == "undefined"))) {
            params = params.append("idProgetto", idProgetto);
        }

        if (!((annoEsercizio == undefined) || (annoEsercizio == "") || (annoEsercizio == "undefined"))) {
            params = params.append("annoEsercizio", annoEsercizio);
        }

        if (!((annoImpegno == undefined) || (annoImpegno == "") || (annoImpegno == "undefined"))) {
            params = params.append("annoImpegno", annoImpegno);
        }

        if (!((numeroImpegno == undefined) || (numeroImpegno == "") || (numeroImpegno == "undefined"))) {
            params = params.append("numeroImpegno", numeroImpegno);
        }

        if (!((annoAtto == undefined) || (annoAtto == "") || (annoAtto == "undefined"))) {
            params = params.append("annoAtto", annoAtto);
        }

        if (!((numeroAtto == undefined) || (numeroAtto == "") || (numeroAtto == "undefined"))) {
            params = params.append("numeroAtto", numeroAtto);
        }

        return this.http.get<Array<AttoDiLiquidazioneVo>>(url, { params: params });
    }

    cercaDettaglioAtto(user: UserInfoSec, idAttoDiLiquidazione: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/attidiliquidazione/cercadettaglioatto';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idAttoDiLiquidazione", idAttoDiLiquidazione)

        return this.http.get<DettaglioAttoDiLiquidazioneVO>(url, { params: params });
    }

    cercaRiepilogoAtti(user: UserInfoSec, idProgetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/attidiliquidazione/cercariepilogoatti';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idProgetto", idProgetto)

        return this.http.get<Array<RiepilogoAttoDiLiquidazionePerProgetto>>(url, { params: params });
    }
}