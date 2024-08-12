export class infoQuietanzeVO {

    constructor(
        public importoPagamento?: number,
        public idPagamento?: number,
        public idModalitaPagamento?: number,
        public descModalitaPagamento?: string,
        public dataPagamento?: Date,
        public dataInvio ?: number,
    ) { }
}