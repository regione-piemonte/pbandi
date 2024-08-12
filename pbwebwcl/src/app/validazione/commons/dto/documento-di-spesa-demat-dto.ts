import { DocumentoAllegatoDTO } from "src/app/rendicontazione/commons/dto/documento-allegato-dto";
import { AffidamentoValidazione } from "./affidamento-validazione";
import { DocumentoAllegatoPagamentoDTO } from "./documento-allegato-pagamento-dto";
import { DocumentoDiSpesaDTO } from "./documento-di-spesa-dto";
import { RigaNotaDiCreditoItemDTO } from "./riga-nota-di-credito-item-dto";
import { RigaValidazioneItemDTO } from "./riga-validazione-item-dto";

export class DocumentoDiSpesaDematDTO extends DocumentoDiSpesaDTO {
    public documentoAllegato: Array<DocumentoAllegatoDTO>;
    public documentoAllegatoPagamento: Array<DocumentoAllegatoPagamentoDTO>;
    public documentoAllegatoNotaDiCredito: Array<DocumentoAllegatoDTO>;
    public noteDiCredito: Array<RigaNotaDiCreditoItemDTO>;
    public rigaValidazioneItem: Array<RigaValidazioneItemDTO>;
    public file: DocumentoAllegatoDTO;
    public messaggiErrore: string;
    public documentoAllegatoFornitore: Array<DocumentoAllegatoDTO>;
    public documentoAllegatoQualificaFornitore: Array<DocumentoAllegatoDTO>;
    public importoValidatoSuVoceDiSpesa: number;
    public descrizioneStatoValidazione: string;
    public affidamento: AffidamentoValidazione
}