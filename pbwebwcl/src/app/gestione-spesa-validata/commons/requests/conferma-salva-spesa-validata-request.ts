import { RigaValidazioneItemDTO } from "src/app/validazione/commons/dto/riga-validazione-item-dto";

export class ConfermaSalvaSpesaValidataRequest {
    constructor(
        public idDichiarazioneDiSpesa: number,
        public idDocumentoDiSpesa: number,
        public idProgetto: number,
        public pagamentiAssociati: Array<RigaValidazioneItemDTO>,
        public noteValidazione: string
    ) { }
}