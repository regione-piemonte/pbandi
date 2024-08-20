/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from 'src/app/core/services/config.service';
import { OperazioneVO } from '../commons/dto/operazione-vo';
import { map } from 'rxjs/operators';
import { Observable, Subject } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { DatePipe, DecimalPipe } from '@angular/common';

@Injectable()
export class SharedService {
    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private datePipe: DatePipe
    ) { }

    private drawerExpanded = new Subject<boolean>();

    setDrawerExpanded(value: boolean) {
        this.drawerExpanded.next(value);
    }

    getDrawerExpanded() {
        return this.drawerExpanded.asObservable();
    }

    getOperazioni() {
        let url = this.configService.getApiURL() + "/restfacade/home/operazioni";
        return this.http.get<Array<OperazioneVO>>(url);
    }

    chiudiAttivita(idProgetto: number, descBreveTask: string): Observable<string> {
        let url = this.configService.getApiURL() + "/restfacade/attivita/chiudiAttivita";
        let params = new HttpParams().set("idProgetto", idProgetto.toString()).set("descBreveTask", descBreveTask);
        return this.http.get<string>(url, { params: params });
    }

    getContenutoDocumentoById(idDocumentoIndex: number) {
        let url = this.configService.getApiURL() + `/restfacade/affidamenti/documenti/${idDocumentoIndex}`;
        return this.http.get(url, { responseType: 'blob' }).pipe(map(
            (res) => {
                return new Blob([res]);
            }));
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

    parseDate(value: any): Date | null {
        if ((typeof value === 'string') && (value.includes('/'))) {
            const str = value.split('/');

            const year = Number(str[2]);
            const month = Number(str[1]) - 1;
            const date = Number(str[0]);

            return new Date(year, month, date);
        } else if ((typeof value === 'string') && (value.includes('-'))) {
            const str = value.split('-');

            const year = Number(str[0]);
            const month = Number(str[1]) - 1;
            const date = Number(str[2]);
            return new Date(year, month, date);
        } else if ((typeof value === 'string') && value === '') {
            return new Date();
        }
        const timestamp = typeof value === 'number' ? value : Date.parse(value);
        return isNaN(timestamp) ? null : new Date(timestamp);
    }

    formatDateToString(value: Date): string {
        if (!value) {
            return null;
        }
        return this.datePipe.transform(value, 'dd/MM/yyyy');
    }

    isDateEqual(date1: Date, date2: Date) {
        if (date1.getFullYear() === date2.getFullYear()
            && date1.getMonth() === date2.getMonth()
            && date1.getDate() === date2.getDate()) {
            return true;
        }
        return false;
    }
}