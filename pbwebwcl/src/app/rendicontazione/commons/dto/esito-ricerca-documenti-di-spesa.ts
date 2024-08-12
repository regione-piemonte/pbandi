import { ElencoDocumentiSpesaItem } from "./elenco-documenti-spesa-item";

export class EsitoRicercaDocumentiDiSpesa {
    constructor(
        public esito: boolean,
        public messaggio: string,
        public documenti: Array<ElencoDocumentiSpesaItem>
    ) { }
}