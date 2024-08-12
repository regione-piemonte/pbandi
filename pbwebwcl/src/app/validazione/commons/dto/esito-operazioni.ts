export class EsitoOperazioni {
    constructor(
        public esito: boolean,
        public msg: string,
        public params: Array<string>
    ) { }
}