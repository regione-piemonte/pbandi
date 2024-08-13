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
import { ResponseCodeMessage } from '../../shared/commons/dto/response-code-message-dto';
import { ModelloDTO } from '../commons/vo/modello-DTO';
import { map } from 'rxjs/operators';
import { TemplateDTO } from '../commons/vo/template-DTO';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';

@Injectable()
export class ModelliDocumentoService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    findBandoLinea(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/findbandolinea';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    findModelliDaAssociare(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/findmodellidaassociare';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    inserisciModello(request: ModelloDTO) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/inseriscimodello';

        return this.http.post<EsitoAssociazioneDTO>(url, request);
    }

    findModelliAssociati(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/findmodelliassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<TemplateDTO>>(url, { params: params });
    }

    eliminaModelloAssociato(user: UserInfoSec, progrBandoLineaIntervento: string, idTipoDocumentoIndex: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/eliminamodelloassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idTipoDocumentoIndex", idTipoDocumentoIndex)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    modificaTipoDocumento(user: UserInfoSec, progrBandoLinea: string, idTipoDocumento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/modificatipodocumento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLinea", progrBandoLinea)
            .set("idTipoDocumento", idTipoDocumento)

        return this.http.get<any>(url, { params: params });
    }

    salvaMoficheoDocumento(user: UserInfoSec, paramSezioni: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/salvamoficheodocumento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.post<ResponseCodeMessage>(url, paramSezioni, { params: params });
    }

    generaDocumento(user: UserInfoSec, progrBandolineaIntervento: string, idTipoDocumento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/generadocumento';

        let params = new HttpParams().set("idU", user.idUtente.toString());
        if (progrBandolineaIntervento) {
            params = params.set("progrBandolineaIntervento", progrBandolineaIntervento);
        }
        if (idTipoDocumento) {
            params = params.set("idTipoDocumento", idTipoDocumento);
        }
        return this.http.get(url, { params: params });
    }

    downloadDocumento(user: UserInfoSec, idTipoDocumentoIndex: string, progrBandolinea: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/downloaddocumento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idTipoDocumentoIndex", idTipoDocumentoIndex)
            .set("progrBandolinea", progrBandolinea)

        return this.http.get(url, { params: params, responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res]);
            }));
    }

    getNomeDocumento(user: UserInfoSec, progrBandolineaIntervento: string, idTipoDocumento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/modellididocumento/getnomedocumento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandolineaIntervento", progrBandolineaIntervento)
            .set("idTipoDocumento", idTipoDocumento)

        return this.http.get(url, { params: params, responseType: 'text' });
    }
}