export class DocumentiSpesaSospesiVO {
    constructor(

        public idDocuSpesa: number,
        public importoRendicon: string,
        public idUtenteIns: string,
        public idUtenteAgg: string,
        public noteValidazione: string,
        public task: string,
        public idStatoDocumentoSpesa: string,
        public idStatoDocumentoSpesaValid:string,
        public tipoInvio: string,
        public idAppalto: string
        
        ){}
}