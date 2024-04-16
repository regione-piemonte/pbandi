/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Comune } from "./comune";

export class SoggettoPF {
    constructor(
        public iban: string,
        public cognome: string,
        public nome: string,
        public sesso: string,
        public dataNascita: string,
        public indirizzoRes: string,
        public capRes: string,
        public emailRes: string,
        public faxRes: string,
        public telefonoRes: string,
        public comuneRes: Comune,
        public comuneNas: Comune,
        public codiceFiscale: string,
        public ruolo: Array<string>,
        public idRelazioneColBeneficiario: string,
        public descRelazioneColBeneficiario: string,
        public numCivicoRes: string
    ) { }
}