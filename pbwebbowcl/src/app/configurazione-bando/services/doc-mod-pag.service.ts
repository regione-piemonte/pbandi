/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { ConfigService } from 'src/app/core/services/config.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { DocPagamBandoLineaDTO } from '../commons/vo/doc-pagam-bando-linea-DTO';
import { GestioneBackOfficeEsitoGenerico } from '../commons/vo/gestione-back-office-esito-generico-vo';
import { EsitoAssociazioneDTO } from '../commons/vo/esito-associazione-vo';
import { Decodifica } from 'src/app/shared/commons/dto/decodifica';

@Injectable()
export class DocModPagService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient,
    ) { }

    tipiDocumento(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/tipidocumento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    modalitaPagamento(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/modalitapagamento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<Decodifica>>(url, { params: params });
    }

    docPagamentiAssociati(user: UserInfoSec, progrBandoLineaIntervento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/docpagamentiassociati';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)

        return this.http.get<Array<DocPagamBandoLineaDTO>>(url, { params: params });
    }

    docPagamentiAssociatiTuttiBL(user: UserInfoSec) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/docpagamentiassociatituttibl';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())

        return this.http.get<Array<DocPagamBandoLineaDTO>>(url, { params: params });
    }

    eliminaDocPagamAssociato(user: UserInfoSec, progrEccezBanLinDocPag: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/eliminadocpagamassociato';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrEccezBanLinDocPag", progrEccezBanLinDocPag)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    eliminaDocPagamAssociatoTuttiBL(user: UserInfoSec, idTipoDocumento: string, idModalitaPagamento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/eliminadocpagamassociatotuttibl';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idTipoDocumento", idTipoDocumento)
            .set("idModalitaPagamento", idModalitaPagamento)

        return this.http.get<GestioneBackOfficeEsitoGenerico>(url, { params: params });
    }

    associaDocPagamento(user: UserInfoSec, progrBandoLineaIntervento: string, idTipoDocumento: string, idModalitaPagamento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/associadocpagamento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("progrBandoLineaIntervento", progrBandoLineaIntervento)
            .set("idTipoDocumento", idTipoDocumento)
            .set("idModalitaPagamento", idModalitaPagamento)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    associaDocPagamTuttiBL(user: UserInfoSec, idTipoDocumento: string, idModalitaPagamento: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/associadocpagamtuttibl';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("idTipoDocumento", idTipoDocumento)
            .set("idModalitaPagamento", idModalitaPagamento)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    inserisciTipoDocumentoSpesa(user: UserInfoSec, descrizione: string, descrizioneBreve: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/inseriscitipodocumentospesa';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("descrizione", descrizione)
            .set("descrizioneBreve", descrizioneBreve)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }

    inserisciModalitaPagamento(user: UserInfoSec, descrizione: string, descrizioneBreve: string) {
        let url = this.configService.getApiURL() + '/restfacade/configurazonebando/configurazonebandolinea/pagamento/inseriscimodalitapèagamento';

        let params = new HttpParams()
            .set("idU", user.idUtente.toString())
            .set("descrizione", descrizione)
            .set("descrizioneBreve", descrizioneBreve)

        return this.http.get<EsitoAssociazioneDTO>(url, { params: params });
    }
}