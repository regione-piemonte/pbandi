import { DocumentoAllegatoDTO } from "src/app/rendicontazione/commons/dto/documento-allegato-dto";
export class DocumentoAllegatoPagamentoDTO {
    constructor(
        public descrizioneModalitaPagamento: string,
        public dtPagamento: string,
        public importoPagamento: number,
        public nomeFile: string,
        public id: number,
        public idPagamento: number,
        public sizeFile: number,
        public idIntegrazioneSpesa: string,
        public documentoAllegatoIntegrazioni :Array<Array<DocumentoAllegatoDTO>>,
        public documentoAllegatoGenerico : Array<DocumentoAllegatoDTO>,
    ) { }
}