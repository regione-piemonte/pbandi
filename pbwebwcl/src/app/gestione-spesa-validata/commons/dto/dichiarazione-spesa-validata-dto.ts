export class DichiarazioneSpesaValidataDTO {
    constructor(
        public dtChiusuraValidazione: string,
        public descTipoDichiarazioneSpesa: string,
        public idDichiarazioneSpesa: number,
        public idProgetto: number,
        public idUtenteIns: number,
        public noteChiusuraValidazione: string,
        public periodoAl: string,
        public periodoDal: string,
        public periodo: string,
        public idDocIndexDichiarazioneDiSpesa: number,
        public idDocIndexReportDettaglio: number
    ) { }
}