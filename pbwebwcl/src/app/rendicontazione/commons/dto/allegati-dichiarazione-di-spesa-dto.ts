/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { DatiIntegrazioneDsDTO } from "./dati-integrazione-ds-dto";
import { DocumentoAllegatoDTO } from "./documento-allegato-dto";

export class AllegatiDichiarazioneDiSpesaDTO {
    constructor(
        public allegati: Array<DocumentoAllegatoDTO>,
        public integrazioni: Array<DatiIntegrazioneDsDTO>
    ) { }
}