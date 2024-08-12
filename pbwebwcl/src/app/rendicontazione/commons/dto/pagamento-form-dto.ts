export class PagamentoFormDTO {
    constructor(
        public idPagamento: number,
        public idModalitaPagamento: number,
        public dtPagamento: Date,
        public importoPagamento: number,
        public idCausalePagamento: number,
        public flagPagamento: string,
        public rifPagamento: string
    ) { }
}