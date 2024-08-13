/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { CodiceDescrizioneDTO } from '../commons/dto/codice-descrizione-dto';
import { IstruttoreDTO } from '../commons/dto/istruttore-dto';
import { ProgettoDTO } from '../commons/dto/progetto-dto';
import { ResponseCodeMessage } from 'src/app/shared/commons/dto/response-code-message-dto';
import { BeneficiarioProgettoDTO } from '../commons/dto/beneficiario-progetto-dto';
import { EsitoOperazioneAssociaProgettiAIstruttore } from '../commons/dto/esito-operazione-associa-progetti-istruttore';
import { BandoLineaAssociatiAIstruttoreVO } from '../commons/dto/bando-linea-associati-istruttore-dto';
import { BandoLineaDaAssociareAIstruttoreVO } from '../commons/dto/bando-linea-da-associare-istruttore-dto';

@Injectable()
export class AssociazioneIstruttoreProgettiService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    cercaBandi(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/cercabandi';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<CodiceDescrizioneDTO>>(url, { params: params });
    }

    cercaIstruttore(user: UserInfoSec, idSoggetto: string, nome: string, cognome: string, codicefiscale: string, idBando: string, tipoAnagrafica: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/cercaistruttore';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());
        params = params.append("idSoggetto", idSoggetto);

        if (!((nome == undefined) || (nome == "") || (nome == "undefined"))) {
            params = params.append("nome", nome);
        }

        if (!((cognome == undefined) || (cognome == "") || (cognome == "undefined"))) {
            params = params.append("cognome", cognome);
        }

        if (!((codicefiscale == undefined) || (codicefiscale == "") || (codicefiscale == "undefined"))) {
            params = params.append("codicefiscale", codicefiscale);
        }

        if (!((idBando == undefined) || (idBando == "") || (idBando == "undefined"))) {
            params = params.append("idBando", idBando);
        }

        if (!((tipoAnagrafica == undefined) || (tipoAnagrafica == "") || (tipoAnagrafica == "undefined"))) {
            params = params.append("tipoAnagrafica", tipoAnagrafica);
        }

        return this.http.get<Array<IstruttoreDTO>>(url, { params: params });
    }

    gestisciAssociazioni(user: UserInfoSec, idSoggettoIstruttore: string, isIstruttoreAffidamenti: boolean) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/gestisciassociazioni';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggettoIstruttore", idSoggettoIstruttore)
            .set("isIstruttoreAffidamenti", isIstruttoreAffidamenti?.toString() || "false");

        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    disProgetti(user: UserInfoSec, idSoggetto: string, codRuolo: string, progrSoggettoProgetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/disprogetti';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("codRuolo", codRuolo)
            .set("progrSoggettoProgetto", progrSoggettoProgetto)

        return this.http.get<ResponseCodeMessage>(url, { params: params });
    }

    cercaProgettiByBandoLinea(user: UserInfoSec, idBandoLinea: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/cercaprogettibybandolinea';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idBandoLinea", idBandoLinea)

        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    cercaBeneficiari(user: UserInfoSec, idBandoLinea: string, idProgetto: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/cercabeneficiari';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());
        params = params.append("idBandoLinea", idBandoLinea);

        if (!((idProgetto == null) || (idProgetto == undefined))) {
            params = params.append("idProgetto", idProgetto);
        }

        return this.http.get<Array<BeneficiarioProgettoDTO>>(url, { params: params });
    }

    cercaProgettiByBeneficiario(user: UserInfoSec, idBandoLinea: string, idSoggettoBeneficiario: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/cercaprogettibybeneficiario';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());
        params = params.append("idBandoLinea", idBandoLinea);

        if (!((idSoggettoBeneficiario == null) || (idSoggettoBeneficiario == undefined))) {
            params = params.append("idSoggettoBeneficiario", idSoggettoBeneficiario);
        }

        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    findProgettiDaAssociare(user: UserInfoSec, idBandoLinea: string, idProgetto: string, idSoggettoBeneficiario: string, idSoggetto: string, isIstruttoriAssociati: string,
        isIstruttoreAffidamenti: boolean) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/findprogettidaassociare';

        let params = new HttpParams({
            fromObject: {
                requiredParam: 'requiredParam'
            }
        });

        params = params.append("idU", user.idUtente.toString());
        params = params.append("idBandoLinea", idBandoLinea);
        params = params.append("isIstruttoriAssociati", isIstruttoriAssociati);
        params = params.append("idSoggetto", idSoggetto);
        params = params.append("isIstruttoreAffidamenti", isIstruttoreAffidamenti?.toString() || "false");

        if (!((idProgetto == null) || (idProgetto == undefined))) {
            params = params.append("idProgetto", idProgetto);
        }

        if (!((idSoggettoBeneficiario == null) || (idSoggettoBeneficiario == undefined))) {
            params = params.append("idSoggettoBeneficiario", idSoggettoBeneficiario);
        }

        return this.http.get<Array<ProgettoDTO>>(url, { params: params });
    }

    associaProgettiAIstruttore(user: UserInfoSec, idProgetto: string, idSoggettoIstruttore: string, idSoggetto: string, codRuolo: string, isIstruttoreAffidamenti: boolean) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/associaprogettiaistruttore';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idProgetto", idProgetto)
            .set("idSoggettoIstruttore", idSoggettoIstruttore)
            .set("idSoggetto", idSoggetto)
            .set("codRuolo", codRuolo)
            .set("isIstruttoreAffidamenti", isIstruttoreAffidamenti?.toString() || "false");

        return this.http.get<EsitoOperazioneAssociaProgettiAIstruttore>(url, { params: params });
    }

    bandolineaAssociati(user: UserInfoSec, idSoggettoIstruttore: string, isIstruttoreAffidamenti: boolean) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/bandolineaassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggettoIstruttore", idSoggettoIstruttore)
            .set("isIstruttoreAffidamenti", isIstruttoreAffidamenti?.toString() || "false");

        return this.http.get<Array<BandoLineaAssociatiAIstruttoreVO>>(url, { params: params });
    }


    dettaglioIstruttori(user: UserInfoSec, idSoggettoIstruttore: string, progBandoLina: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/dettaglioistruttori';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggettoIstruttore", idSoggettoIstruttore)
            .set("progBandoLina", progBandoLina)

        return this.http.get<Array<BandoLineaAssociatiAIstruttoreVO>>(url, { params: params });
    }

    getBandoLineaDaAssociare(user: UserInfoSec, idSoggetto: string, idSoggettoIstruttore: string, descBreveTipoAnagrafica: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/getbandolineadaAssociare';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggetto", idSoggetto)
            .set("idSoggettoIstruttore", idSoggettoIstruttore)
            .set("descBreveTipoAnagrafica", descBreveTipoAnagrafica)

        return this.http.get<Array<BandoLineaDaAssociareAIstruttoreVO>>(url, { params: params });
    }

    associaIstruttoreBandolinea(user: UserInfoSec, progBandoLinaIntervento: string, idSoggettoIstruttore: string, ruolo: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/associaistruttorebandolinea';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progBandoLinaIntervento", progBandoLinaIntervento)
            .set("idSoggettoIstruttore", idSoggettoIstruttore)
            .set("ruolo", ruolo)

        return this.http.get<number>(url, { params: params });
    }

    eliminaAssociazioneIstruttoreBandolinea(user: UserInfoSec, idSoggettoIstruttore: string, progBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/associazioni/eliminaassociazioneistruttorebandolinea';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idSoggettoIstruttore", idSoggettoIstruttore)
            .set("progBandoLineaIntervento", progBandoLineaIntervento)

        return this.http.get<number>(url, { params: params });
    }
}
