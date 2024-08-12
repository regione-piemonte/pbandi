import { RigaValidazioneItemDTO } from "src/app/validazione/commons/dto/riga-validazione-item-dto";

export class SalvaSpesaValidataRequest {
    constructor(
        public pagamentiAssociati: Array<RigaValidazioneItemDTO>,
        public idDichiarazioneDiSpesa: number,
        public idDocumentoDiSpesa: number,
        public idProgetto: number,
    ) { }
}