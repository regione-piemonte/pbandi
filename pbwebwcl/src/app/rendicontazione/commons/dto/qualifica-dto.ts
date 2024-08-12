export class QualificaDTO {
    constructor(
        public comodoIdTabella: number,
        public costoOrario: number,
        public descQualifica: string,
        public dtFineValidita: Date,
        public idFornitore: number,
        public idQualifica: number,
        public noteQualifica: string,
        public progrFornitoreQualifica: number,
        public idTable?: number, //solo frontend, per identificare la qualifica non ancora salvata su db in gestione fornitori
        public modificata?: boolean //solo frontend, per identificare la qualifica modificata
    ) { }
}