export class EsitoOperazioneDTO {
    constructor(
        public esito: boolean,
        public messaggi: Array<string>,
        public id: number
    ) { }
}