export class FiltroRicercaDocumentoSpesa {
    constructor(
        public partner: string,
        public documentiDiSpesa: string,
        public idTipologia: number,
        public idCategoria: number,
        public idStato: number,
        public idFornitore: number,
        public idVoceDiSpesa: number,
        public numero: string,
        public data: Date,
        public dataA: Date,
        public task: string
    ) { }
}