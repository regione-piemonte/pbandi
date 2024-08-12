import { DocumentoDiPagamentoDTO } from "./documento-di-pagamento-dto";

export class QuietanzeAssociateDTO {
    constructor(
        public array: Array<DocumentoDiPagamentoDTO>,
        public ggQuietanza: number 

    ) { }
}