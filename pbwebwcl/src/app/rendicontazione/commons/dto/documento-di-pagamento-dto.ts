import { DocumentoAllegatoDTO } from "./documento-allegato-dto";

export class DocumentoDiPagamentoDTO {
    constructor(
        public causalePagamento: string,
        public dtPagamento: string,
        public descBreveModalitaPagamento: string,
        public descrizioneModalitaPagamento: string,
        public destinatarioPagamento: string,
        public documentiAllegati: Array<DocumentoAllegatoDTO>,
        public estremiPagamento: string,
        public id: number,
        public idCausalePagamento: string,
        public idModalitaPagamento: number,
        public importoPagamento: number,
        public importoDaAssociare: number,
        public importoRendicontabilePagato: number,
        public importoResiduoUtilizzabile: number,
        public importoResiduoUtilizzabileVuoto: boolean,
        public idSoggetto: number,
        public linkAllegaFileVisible: boolean,
        public pagamentoModificabile: boolean,
        public pagamentoEliminabile: boolean,
        public rifPagamento: string,
        public flagPagamento:string,
        public statoPagamento: string
    ) { }
}