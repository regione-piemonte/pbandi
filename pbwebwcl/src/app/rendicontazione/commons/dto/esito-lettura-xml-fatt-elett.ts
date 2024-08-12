import { DatiFatturaElettronica } from "./dati-fattura-elettronica";
import { FornitoreFormDTO } from "./fornitore-form-dto";

export class EsitoLetturaXmlFattElett {
    constructor(
        public datiFatturaElettronica: DatiFatturaElettronica,
        public fornitoreDb: FornitoreFormDTO,
        public esito: string
    ) { }
}