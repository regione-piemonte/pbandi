export class AssociaFilesRequest {
    constructor(
        public elencoIdDocumentoIndex: Array<number>,
        public idTarget: number,
        public idProgetto: number
    ) { }
}