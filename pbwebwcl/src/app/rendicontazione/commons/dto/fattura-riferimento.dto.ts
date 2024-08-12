export class FatturaRiferimentoDTO {
    constructor(
        public idDocumentoDiSpesa: number,
        public descrizione: string,
        public importoRendicontabile: number,
        public importoTotaleDocumentoIvato: number,
        public importoTotaleQuietanzato: number
    ) { }
}