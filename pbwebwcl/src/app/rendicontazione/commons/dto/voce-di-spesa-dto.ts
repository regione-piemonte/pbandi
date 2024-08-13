/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class VoceDiSpesaDTO {
    constructor(
        public idVoceDiSpesa: number,
        public idRigoContoEconomico: number,
        public idProgetto: number,
        public idQuotaParteDocSpesa: number,
        public idDocSpesa: number,
        public importo: number,
        public descVoceDiSpesa: string,
        public importoRichiesto: number,
        public importoAgevolato: number,
        public importoFinanziamento: number,
        public idVoceDiSpesaPadre: number,
        public oreLavorate: number,
        public costoOrario: number,
        public idTipoDocumentoDiSpesa: number,
        public descVoceDiSpesaCompleta: string,
        public importoRendicontato: number,
        public importoResiduoAmmesso: number,
        public descVoceDiSpesaPadre: string,
        public modificaAbilitata: boolean,
        public cancellazioneAbilitata: boolean
    ) { }
}