import { AllegatoIntegrazioneDs } from "./allegato-integrazione-ds";

export class RigaTabRichiesteIntegrazioniDs {
    constructor(
        public idIntegrazioneSpesa: number,
        public idDichiarazioneSpesa: string,
        public descrizione: string,
        public dataRichiesta: string,
        public dataInvio: string,
        public dataDichiarazione: string,
        public dataNumDichiarazione: string,
        public allegati: Array<AllegatoIntegrazioneDs>

    ) { }
}