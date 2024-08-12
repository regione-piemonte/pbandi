export class EsitoSalvaSpesaValidataDTO {
    constructor(
        public esito: boolean,
        public messaggi: Array<string>,
        public id: number,
        public chiedereConfermaPerProseguire: boolean
    ) { }
}