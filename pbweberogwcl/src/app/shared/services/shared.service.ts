/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe, DecimalPipe } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Observable, Subject } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { OperazioneVO } from '../commons/dto/operazione-vo';

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

    getDateFromStrDateIta(date: string): Date { // gg/mm/aaaa
        let rDate: Date;
        if (date && date.length >= 10) {
            rDate = new Date(parseInt(date.substr(6, 4)), parseInt(date.substr(3, 2)) - 1, parseInt(date.substr(0, 2)))
        }
        return rDate;
    }

    getDateFromStrDate(date: string): Date {    // aaaammgg
        let rDate: Date;
        if (date && date.length >= 10) {
            rDate = new Date(parseInt(date.substr(0, 4)), parseInt(date.substr(4, 2)), parseInt(date.substr(6, 2)))
        }
        return rDate;
    }

    getStrDateFromDate(date: Date): string {    // aaaammgg hhmmss
        let rDate: string = '';
        if (date) {
            rDate = date.getFullYear() + (((date.getMonth() + 1) <= 9) ? '0' : '') + (date.getMonth() + 1) + ((date.getDate() <= 9) ? '0' : '') + date.getDate() + ' ' + ((date.getHours() <= 9) ? '0' : '') + date.getHours() + ((date.getMinutes() <= 9) ? '0' : '') + date.getMinutes() + ((date.getSeconds() <= 9) ? '0' : '') + date.getSeconds();
        }
        return rDate;
    }

    checkFieldFormPattern(form: NgForm, field: string, fieldName: string, pattern: RegExp) {
        if (field) {
            let res = field.match(pattern);
            if (!res) {
                form.form.get(fieldName).setErrors({ error: 'notValid' });
                form.form.get(fieldName).markAsTouched();
                return true;
            }
        }
        return false
    }

    parseDate(value: any): Date | null {
        if ((typeof value === 'string') && (value.includes('/'))) {
            const str = value.split('/');

            const year = Number(str[2]);
            const month = Number(str[1]) - 1;
            const date = Number(str[0]);

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

    isBeneficiario(user: UserInfoSec): boolean {
        if (user && (user.codiceRuolo === Constants.CODICE_RUOLO_BEN_MASTER || user.codiceRuolo === Constants.CODICE_RUOLO_PERSONA_FISICA)) {
            return true;
        }
        return false;
    }

    isIstruttore(user: UserInfoSec): boolean {
        if (user && (user.codiceRuolo === Constants.CODICE_RUOLO_ADG_IST_MASTER || user.codiceRuolo === Constants.CODICE_RUOLO_ADG_ISTRUTTORE
            || user.codiceRuolo === Constants.CODICE_RUOLO_OI_IST_MASTER)) {
            return true;
        }
        return false;
    }
}