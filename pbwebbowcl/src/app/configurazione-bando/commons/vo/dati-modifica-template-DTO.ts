/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { SezioneVO } from "./sezione-DTO";

export class DatiModificaTemplate { 
    constructor(
        public sezioni: Array<SezioneVO>,
        public descTipoDocumento: string,
        public placeholders: Array<string>,
        public serialVersionUID: number
    ) { }
}