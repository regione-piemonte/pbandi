/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { AnagraficaBeneFisico } from "./anagraficaBeneFisico";

export class ModificaPersonaFisica {
    constructor(
        public idSoggetto: any,
        public anagBene: AnagraficaBeneFisico
    ) { }
}
