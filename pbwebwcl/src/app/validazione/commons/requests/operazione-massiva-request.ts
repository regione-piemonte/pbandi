export class OperazioneMassivaRequest {
    constructor(
        public operazione: string,                               // "VALIDARE", "INVALIDARE", "RESPINGERE".
        public idDocumentiDiSpesaDaElaborare: Array<number>,     // Elenco degli id dei documenti di spesa da elaborare.
        public idDichiarazioneDiSpesa: number
    ) { }
}