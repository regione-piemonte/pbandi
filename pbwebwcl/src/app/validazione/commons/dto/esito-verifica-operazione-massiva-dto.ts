export class EsitoVerificaOperazioneMassivaDTO {
    constructor(
        public esito: boolean,
        public messaggio: string,
        public messaggioImportoAmmissibileSuperato: string,
        public idDocumenti: Array<number>

    ) { }
}