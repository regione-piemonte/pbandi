/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatiFatturaElettronica } from "./dati-fattura-elettronica";
import { FornitoreFormDTO } from "./fornitore-form-dto";

export class EsitoLetturaXmlFattElett {
    constructor(
        public datiFatturaElettronica: DatiFatturaElettronica,
        public fornitoreDb: FornitoreFormDTO,
        public esito: string
    ) { }
}