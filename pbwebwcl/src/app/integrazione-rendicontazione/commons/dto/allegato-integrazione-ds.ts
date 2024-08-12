export class AllegatoIntegrazioneDs {
    constructor(
        public idDocumentoIndex: string,
        public nomeFile: string,
        public flagEntita: string,
        public checked?: boolean    //solo frontend
    ) { }
}