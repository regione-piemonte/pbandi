export class EsitoAssociaFilesDTO {
    constructor(
        public elencoIdDocIndexFilesAssociati: Array<number>,
        public elencoIdDocIndexFilesNonAssociati: Array<number>
    ) { }
}