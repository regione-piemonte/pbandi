/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Observable } from 'rxjs';
import { SalvaQualificaRequest } from '../commons/requests/salva-qualifica-request';
import { UserInfoSec } from '../../core/commons/dto/user-info';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { QualificaDTO } from '../commons/dto/qualifica-dto';
import { FornitoreFormDTO } from '../commons/dto/fornitore-form-dto';
import { EsitoDTO } from 'src/app/shared/commons/dto/esito-dto';
import { AttivitaAtecoNodoDTO } from '../commons/dto/attivita-ateco-nodo-dto';
import { NazioneDTO } from '../commons/dto/nazione-dto';
import { FatturaRiferimentoDTO } from '../commons/dto/fattura-riferimento.dto';
import { FornitoreDTO } from '../commons/dto/fornitore-dto';
import { EsitoRicercaFornitori } from '../commons/dto/esito-ricerca-fornitori';
import { SalvaQualificheRequest } from '../commons/requests/salva-qualifiche-request';
import { AssociaFilesRequest } from '../commons/requests/associa-files-request';
import { EsitoAssociaFilesDTO } from '../commons/dto/esito-associa-files-dto';
import { EsitoOperazioneDTO } from 'src/app/shared/commons/dto/esito-operazione-dto';

@Injectable()
export class FornitoreService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    cercaFornitore(idFornitore: number, idProgetto: number, idSoggettoBeneficiario: number): Observable<FornitoreFormDTO> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/cercaFornitore';
        let params = new HttpParams()
            .set("idFornitore", idFornitore.toString())
            .set("idProgetto", idProgetto.toString())
            .set("idSoggettoBeneficiario", idSoggettoBeneficiario.toString());
        return this.http.get<FornitoreFormDTO>(url, { params: params });
    }

    salvaFornitore(fornitoreFormDTO: FornitoreFormDTO, idUtente: number, idSoggettoBeneficiario: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/salvaFornitore';
        let params = new HttpParams().set("idUtente", idUtente.toString()).set("idSoggettoBeneficiario", idSoggettoBeneficiario.toString());
        return this.http.post<EsitoDTO>(url, fornitoreFormDTO, { params: params });
    }

    salvaQualifica(request: SalvaQualificaRequest): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/salvaQualifica';
        return this.http.post<EsitoDTO>(url, request);
    }

    getQualificheFornitore(userInfo: UserInfoSec, idFornitore: number) {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/qualificheFornitore';

        let params = new HttpParams()
            .set("idFornitore", idFornitore.toString())
            .set("idUtente", userInfo.idUtente.toString())

        return this.http.get<Array<QualificaDTO>>(url, { params: params });
    }

    getFattureFornitore(idProgetto: number, idFornitore: number): Observable<Array<FatturaRiferimentoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/fattureFornitore';
        let params = new HttpParams().set("idFornitore", idFornitore.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<Array<FatturaRiferimentoDTO>>(url, { params: params });
    }

    getAlberoAttivitaAteco(idUtente: number): Observable<Array<AttivitaAtecoNodoDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/alberoAttivitaAteco';
        let params = new HttpParams().set("idUtente", idUtente.toString());
        return this.http.get<Array<AttivitaAtecoNodoDTO>>(url, { params: params });
    }

    getNazioni(): Observable<Array<NazioneDTO>> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/nazioni';
        return this.http.get<Array<NazioneDTO>>(url);
    }

    ricercaElencoFornitori(filtro: FornitoreDTO, idSoggettoBeneficiario: number, idProgetto: number): Observable<EsitoRicercaFornitori> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/ricercaElencoFornitori';
        let params = new HttpParams().set("idSoggettoBeneficiario", idSoggettoBeneficiario.toString()).set("idProgetto", idProgetto.toString());
        return this.http.post<EsitoRicercaFornitori>(url, filtro, { params: params });
    }

    salvaQualifiche(request: SalvaQualificheRequest): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/salvaQualifiche';
        return this.http.post<EsitoDTO>(url, request);
    }

    associaAllegatiAFornitore(request: AssociaFilesRequest): Observable<EsitoAssociaFilesDTO> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/associaAllegatiAFornitore';
        return this.http.post<EsitoAssociaFilesDTO>(url, request);
    }

    disassociaAllegatoFornitore(idDocumentoIndex: number, idFornitore: number, idProgetto: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/disassociaAllegatoFornitore';
        let params = new HttpParams().set("idDocumentoIndex", idDocumentoIndex.toString()).set("idFornitore", idFornitore.toString()).set("idProgetto", idProgetto.toString());
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    esistonoDocumentiAssociatiAQualificaFornitore(progrFornitoreQualifica: number): Observable<boolean> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/esistonoDocumentiAssociatiAQualificaFornitore';
        let params = new HttpParams().set("progrFornitoreQualifica", progrFornitoreQualifica.toString());
        return this.http.get<boolean>(url, { params: params });
    }

    eliminaQualifica(progrFornitoreQualifica: number): Observable<EsitoDTO> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/eliminaQualifica';
        let params = new HttpParams().set("progrFornitoreQualifica", progrFornitoreQualifica.toString());
        return this.http.get<EsitoDTO>(url, { params: params });
    }

    eliminaFornitore(idFornitore: number): Observable<EsitoOperazioneDTO> {
        let url = this.configService.getApiURL() + '/restfacade/fornitore/eliminaFornitore';
        let params = new HttpParams().set("idFornitore", idFornitore.toString());
        return this.http.get<EsitoOperazioneDTO>(url, { params: params });
    }
}
