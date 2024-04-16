/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SedeLegaleBeneficiarioCspDTO {
    constructor(
        public civico: string,
        public partitaIva: string,
        public descIndirizzo: string,
        public cap: string,
        public idNazione: number,
        public descNazione: string,
        public idRegione: number,
        public descRegione: string,
        public idProvincia: number,
        public descProvincia: string,
        public idComune: number,
        public descComune: string,
        public idComuneEstero: number,
        public descComuneEstero: string,
        public email: string,
        public pec: string,
        public telefono: string,
        public fax: string
    ) { }
}