/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class IndirizziRapprLegaleCspDTO {
    constructor(
        public civico: string,
        public descIndirizzo: string,
        public cap: string,
        public email: string,
        public telefono: string,
        public fax: string,
        public idNazioneRes: number,
        public descNazioneRes: string,
        public idRegioneRes: number,
        public descRegioneRes: string,
        public idProvinciaRes: number,
        public descProvinciaRes: string,
        public idComuneRes: number,
        public descComuneRes: string,
        public idNazioneNascita: number,
        public descNazioneNascita: string,
        public idRegioneNascita: number,
        public descRegioneNascita: string,
        public idProvinciaNascita: number,
        public descProvinciaNascita: string,
        public idComuneNascita: number,
        public descComuneNascita: string,
        public idComuneEsteroNascita: number,
        public descComuneEsteroNascita: string,
        public idComuneEsteroRes: number,
        public descComuneEsteroRes: string
    ) { }
}