export class ProseguiChiudiValidazioneRequest {
    constructor(
        public noteChiusura: string,
        public dsIntegrativaConsentita: boolean,
        public idAppaltiSelezionati: Array<number>,
        public idProgetto: number,
        public idDichiarazioneDiSpesa: number
    ) { }
}