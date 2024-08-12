export class EsitoOperazioneDTO {
    constructor(
        public esito: boolean,
        public descBreveStato: string,
        public codiceMessaggio: string
    ) { }
}