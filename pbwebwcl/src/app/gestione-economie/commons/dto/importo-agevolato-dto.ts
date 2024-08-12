export class ImportoAgevolatoDTO {
    constructor(
        public descrizione: string,
        public importo: number,
        public importoAlNettoRevoche: number
    ) { }
}