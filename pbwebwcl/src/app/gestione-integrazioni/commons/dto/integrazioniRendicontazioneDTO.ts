
export class IntegrazioniRendicontazioneDTO {
    constructor(
        public allegatiInseriti: boolean,
        public nDichiarazioneSpesa: number,
        public idIntegrazioneSpesa: number,
        public dataRichiesta: string,
        public dataNotifica: string,
        public dataScadenza: string,
        public dataInvio: string,
        public idStatoRichiesta: string,
        public statoRichiesta: string,
        public longStatoRichiesta: string,
        public invia: string,
        public dtRichiesta: string,
        public ggApprovati: string,
        public ggRichiesti: string,
        public idStatoProroga: string,
        public motivazione: string,
        public richiediIntegrazionione: boolean,
        public statoProroga: string,
    ) { }
}