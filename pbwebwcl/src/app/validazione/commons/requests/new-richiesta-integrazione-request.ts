export class NewRichiediIntegrazioneRequest {
    constructor(
        public idDichiarazioneDiSpesa: number,
        public numGiorniScadenza: number,
        public idStatoRichiesta: number
    ) { }
}