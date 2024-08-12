export class EsitoLockCheckListDTO {
    constructor(
        public esito: boolean,
        public descBreveStato: string,
        public codiceMessaggio: string
    ) { }
}