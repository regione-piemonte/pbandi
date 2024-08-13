/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe, DecimalPipe } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup, NgForm, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Observable, Subject } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { ConfigService } from 'src/app/core/services/config.service';
import { OperazioneVO } from '../commons/dto/operazione-vo';
import { DrawerService } from '@pbandi/common-lib';

@Injectable()
export class SharedService {
    constructor(
        private configService: ConfigService,
        private http: HttpClient,
        private datePipe: DatePipe,
        private drawerService: DrawerService,
    ) { }

    getDrawerExpanded() {
        return this.drawerService.getDrawerExpanded();
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

    checkFormFieldRequired(form: NgForm, name: string) {
        if (form.form.get(name) && !form.form.get(name).value) {
            form.form.get(name).setErrors({ error: 'required' });
            return true;
        }
        return false;
    }

    addValidatorOnForm(elements: string[], form: FormGroup, validator: ValidationErrors) {
        if (elements?.length) {
            elements.forEach(r => {
                let el = form.get(r);
                if (el) {
                    el.setValidators([<ValidatorFn>validator]);
                    el.updateValueAndValidity();
                }
            });
        }
    }

    removeValidatorOnForm(elements: string[], form: FormGroup) {
        if (elements?.length) {
            elements.forEach(r => {
                let el = form.get(r);
                if (el) {
                    el.clearValidators();
                    el.updateValueAndValidity();
                }
            });
        }
    }
}