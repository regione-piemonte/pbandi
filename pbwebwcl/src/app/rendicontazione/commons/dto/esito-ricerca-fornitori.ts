import { Fornitore } from "./fornitore";

export class EsitoRicercaFornitori {
    constructor(
        public esito: boolean,
        public messaggio: string,
        public fornitori: Array<Fornitore>
    ) { }
}