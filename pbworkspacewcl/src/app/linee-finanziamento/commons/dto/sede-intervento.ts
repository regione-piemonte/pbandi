/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Comune } from "./comune";

export class SedeIntervento {
    constructor(
        public id: number,
        public partitaIva: string,
        public indirizzo: string,
        public cap: string,
        public email: string,
        public fax: string,
        public telefono: string,
        public comuneSede: Comune,
        public numeroCivico: string,

    ) { }
}