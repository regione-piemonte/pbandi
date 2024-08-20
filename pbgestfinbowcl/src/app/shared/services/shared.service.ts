/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DecimalPipe } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';
import { Observable, Subject } from 'rxjs';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { OperazioneVO } from '../commons/dto/operazione-vo';

const emailRegex = /^[a-zA-Z0-9!#$%&'*+\/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+\/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$/g;
const numberRegex = /^[0-9]*$/g;

@Injectable()
export class SharedService {

    constructor(
        private configService: ConfigService,
        private http: HttpClient
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
    getNumberFromFormattedString(importoFormatted: string) {
        if (!importoFormatted) return null;
        importoFormatted = importoFormatted.replace(/[.]/g, '');
        importoFormatted = importoFormatted.replace(/[,]/g, '.'); // Era ',' e sostituiva solo una occorrenza
        if (!Number.isNaN(+importoFormatted)) {
            return +importoFormatted;
        }
        return null;
    }

    formatValue(importoFormatted: string) {
        return new DecimalPipe('it').transform(importoFormatted, '0.2');
    }

    checkFormFieldRequired(form: FormGroup, name: string) {
        if (form.get(name) && !form.get(name).value) {
            form.get(name).setErrors({ required: true });
            return true;
        }
        return false;
    }

    checkFormFieldMinlength(form: FormGroup, name: string, minlength: number) {
        if (form.get(name) && form.get(name).value && form.get(name).value.length < minlength) {
            form.get(name).setErrors({ minlength: true });
            return true;
        }
        return false;
    }

    checkDateAfterToday(control: AbstractControl) {
        let today = new Date();
        today.setHours(0, 0, 0, 0);
        if (control && control.value && control.value > today) {
            control.setErrors({ maggioreToday: true });
            return true;
        }
        return false;
    }

    checkFormFieldEmailPattern(form: FormGroup, name: string) {
        if (form.get(name) && form.get(name).value && form.get(name).value?.length) {
            let res = form.get(name)?.value.match(emailRegex);
            if (!res?.length) {
                form.get(name).setErrors({ notValid: true });
                return true;
            }
        }
        return false;
    }

    checkFormFieldNumberPattern(form: FormGroup, name: string) {
        if (form.get(name) && form.get(name).value && form.get(name).value?.length) {
            let res = form.get(name)?.value.match(numberRegex);
            if (!res?.length) {
                form.get(name).setErrors({ onlyNumbers: true });
                return true;
            }
        }
        return false;
    }

}