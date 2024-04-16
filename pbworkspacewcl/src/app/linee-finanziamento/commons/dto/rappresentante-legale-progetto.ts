/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FormControl } from "@angular/forms";
import { CodiceDescrizione } from "./codice-descrizione";
import { IdDescBreveDescEstesa } from "./id-desc-breve-desc-estesa";

export class RappresentanteLegaleProgetto {
    id: number;
    codiceFiscale: string;
    cognome: string;
    nome: string;
    sesso: string;
    dataNascita: FormControl;
    nazioneNascita: IdDescBreveDescEstesa;
    nazioneResidenza: IdDescBreveDescEstesa;
    regioneNascita: CodiceDescrizione;
    regioneResidenza: CodiceDescrizione;
    provinciaNascita: CodiceDescrizione;
    provinciaResidenza: CodiceDescrizione;
    comuneNascita: CodiceDescrizione;
    comuneResidenza: CodiceDescrizione;
    indirizzoResidenza: string;
    civicoResidenza: string;
    capResidenza: string;
    email: string;
    telefono: string;
    fax: string;

    constructor() {
        this.dataNascita = new FormControl();
    }
}