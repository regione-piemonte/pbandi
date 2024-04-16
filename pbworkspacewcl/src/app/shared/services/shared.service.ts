/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatePipe } from "@angular/common";
import { Injectable } from "@angular/core";
import { NgForm } from "@angular/forms";
import { Constants } from "src/app/core/commons/util/constants";
import { UserInfoSec } from "src/app/core/commons/vo/user-info";
import { ConfigService } from "src/app/core/services/config.service";

export const patternEmail = /^[a-zA-Z0-9!#$%&'*+\/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+\/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$/g;
export const patternCap = /^([0-9]){5}$/g;
export const patternTelFax = /^([0-9])+$/g;

@Injectable()
export class SharedService {

  constructor(
    private configService: ConfigService,
    private datePipe: DatePipe) { }

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

  checkCodiceFiscalePF(form: NgForm, cf: string, fieldName: string) {
    let setdisp: Array<number> = [1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23];
    let s: number = 0;
    if (cf && cf.length > 0 && form.form.get(fieldName)) {
      for (let i = 1; i <= 13; i += 2) {
        let c: number = cf.charCodeAt(i);
        if (c >= ('0').charCodeAt(0) && c <= ('9').charCodeAt(0)) {
          s = s + c - ('0').charCodeAt(0);
        } else {
          s = s + c - ('A').charCodeAt(0);
        }
      }
      for (let i = 0; i <= 14; i += 2) {
        let c = cf.charCodeAt(i);
        if (c >= ('0').charCodeAt(0) && c <= ('9').charCodeAt(0)) {
          c = c - ('0').charCodeAt(0) + ('A').charCodeAt(0);
        }
        s = s + setdisp[c - ('A').charCodeAt(0)];
      }
      if (s % 26 + ('A').charCodeAt(0) != cf.charCodeAt(15)) {
        form.form.get(fieldName).setErrors({ error: 'notValidPF' });
        form.form.get(fieldName).markAsTouched();
        return true;
      }
    }
    if (form.form.get(fieldName)) {
      form.form.get(fieldName).setErrors(null);
    }
    return false;
  }

  checkCodiceFiscaleEG(form: NgForm, cf: string, fieldName: string) {
    //questi messaggi non vengono mostrati, viene mostrato solo quello del cf persona fisica
    if (cf && cf.length > 0 && form.form.get(fieldName)) {
      if (cf.length !== 11) {
        form.form.get(fieldName).setErrors({ error: 'length' });
        form.form.get(fieldName).markAsTouched();
        return true;
      }
      for (let i = 0; i < 11; i++) {
        if (cf.charCodeAt(i) < ('0').charCodeAt(0) || cf.charCodeAt(i) > ('9').charCodeAt(0)) {
          form.form.get(fieldName).setErrors({ error: 'notValidEG' });
          form.form.get(fieldName).markAsTouched();
          return true;
        }
      }
      let s: number = 0;
      for (let i = 0; i <= 9; i += 2) {
        s += cf.charCodeAt(i) - ('0').charCodeAt(0);
      }

      for (let i = 1; i <= 9; i += 2) {
        let c: number = 2 * (cf.charCodeAt(i) - ('0').charCodeAt(0));
        if (c > 9)
          c = c - 9;
        s += c;
      }
      if ((10 - s % 10) % 10 !== cf.charCodeAt(10) - ('0').charCodeAt(0)) {
        form.form.get(fieldName).setErrors({ error: 'codControllo' });
        form.form.get(fieldName).markAsTouched();
        return true;
      }
    }
    return false;
  }

}
