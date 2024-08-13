/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/



export class UtenteRicercatoDTO {
    constructor(

        public seralVersionUID: number,

        public idPersonaFisica: number,
        public idSoggetto: number,
        public nome: string,
        public cognome: string,
        public codiceFiscale: string,
        public idTipoAnagrafica: number,
        public descTipoAnagrafica: string,
        public descBreveTipoAnagrafica: string,
        public codiceFiscaleBeneficiario: string,
        public dataInserimento: Date,
        public numeroProgettiAbilitati: number,
        public dataInserimentoDal: Date,
        public dataInserimentoAl: Date,
        public idRelazioneBeneficiario: number,
        public idUtente: number,
        public progettiValidi: number,
        public progettiNonValidi: number,
        public email: string

    ) { }


    public static createEmptyUtenteRicercato(): UtenteRicercatoDTO {
        var filter = new UtenteRicercatoDTO(undefined, undefined, undefined, undefined, undefined, undefined, undefined,
            undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined)
        return filter

    }

    public toString(): string {
        return "class = UtenteRicercatoDTO, nome = " + this.nome + ", cognome = " + this.cognome + ", des = " + this.descTipoAnagrafica;
    }

}

