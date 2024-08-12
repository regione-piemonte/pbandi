import { MessaggioDTO } from "./messaggio-dto";

export class EsitoOperazioneEconomia {
    constructor(
        public esito: boolean,
        public idEconomia: number,
        public msgs: MessaggioDTO
    ) { }
}