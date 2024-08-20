/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { sanitizeIdentifier } from "@angular/compiler";

export class StoricoRichiesta {
    constructor(
        public  id_richiesta: number,
        public  destinatario_mittente: string,
        public  dt_comunicazione_esterna: string,
        public  numero_protocollo: number,
        public  motivazione: string,
        public  cognome: string,
        public  desc_tipo_comunicazione: string,
    ) {}
}
