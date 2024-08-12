export class CercaChecklistRequestDTO {
    constructor(
        public dichiarazioneSpesa: string,
        public dataControllo: string,
        public soggettoControllo: string,
        public stati: string[],
        public tipologia: string,
        public rilevazione: string,
        public versione: string,
        public idProgetto: string
    ) { }
}