export class FornitoreComboDTO {
    constructor(
        public idFornitore: number,
        public descrizione: string,
        public cfValido: boolean,
        public formaGiuridicaValida: boolean
    ) { }
}