export class VerificaOperazioneMassivaRequest {
    constructor(
        public operazione: string,                                  // "VALIDARE", "INVALIDARE", "RESPINGERE".
        public tutti: boolean,                                      // true = si vogliono elaborare tutti i documenti; false = si vogliono elaborare solo i documenti in idDocumentiDiSpesaDaElaborare.         
        public idDocumentiDiSpesaDaElaborare: Array<number>,        // Elenco degli id dei documenti di spesa da elaborare:number, ignorato se tutti = true.
        public idDichiarazioneDiSpesa: number,
        public idBandoLinea: number
    ) { }
}