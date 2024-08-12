export class BeneficiarioDTO {
    constructor(
        public cognome: string,
        public nome: string,
        public codiceFiscale: string,
        public idSoggetto: number,
        public descrizione: string,
        public dataInizioValidita: Date
    ) { }
}