/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { EnteCompetenzaDTO } from '../commons/ente-competenza-vo';
import { ImpegnoDTO } from '../commons/impegno-vo';
import { BandolineaImpegnoDTO } from '../commons/bando-linea-impegno-vo';
import { ProgettoImpegnoDTO } from '../commons/progetto-impegno-vo';
import { EsitoOperazioneAssociaImpegnoDTO } from '../commons/esito-operazione-associa-impegno-vo';
import { EsitoOperazioneDisassociaDTO } from '../commons/esito-operazione-disassocia-vo';
import { EsitoOperazioneAggiornaImpegnoDTO } from '../commons/esito-operazione-aggiorna-impegno-vo';
import { GestioneImpegnoDTO } from '../commons/gestione-impegni-vo';
import { BeneficiarioVo } from '../commons/beneficiario-vo';
import { DettaglioAttoDTO } from '../commons/dettaglio-atto-vo';

@Injectable()
export class GestioneImpegniService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    cercaDirezione(user: UserInfoSec, idSoggetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercadirezione';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)

        return this.http.get<Array<EnteCompetenzaDTO>>(url, { params: params });
    }

    cercaAnniEsercizioValidi(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercaannieserciziovalidi';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<string>>(url, { params: params });
    }

    cercaImpegni(user: UserInfoSec, annoEsercizio: string, annoImpegno: string, annoProvvedimento: string, numeroProvvedimento: string, direzioneProvvedimento: string, idDirezioneProvvedimento: string, numeroCapitolo: string, ragsoc: string, numeroImpegno: string, idSoggetto: string, isImpegniReimputati: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercaimpegni';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());

        if (!((annoEsercizio == undefined) || (annoEsercizio == "") || (annoEsercizio == "undefined"))) {
            params = params.append("annoEsercizio", annoEsercizio);
        }

        if (!((annoImpegno == undefined) || (annoImpegno == "") || (annoImpegno == "undefined"))) {
            params = params.append("annoImpegno", annoImpegno);
        }

        if (!((annoProvvedimento == undefined) || (annoProvvedimento == "") || (annoProvvedimento == "undefined"))) {
            params = params.append("annoProvvedimento", annoProvvedimento);
        }

        if (!((numeroProvvedimento == undefined) || (numeroProvvedimento == "") || (numeroProvvedimento == "undefined"))) {
            params = params.append("numeroProvvedimento", numeroProvvedimento);
        }

        if (!((direzioneProvvedimento == undefined) || (direzioneProvvedimento == "") || (direzioneProvvedimento == "undefined"))) {
            params = params.append("direzioneProvvedimento", direzioneProvvedimento);
        }

        if (!((idDirezioneProvvedimento == undefined) || (idDirezioneProvvedimento == "") || (idDirezioneProvvedimento == "undefined"))) {
            params = params.append("idDirezioneProvvedimento", idDirezioneProvvedimento);
        }

        if (!((numeroCapitolo == undefined) || (numeroCapitolo == "") || (numeroCapitolo == "undefined"))) {
            params = params.append("numeroCapitolo", numeroCapitolo);
        }

        if (!((ragsoc == undefined) || (ragsoc == "") || (ragsoc == "undefined"))) {
            params = params.append("ragsoc", ragsoc);
        }

        if (!((numeroImpegno == undefined) || (numeroImpegno == "") || (numeroImpegno == "undefined"))) {
            params = params.append("numeroImpegno", numeroImpegno);
        }

        if (!((idSoggetto == undefined) || (idSoggetto == "") || (idSoggetto == "undefined"))) {
            params = params.append("idSoggetto", idSoggetto);
        }

        if (!((isImpegniReimputati == undefined)) || (isImpegniReimputati == "") || (isImpegniReimputati == "undefined")) {
            params = params.append("isImpegniReimputati", isImpegniReimputati);
        }

        return this.http.get<Array<ImpegnoDTO>>(url, { params: params });
    }

    cercaDettaglioAtto(user: UserInfoSec, idSoggetto: string, idImpegno: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercadettaglioatto';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("idImpegno", idImpegno)

        return this.http.get<DettaglioAttoDTO>(url, { params: params });
    }


    cercaDirezioneConImpegno(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercaDirezioneconimpegno';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<EnteCompetenzaDTO>>(url, { params: params });
    }

    cercaListaBandoLinea(user: UserInfoSec, idImpegni: Array<number>, idEnteCompetenza: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercalistabandolinea';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idEnteCompetenza", idEnteCompetenza)

        return this.http.post<Array<BandolineaImpegnoDTO>>(url, idImpegni, { params: params });
    }

    cercaListaProgetti(user: UserInfoSec, idImpegni: Array<number>, progrBandolinea: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercalistaprogetti';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandolinea", progrBandolinea)

        return this.http.post<Array<ProgettoImpegnoDTO>>(url, idImpegni, { params: params });
    }

    cercaProgettiDaAssociare(user: UserInfoSec, idImpegni: Array<number>, progrBandolinea: string, idProgetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercaprogettiDaassociare';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandolinea", progrBandolinea)
            .set("idProgetto", idProgetto)

        return this.http.post<Array<ProgettoImpegnoDTO>>(url, idImpegni, { params: params });
    }

    cercaBandoLineaDaAssociare(user: UserInfoSec, idImpegni: Array<number>, idEnteCompetenza: string, progrBandolinea: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercabandolineadaassociare';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idEnteCompetenza", idEnteCompetenza)
            .set("progrBandolinea", progrBandolinea)

        return this.http.post<Array<BandolineaImpegnoDTO>>(url, idImpegni, { params: params });
    }

    associaBandoLineaImpegni(user: UserInfoSec, idSoggetto: string, gestioneImpegniDTO: GestioneImpegnoDTO) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/associabandolineaimpegni';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)

        return this.http.post<EsitoOperazioneAssociaImpegnoDTO>(url, gestioneImpegniDTO, { params: params });
    }

    associaProgettiImpegni(user: UserInfoSec, gestioneImpegniDTO: GestioneImpegnoDTO) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/associaprogettiimpegni';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.post<EsitoOperazioneAssociaImpegnoDTO>(url, gestioneImpegniDTO, { params: params });
    }

    cercaBandoLineaAssociati(user: UserInfoSec, idSoggetto: string, listaIdImpegno: Array<number>) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercabandolineaassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)

        return this.http.post<Array<BandolineaImpegnoDTO>>(url, listaIdImpegno, { params: params });
    }

    cercaProgettiAssociati(user: UserInfoSec, idSoggetto: string, listaIdImpegno: Array<number>, annoEsercizio: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercaprogettiassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("annoEsercizio", annoEsercizio)

        return this.http.post<Array<ProgettoImpegnoDTO>>(url, listaIdImpegno, { params: params });
    }

    cercaProgettiAssociatiPerBeneficiario(user: UserInfoSec, idSoggetto: string, listaIdImpegno: Array<number>, annoEsercizio: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/cercaprogettiassociatiperbeneficiario';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("annoEsercizio", annoEsercizio)

        return this.http.post<Array<ProgettoImpegnoDTO>>(url, listaIdImpegno, { params: params });
    }

    disassociaProgettiGestAss(user: UserInfoSec, beneficiario: BeneficiarioVo) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/disassociaprogettigestass';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.post<EsitoOperazioneDisassociaDTO>(url, beneficiario, { params: params });
    }

    disassociaBandoLineaGestAss(user: UserInfoSec, impegni: GestioneImpegnoDTO) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/disassociabandolineagestass';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.post<EsitoOperazioneDisassociaDTO>(url, impegni, { params: params });
    }

    acquisisciImpegno(user: UserInfoSec, annoEsercizio: string, annoImpegno: string, numeroImpegno: string, idSoggetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/bilancio/acquisisciimpegno';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("annoEsercizio", annoEsercizio)
            .set("annoImpegno", annoImpegno)
            .set("numeroImpegno", numeroImpegno)
            .set("idSoggetto", idSoggetto)

        return this.http.get<EsitoOperazioneAggiornaImpegnoDTO>(url, { params: params });
    }
}