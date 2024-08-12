import { DocumentoAllegatoDTO } from "src/app/rendicontazione/commons/dto/documento-allegato-dto";

export class RigaNotaDiCreditoItemDTO {
    constructor(
        public rigaNotaDiCredito: boolean,
        public numeroDocumento: string,
        public dataDocumento: string,
        public importoDocumento: number,
        public descrizioneVoceDiSpesa: string,
        public importoVoceDiSpesa: number,
        public statoDocumento: string,
        public id: number,
        public documentoAllegato: Array<DocumentoAllegatoDTO>
    ) { }
}