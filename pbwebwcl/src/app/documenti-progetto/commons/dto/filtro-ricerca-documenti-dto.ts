export class FiltroRicercaDocumentiDTO {
    constructor(
        public idSoggettoBeneficiario: number,
        public idProgetto: number,
        public idTipoDocumentoIndex: number,
        public dataDal: Date,
        public dataAl: Date,
        public docInFirma: boolean,
        public docInviati: boolean,
        public idSoggetto: number,
        public codiceRuolo: string,
        public idBando: number
    ) { }
}