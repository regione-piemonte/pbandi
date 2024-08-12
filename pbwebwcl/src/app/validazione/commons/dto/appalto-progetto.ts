export class AppaltoProgetto {
    constructor(
        public idProgetto: number,
        public idAppalto: number,
        public associato: boolean,
        public descrizione: string
    ) { }
}