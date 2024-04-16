/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { BeneficiarioSec } from 'src/app/core/commons/vo/beneficiario-vo';
import { RuoloSec } from './ruolo';

export class UserInfoSec {
    constructor(
        public idIride: string,
        public codFisc: string,
        public cognome: string,
        public nome: string,
        public ente: string,
        public ruolo: string,
        public idUtente: number,
        public tipoBeneficiario: string,
        public codiceRuolo: string,
        public beneficiarioSelezionato: BeneficiarioSec,
        public idSoggetto: number,
        public idSoggettoIncaricante: number,
        public beneficiarioSelezionatoAutomaticamente: boolean,
        public codRuolo: string,
        public isIncaricato: string,
        public ruoloHelp: string,
        public ruoli: Array<RuoloSec>,
        public beneficiari: Array<BeneficiarioSec>,
        public beneficiariCount: number
    ) { }
}