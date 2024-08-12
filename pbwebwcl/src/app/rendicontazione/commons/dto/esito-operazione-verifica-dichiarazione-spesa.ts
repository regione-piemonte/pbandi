import { DocumentoDiSpesa } from "./documento-di-spesa";

export class EsitoOperazioneVerificaDichiarazioneSpesa {
    constructor(
        public esito: boolean,
        public documentiDiSpesa: Array<DocumentoDiSpesa>,
        public messaggi: Array<string>
    ) { }
}