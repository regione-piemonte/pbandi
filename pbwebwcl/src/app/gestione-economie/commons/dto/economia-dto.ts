import { ResponseCodeMessage } from "./response-code-message";

export class EconomiaDTO {
    public idEconomia: number;
    public idProgettoCedente: number;
    public idProgettoRicevente: number;
    public codiceVisualizzatoCedente: string;
    public importoCeduto: number;
    public noteCessione: string;
    public dataCessione: Date;
    public codiceVisualizzatoRicevente: string;
    public dataRicezione: Date;
    public noteRicezione: string;
    public dataUtilizzo: Date;
    public dataModifica: Date;
    public dataInserimento: Date;
    public idBeneficiarioCedente: number;
    public idBeneficiarioRicevente: number;
    public idBandoLineaCedente: number;
    public idBandoLineaRicevente: number;
    public denominazioneBeneficiarioCedente: string;
    public denominazioneBeneficiarioRicevente: string;
    public responseCodeMessage: ResponseCodeMessage
}