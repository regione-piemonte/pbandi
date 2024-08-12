import { RigaValidazioneItemDTO } from "../dto/riga-validazione-item-dto";

export class ValidaParzialmenteDocumentoRequest {
    constructor(
        public idDichiarazioneDiSpesa: number,
        public idDocumentoDiSpesa: number,
        public idProgetto: number,
        public idBandoLinea: number,
        public noteValidazione: string,

        // Campi di RigaValidazioneItemDTO che devono essere popolati: IdRigoContoEconomico, ImportoValidatoVoceDiSpesa.
        // Gli altri possono anche restare nulli.
        public righeValidazioneItem: Array<RigaValidazioneItemDTO>
    ) { }
}