import { DecodificaDTO } from "src/app/rendicontazione/commons/dto/decodifica-dto";

export class InizializzaValidazioneDTO {
  constructor(
    public codiceVisualizzatoProgetto: string,
    public taskVisibile: boolean,
    public elencoTask: Array<string>,		            // popolato solo se taskVisibile = true.
    public tipiDocumentoSpesa: Array<DecodificaDTO>,
    public tipiFornitore: Array<DecodificaDTO>,
    public statiDocumentoSpesa: Array<DecodificaDTO>,
    public documentoSpesaModificabile: boolean,
    public idDocumentoIndex: number,                    // Per le DS finali è l'id doc index della CFP, altrimenti è l'id doc index della DS.
    public nomeFile: string,
    public flagFirmaCartacea: string,                   // S = Documento cartaceo, N\null = Digitale.
    public idComunicazFineProg: number,
    public dtComunicazFineProg: string,
    public statoDichiarazione: string,                  // firmata \ non firmata.
    public protocollo: string,
    public esisteRichiestaIntegrazioneAperta: boolean,
    public idEnteCompetenza: number,
    public descBreveEnte: string,
    public validazioneMassivaAbilitata: boolean,
    public richiestaIntegrazioneAbilitata: boolean,
    public chiusuraValidazioneAbilitata: boolean,
    public dichiarazioneDiSpesaFinale: boolean,
    public regolaInvioDigitale: string,                 // S \ N

    // +Green.
    // Per le DS finali è l'id doc index della CFP, altrimenti è l'id doc index della DS.
    public idComunicazFineProgPiuGreen: number,
    public dtComunicazFineProgPiuGreen: string,
    public idDocumentoIndexPiuGreen: number,
    public nomeFilePiuGreen: string,
    public statoDichiarazionePiuGreen: string,
    public flagFirmaCartaceaPiuGreen: string,
    public protocolloPiuGreen: string,
    // +Green fine.

    public dateInvio: Array<String>,
  ) { }
}
