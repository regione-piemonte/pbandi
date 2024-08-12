import { DocumentoAllegatoDTO } from "./documento-allegato-dto";

export class DatiIntegrazioneDsDTO {
    constructor(
        public idIntegrazioneSpesa: number,
        public dataRichiesta: string,
        public dataInvio: string,
        public descrizione: string,
        public allegati: Array<DocumentoAllegatoDTO>
    ) { }
}