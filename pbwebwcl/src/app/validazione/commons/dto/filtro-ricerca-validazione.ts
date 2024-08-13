/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { FormControl } from "@angular/forms";
import { DecodificaDTO } from "src/app/rendicontazione/commons/dto/decodifica-dto";
import { DecodificaChecked } from "./decodifica-checked";

export class FiltroRicercaValidazione {
    constructor(
        public tipologiaDocSelected: DecodificaDTO,
        public numDocSpesa: string,
        public dataDoc: FormControl,
        public tipologiaFornitoreSelected: DecodificaDTO,
        public cfFornitore: string,
        public taskSelected: string,
        public pIvaFornitore: string,
        public denomFornitore: string,
        public cognomeFornitore: string,
        public nomeFornitore: string,
        public statiDocumentiSelected: Array<DecodificaChecked>
    ) { }
}