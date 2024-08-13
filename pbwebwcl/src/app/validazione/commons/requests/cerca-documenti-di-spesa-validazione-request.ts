/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class CercaDocumentiDiSpesaValidazioneRequest {
    constructor(
        public idDichiarazioneSpesa: number,
        public idTipoDocumentoSpesa: number,
        public idTipoFornitore: number,
        public dataDocumento: Date,
        public numeroDocumento: string,
        public cognomeFornitore: string,
        public nomeFornitore: string,
        public codiceFiscaleFornitore: string,
        public partitaIvaFornitore: string,
        public denominazioneFornitore: string,
        public task: string,
        public statiDocumento: Array<string>
    ) { }
}