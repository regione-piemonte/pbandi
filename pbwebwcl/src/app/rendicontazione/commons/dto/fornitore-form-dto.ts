import { DocumentoAllegatoDTO } from "./documento-allegato-dto";

export class FornitoreFormDTO {
    public cfAutoGenerato: string;
    public codiceFiscaleFornitore: string;
    public cognomeFornitore: string;
    public denominazioneFornitore: string;
    public idAttivitaAteco: number;
    public idFormaGiuridica: number;
    public idTipoSoggetto: number;
    public idFornitore: number;
    public idNazione: number;
    public nomeFornitore: string;
    public partitaIvaFornitore: string;
    public altroCodice: string;
    public codUniIpa: string;
    public flagPubblicoPrivato: number;
    public documentiAllegati: Array<DocumentoAllegatoDTO>;
}