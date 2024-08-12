export class QualificaFormDTO {
    constructor(
        public progrFornitoreQualifica: number,
        public idFornitore: number,
        public idQualifica: number,
        public costoOrario: number,
        public noteQualifica: string
    ) { }
}