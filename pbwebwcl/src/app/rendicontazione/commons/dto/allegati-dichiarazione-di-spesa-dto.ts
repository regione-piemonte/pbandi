import { DatiIntegrazioneDsDTO } from "./dati-integrazione-ds-dto";
import { DocumentoAllegatoDTO } from "./documento-allegato-dto";

export class AllegatiDichiarazioneDiSpesaDTO {
    constructor(
        public allegati: Array<DocumentoAllegatoDTO>,
        public integrazioni: Array<DatiIntegrazioneDsDTO>
    ) { }
}