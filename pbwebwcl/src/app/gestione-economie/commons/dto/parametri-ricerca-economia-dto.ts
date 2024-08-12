export class ParametriRicercaEconomieDTO {
    constructor(
        public filtro: string,
        public beneficiario: string,
        public progetto: string,
        public beneficiarioRicevente: string,
        public progettoRicevente: string
    ) { }
}