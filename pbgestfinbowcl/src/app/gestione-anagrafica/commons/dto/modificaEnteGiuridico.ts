/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AnagraficaBeneficiarioSec } from "src/app/shared/commons/dto/anagrafica-beneficiario";

export class ModificaEnteGiuridico {
    constructor(
        public idSoggetto: any,
        public anagBene: AnagraficaBeneficiarioSec,
        public isUpdate: boolean
    ) { }
}
