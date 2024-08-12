export class TipoOperazioneDTO {
    constructor(
        public idTipoOperazione: number,
        public descBreveTipoOperazione: string,
        public descTipoOperazione: string,
        public codIgrueT0: string,
        public dtInizioValidita: Date,
        public dtFineValidita: Date
    ) { }
}