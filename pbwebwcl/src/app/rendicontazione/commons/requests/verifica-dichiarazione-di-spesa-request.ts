export class VerificaDichiarazioneDiSpesaRequest {
    constructor(
        public idProgetto: number,
        public idBandoLinea: number,
        public dataLimiteDocumentiRendicontabili: Date,
        public idSoggettoBeneficiario: number,
        public codiceTipoDichiarazioneDiSpesa: string			// I (intermedia), F (finale), IN (integrativa).
    ) { }
}