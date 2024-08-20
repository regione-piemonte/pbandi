/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { LiquidazioneDTO } from '../commons/liquidazione-vo';
import { DettaglioBeneficiarioBilancioDTO } from '../commons/dettaglio-beneficiario-bilancio-vo';
import { DatiIntegrativiDTO } from '../commons/dati-integrativi-vo';
import { Liquidazione } from '../commons/liquidazione';
import { EsitoLeggiRiepilogoFondiDTO } from '../commons/esito-leggi-riepilogo-fondi-vo';
import { Beneficiario } from '../commons/beneficiario';
import { DettaglioBeneficiario } from '../commons/dettaglio-beneficiario-vo';
import { BeneficiarioBilancioDTO } from '../commons/beneficiario-bilancio-vo';
import { DatiIntegrativi } from '../commons/dati-integrativi';
import { EsitoInfoCreaAttoDTO } from '../commons/esito-info-crea-atto-vo';
import { EsitoCreaAttoDTO } from '../commons/esito-crea-atto-vo';
import { AliquotaRienutaDTO } from '../commons/aliquota-ritenuta-vo';
import { SettoreEnteDTO } from '../commons/settore-ente-vo';
import { ImpegniLiquidazioneDTO } from '../commons/impegni-liquidazione-vo';

@Injectable()
export class LiquidazioneService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    caricaDatiGenerali(user: UserInfoSec, idSoggettoBeneficiario: string, idBandoLinea: string, idProgetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/caricadatigenerali';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggettoBeneficiario", idSoggettoBeneficiario)
            .set("idBandoLinea", idBandoLinea)
            .set("idProgetto", idProgetto)

        return this.http.get<Liquidazione>(url, { params: params });
    }

    riepilogoFondi(user: UserInfoSec, liquidazione: LiquidazioneDTO, idSoggetto: string, idSoggettoBeneficiario: string, idBandoLinea: string) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/riepilogofondi';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("idSoggettoBeneficiario", idSoggettoBeneficiario)
            .set("idBandoLinea", idBandoLinea)

        return this.http.post<EsitoLeggiRiepilogoFondiDTO>(url, liquidazione, { params: params });
    }

    beneficiario(user: UserInfoSec, impegniLiquidazioneDTO: ImpegniLiquidazioneDTO, idSoggetto: string, idSoggettoBeneficiario: string, idBandoLinea: string) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/beneficiario';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("idSoggettoBeneficiario", idSoggettoBeneficiario)
            .set("idBandoLinea", idBandoLinea)

        return this.http.post<Beneficiario>(url, impegniLiquidazioneDTO, { params: params });
    }

    apriDettaglioBeneficiario(user: UserInfoSec, idProvincia: string, iban: string) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/apridettagliobeneficiario';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idProvincia", idProvincia)
            .set("iban", iban)

        return this.http.get<DettaglioBeneficiario>(url, { params: params });
    }

    aggiornaBeneficiario(user: UserInfoSec, idProgetto: string, idAttoLiquidazione: string, dettaglioBeneficiarioBilancioDTO: DettaglioBeneficiarioBilancioDTO) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/aggiornabeneficiario';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idProgetto", idProgetto)
            .set("idAttoLiquidazione", idAttoLiquidazione)

        return this.http.post<BeneficiarioBilancioDTO>(url, dettaglioBeneficiarioBilancioDTO, { params: params });
    }

    datiIntegrativi(user: UserInfoSec, idAttoLiquidazione: string, idSoggetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/datiintegrativi';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("idAttoLiquidazione", idAttoLiquidazione)

        return this.http.get<DatiIntegrativi>(url, { params: params });
    }

    ritenute(user: UserInfoSec, idProgetto: string, datiIntegrativiDTO: DatiIntegrativiDTO) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/ritenute';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idProgetto", idProgetto)

        return this.http.post<AliquotaRienutaDTO>(url, datiIntegrativiDTO, { params: params });
    }

    tabCreaAtto(user: UserInfoSec, idAttoLiquidazione: string, imponibile: string, sommeNonImponibili: string, idAliquotaRitenuta: string) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/tabcreaatto';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());
        params = params.append("idAttoLiquidazione", idAttoLiquidazione);

        if (!((imponibile == undefined) || (imponibile == "") || (imponibile == "undefined"))) {
            params = params.append("imponibile", imponibile);
        }

        if (!(sommeNonImponibili == null)) {
            params = params.append("idAliquotaRitenuta", idAliquotaRitenuta);
        }

        if (!((sommeNonImponibili == undefined) || (sommeNonImponibili == "") || (sommeNonImponibili == "undefined"))) {
            params = params.append("sommeNonImponibili", sommeNonImponibili);
        }

        return this.http.get<EsitoInfoCreaAttoDTO>(url, { params: params });
    }

    creaAtto(user: UserInfoSec, idAttoLiquidazione: string, idProgetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/creaatto';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idAttoLiquidazione", idAttoLiquidazione)
            .set("idProgetto", idProgetto)

        return this.http.get<EsitoCreaAttoDTO>(url, { params: params });
    }

    cercaSettoreAppartenenza(user: UserInfoSec, idSoggetto: string, idEnte: string) {
        let url = this.configService.getApiURL() + '/restfacade/liquidazione/cercasettoreappartenenza';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("idEnte", idEnte)

        return this.http.get<Array<SettoreEnteDTO>>(url, { params: params });
    }
}