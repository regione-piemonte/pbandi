export class EsitoOperazioneInviaDichiarazioneDTO {
    constructor(
        public esito: boolean,
        public msg: string,
        public nomeFileDichiarazioneSpesa: string,
        public idDichiarazioneSpesa: number,
        public idDocumentoIndex: number
    ) { }
}