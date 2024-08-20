/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { OperazioneVO } from '../commons/dto/operazione-vo';
import { DecimalPipe } from '@angular/common';

@Injectable()
export class SharedService {
    constructor(
        private configService: ConfigService,
        private http: HttpClient
    ) { }

    getOperazioni() {
        let url = this.configService.getApiURL() + "/restfacade/home/operazioni";
        return this.http.get<Array<OperazioneVO>>(url);
    }

    chiudiAttivita(idProgetto: number, descBreveTask: string): Observable<string> {
        let url = this.configService.getApiURL() + "/restfacade/attivita/chiudiAttivita";
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("descBreveTask", descBreveTask);
        return this.http.get<string>(url, { params: params });
    }

    navigateToOperazioneEsterna(configComponentURL: string, idOP: number, destComponentPath: string, user: UserInfoSec) {
        let url: string = `${configComponentURL}/#/drawer/${idOP}/${destComponentPath}?idSg=${user.idSoggetto}`
            + `&idSgBen=${(user.beneficiarioSelezionato && user.beneficiarioSelezionato.idBeneficiario ? user.beneficiarioSelezionato.idBeneficiario : "0")}`
            + `&idU=${user.idUtente}&role=${user.codiceRuolo}`;
        window.location.href = url;
    }

    navigateToArchivioFile(user: UserInfoSec) {
        let url: string = `${this.configService.getPbwebURL()}/#/drawer/${Constants.ID_OPERAZIONE_ARCHIVIO_FILE}/archivioFile?idSg=${user.idSoggetto}`
            + `&idSgBen=${user.beneficiarioSelezionato.idBeneficiario}&idU=${user.idUtente}&role=${user.codiceRuolo}&taskIdentity=${Constants.DESCR_BREVE_ARCHIVIOFILE}`;
        window.location.href = url;
    }

    //UTILS
    getNumberFromFormattedString(importoFormatted: string) {
        if (!importoFormatted) return null;
        importoFormatted = importoFormatted.replace(/[.]/g, '');
        importoFormatted = importoFormatted.replace(',', '.');
        if (!Number.isNaN(+importoFormatted)) {
            return +importoFormatted;
        }
        return null;
    }

    formatValue(importoFormatted: string) {
        return new DecimalPipe('it').transform(importoFormatted, '0.2');
    }
}