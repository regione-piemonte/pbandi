/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class SedeDTO {
    constructor(
        public idProgetto: number,
        public idSoggettoBeneficiario: number,
        public idSede: number,
        public progrSoggettoProgettoSede: number,
        public idTipoSede: number,
        public descBreveTipoSede: string,
        public descTipoSede: string,
        public partitaIva: string,
        public idNazione: number,
        public descNazione: string,
        public idRegione: number,
        public descRegione: string,
        public idProvincia: number,
        public descProvincia: string,
        public idComune: number,
        public descComune: string,
        public idIndirizzo: number,
        public descIndirizzo: string,
        public idRecapiti: number,
        public email: string,
        public fax: string,
        public telefono: string,
        public cap: string,
        public codIstatComune: string,
        public civico: number,
        public flagSedeAmministrativa: string
    ) { }
}