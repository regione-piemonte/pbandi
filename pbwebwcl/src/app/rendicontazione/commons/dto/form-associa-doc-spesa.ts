export class FormAssociaDocSpesa {
    constructor(
        public idDocumentoDiSpesa: number,
        public task: string,
        public importoRendicontabile: number,
        public massimoRendicontabile: number,
        public isDocumentoTotalmenteRendicontato: boolean,
        public descBreveTipoDocumento: string
    ) { }
}