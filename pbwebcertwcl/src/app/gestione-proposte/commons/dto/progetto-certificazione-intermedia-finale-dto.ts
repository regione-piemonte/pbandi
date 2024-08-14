/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProgettoCertificazioneIntermediaFinaleDTO {
    constructor(
        public idDettPropCertAnnual: number,
        public idDettPropostaCertif: number,
        public idPropostaCertificaz: number,
        public importoRevocheRilevCum: number,
        public importoRecuperiCum: number,
        public importoSoppressioniCum: number,
        public importoErogazioniCum: number,
        public importoPagamValidCum: number,
        public importoCertifNettoAnnual: number,
        public dataAgg: Date,
        public idUtenteAgg: number,
        public certificatoNettoCumulato: number,
        public certificatoLordoCumulato: number,
        public colonnaC: number,
        public descBreveStatoPropostaCert: string,
        public descStatoPropostaCertif: string,
        public descProposta: string,
        public beneficiario: string,
        public codiceProgetto: string,
        public nomeBandoLinea: string,
        public idStatoPropostaCertif: number,
        public idLineaDiIntervento: number,
        public idProgetto: number,
        public asse: string,
        public diffCna: number,
        public diffRev: number,
        public diffRec: number,
        public diffSoppr: number,
        public importoCertifNettoAnnualModificabile?: string, //solo frontend
        public colonnaCModificabile?: string //solo frontend
    ) { }
}