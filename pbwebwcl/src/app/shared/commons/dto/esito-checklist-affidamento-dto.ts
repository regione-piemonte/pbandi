export class EsitoChecklistAffidamentoDTO {
    constructor(
        public esito: string,
        public fase: number,
        public flagRettifica: string,
        public idChecklist: number,
        public idEsito: number,
        public note: string
    ) { }
}