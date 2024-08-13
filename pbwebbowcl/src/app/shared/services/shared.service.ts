/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { UserInfoSec } from 'src/app/core/commons/dto/user-info';
import { Constants } from 'src/app/core/commons/util/constants';
import { ConfigService } from 'src/app/core/services/config.service';
import { OperazioneVO } from '../commons/dto/operazione-vo';

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

    //quando si utilizza l'autocomplete, sul blur, se non è stata selezionata nessuna opzione, cancella l'input
    //l'input corrisponde a fields[0], mentre gli altri fields sono campi da cancellare in cascata, se ci sono
    onBlurAutoComplete(form: FormGroup, fields: string[]) {
        if (!(form.get(fields[0])?.value && form.get(fields[0])?.value instanceof Object)) {
            for (let field of fields) {
                form.get(field)?.setValue(null);
            }
        }
    }
}