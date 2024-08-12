export class CercaDocumentiSpesaValidataRequest {
    constructor(
        public idProgetto: number,
        public idDichiarazioneDiSpesa: number,
        public idVoceDiSpesa: number,
        public idTipoDocumentoDiSpesa: number,
        public task: string,
        public numeroDocumentoDiSpesa: string,
        public dataDocumentoDiSpesa: Date,
        public idTipoFornitore: number,
        public codiceFiscaleFornitore: string,
        public partitaIvaFornitore: string,
        public denominazioneFornitore: string,
        public cognomeFornitore: string,
        public nomeFornitore: string,
        public rettificato: boolean,                    // true = selezionato check "Documenti rettificati":string, false = selezionato il check "Tutti i documenti".      
        public idContoEconomico: number                 // Ancora non è chiaro quando valorizzarlo:string, forse Spesa Validata viene chiamata da Conto Economico.
    ) { }
}