
export class IntegrazioniRevocaDTO {

    constructor(
        public nRevoca: number,
        public dataRichiesta: string,
        public dataNotifica: string,
        public dataScadenza: string,
        public dataInvio: string,
        public idStatoRichiesta: string,
        public statoRichiesta: string,
        public longStatoRichiesta: string,
        public invia: string
    ) { }
}