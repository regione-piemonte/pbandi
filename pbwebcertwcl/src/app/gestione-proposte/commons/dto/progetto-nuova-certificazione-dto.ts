/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProgettoNuovaCertificazioneDTO {
    constructor(
        public idStatoProgetto: number,
        public importoCertificazioneNetto: number,
        public titoloProgetto: string,
        public idProgetto: number,
        public nomeBandoLinea: string,
        public codiceProgetto: string,
        public denominazioneBeneficiario: string,
        public idDettPropostaCertif: number,
        public impCertifNettoPremodifica: number,
        public idLineaDiIntervento: number,
        public nota: string,
        public importoModificabile?: string //solo frontend per formattazione
    ) { }
}