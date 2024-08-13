/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { CodiceDescrizioneDTO } from "src/app/gestione-spesa-validata/commons/dto/codice-descrizione-dto";

export class InizializzaDocumentiDiProgettoDTO {
    constructor(
        public comboBeneficiari: Array<CodiceDescrizioneDTO>,
        public comboTipiDocumentoIndex: Array<CodiceDescrizioneDTO>,
        public comboTipiDocumentoIndexUploadable: Array<CodiceDescrizioneDTO>,
        public categorieAnagrafica: Array<CodiceDescrizioneDTO>,
        public dimMaxSingoloFile: number,
        public estensioniConsentite: Array<string>
    ) { }
}