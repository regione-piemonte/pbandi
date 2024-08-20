/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class AnagraficaBeneFisico {
    constructor(
        //Persona Fisica
        //Dati anagrafici:
        public cognome: any,
        public nome: any,
        public tipoSoggetto: any,
        public codiceFiscale: any,
        public dataDiNascita: any,
        public idComuneDiNascita: any,
        public idProvinciaDiNascita: any,
        public idRegioneDiNascita: any,
        public idNazioneDiNascita: any,
        public comuneDiNascita: any,
        public provinciaDiNascita: any,
        public regioneDiNascita: any,
        public nazioneDiNascita: any,
        //RESIDENZA:
        public indirizzoPF: any,
        public idComunePF: any,
        public idProvinciaPF: any,
        public idRegionePF: any,
        public idNazionePF: any,
        public comunePF: any,
        public provinciaPF: any,
        public capPF: any,
        public regionePF: any,
        public nazionePF: any
    ) { }
}
