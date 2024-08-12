
import { FileDTO } from "./file-dto";
import { EsitoChecklistAffidamentoDTO } from "./esito-checklist-affidamento-dto";

export class ChecklistHtmlDTO {
    constructor(

        public allegati: Array<File>, // Array<FileDTO>,
        public codStatoTipoDocIndex:string,
        public bytesVerbale: string ,  // Array<byte>
        public contentHtml: string,
        public esitoDefinitivo: EsitoChecklistAffidamentoDTO,
        public esitoIntermedio: EsitoChecklistAffidamentoDTO,
        public idChecklist: number,
        public idDocumentoIndex: number,
        public idProgetto: number,
        public nomeFile: string,
        public soggettoControllore: string
    ) { }
}