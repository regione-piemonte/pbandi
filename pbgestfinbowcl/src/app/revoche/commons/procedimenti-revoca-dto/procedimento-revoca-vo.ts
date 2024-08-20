/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProcedimentoRevocaVO {

  //[Numero procedimento di revoca]
  public idProcedimentoRevoca: string;
  public numeroProcedimentoRevoca: string;

  //[Beneficiario]
  public idSoggetto: string;
  public codiceFiscaleSoggetto: string;
  public idBeneficiario: string; //id_ente_giuridico / id_persona_fisica
  public denominazioneBeneficiario: string;

  //[Bando]
  public idDomanda: string;
  public progrBandoLineaIntervento: string;
  public nomeBandoLinea: string;

  //[Progetto]
  public idProgetto: string;
  public titoloProgetto: string;
  public codiceVisualizzatoProgetto: string;

  //[Causa del procedimento di revoca]
  public idCausaleBlocco: string;
  public descCausaleBlocco: string;

  //[Autorità controllante]
  public idAutoritaControllante: string;
  public descAutoritaControllante: string;

  //[Stato del procedimento di revoca]
  public idStatoRevoca: string;
  public descStatoRevoca: string;

  //[Data dello stato del procedimento di revoca]
  public dataStatoRevoca: Date;

  //[Attività]
  public idAttivitaRevoca: string;
  public descAttivitaRevoca: string;

  //[Data attività]
  public dataAttivitaRevoca: Date;

  //[Data notifica]
  public dataNotifica: Date;

  //[scadenza]
  public giorniScadenza: string;
  public dataScadenza: Date;

  //[Proroga]
  public proroga: boolean;

  //[Note]
  public note: string;

  //[Istruttore che ha avviato l’istruttoria]
  public idSoggettoIstruttore: string;
  public denominazioneIstruttore: string;

  //[Numero di protocollo]
  public numeroProtocollo: string;

  //[Data avvio procedimento di revoca]
  public dataAvvioProcedimento: Date;



  //IMPORTI PROCEDIMENTO REVOCA
  public modalitaAgevolazioneContributo: string;
  public modalitaAgevolazioneFinanziamento: string;
  public modalitaAgevolazioneGaranzia: string;

  //[Importo ammesso]
  public importoAmmessoContributo: number;
  public importoAmmessoFinanziamento: number;
  public importoAmmessoGaranzia: number;
  //[Importo concesso] per le modalità di agevolazioni previste         --> SE IMPORTO CONCESSO NULL NON MOSTRO RIGA
  public importoConcessoContributo: number;
  public importoConcessoFinanziamento: number;
  public importoConcessoGaranzia: number;
  //[Importo già revocato] per le modalità di agevolazioni previste
  public importoRevocatoContributo: number;
  public importoRevocatoFinanziamento: number;
  public importoRevocatoGaranzia: number;
  //[Importo da revocare] per le modalità di agevolazioni previste
  public importoDaRevocareContributo: number;
  public importoDaRevocareFinanziamento: number;
  public importoDaRevocareGaranzia: number;

  //Informazioni ricavate da Amministrativo contabile
  //[Importo erogato]
  public importoErogatoContributo: number;
  public importoErogatoFinanziamento: number;
  public importoErogatoGaranzia: number;
  //[Importo recuperato]
  public importoRecuperatoContributo: number;
  public importoRecuperatoFinanziamento: number;
  public importoRecuperatoGaranzia: number;
  //[Importo rimborsato]
  public importoRimborsatoFinanziamento: number;

  //Informazioni calcolate
  //[Importo concesso al netto del revocato]
  public importoConcessoNeRevocatoContributo: number;
  public importoConcessoNeRevocatoFinanziamento: number;
  public importoConcessoNeRevocatoGaranzia: number;
  //[Importo erogato al netto del recuperato e del rimborsato]
  public importoErogatoNeRecuperatoRimborsatoContributo: number;
  public importoErogatoNeRecuperatoRimborsatoFinanziamento: number;
  public importoErogatoNeRecuperatoRimborsatoGaranzia: number;


  //parametri nuovi
  public idDichiarazioneSpesa?: string;
  public impDaErogareContributo?: string;
  public causaleErogazioneContributo?: string;
  public impDaErogareFinanziamento?: string;
  public causaleErogazioneFinanziamento?: string;
  public impIres?: string;

}



