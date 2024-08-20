/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class ProvvedimentoRevocaVO {

  //Provvedimento
  public idProvvedimentoRevoca?: number;
  public numeroProvvedimentoRevoca?: number;
  //Beneficiario
  public idSoggetto?: number;
  public ndg?: string;
  public codiceFiscaleSoggetto?: string;
  public idBeneficiario?: number;
  public denominazioneBeneficiario?: string;
  //Domanda
  public idDomanda?: number;
  //Bando
  public progrBandoLineaIntervento?: number;
  public nomeBandoLinea?: string;
  //Progetto
  public idProgetto?: number;
  public titoloProgetto?: string;
  public codiceVisualizzatoProgetto?: string;
  //Numero di protocollo
  public numeroProtocollo?: number;
  //Data avvio del provvedimento di revoca
  public dataAvvioProvvedimentoRevoca?: Date;
  //Determina, Data Determina e estremi
  public flagDetermina?: boolean;
  public dtDetermina?: Date;
  public estremi?: string;
  //Recupero, Modalità del recupero
  public flagOrdineRecupero?: boolean;
  public idMancatoRecupero?: number;
  public descMancatoRecupero?: string;
  //Motivo del provvedimento
  public idMotivoRevoca?: number;
  public descMotivoRevoca?: string;
  //Causa del provvedimento di revoca
  public idCausaleBlocco?: number;
  public descCausaleBlocco?: string;
  //Autorità controllante
  public idAutoritaControllante?: number;
  public descAutoritaControllante?: string;
  //Stato del provvedimento di revoca
  public idStatoRevoca?: number;
  public descStatoRevoca?: string;
  //Data dello stato provvedimento di revoca
  public dataStatoRevoca?: Date;
  //Attività del provvedimento di revoca
  public idAttivitaRevoca?: number;
  public descAttivitaRevoca?: string;
  //Data attività del provvedimento di revoca
  public dataAttivitaRevoca?: Date;
  //Data notifica
  public dataNotifica?: Date;
  //Giorni di scadenza
  public giorniScadenza?: number;
  //Data scadenza
  public dataScadenza?: Date;

  //Verifica presenza disimpegni sul contributo, finanziamento e garanzia
  public flagContribRevoca?: boolean;
  public flagContribMinorSpese?: boolean;
  public flagContribDecurtaz?: boolean;

  public flagFinanzRevoca?: boolean;
  public flagFinanzMinorSpese?: boolean;
  public flagFinanzDecurtaz?: boolean;

  public flagGaranziaRevoca?: boolean;
  public flagGaranziaMinorSpese?: boolean;
  public flagGaranziaDecurtaz?: boolean;


  //ModAgev
  public modAgevContrib?: string;
  public modAgevFinanz?: string;
  public modAgevGaranz?: string;
  public idModAgevContrib?: number;
  public idModAgevFinanz?: number;
  public idModAgevGaranz?: number;
  public idModAgevContribRif?: number;
  public idModAgevFinanzRif?: number;
  public idModAgevGaranzRif?: number;

  //Importo ammesso
  public importoAmmessoContributo?: number;
  public importoAmmessoFinanziamento?: number;
  public importoAmmessoGaranzia?: number;
  //Importo concesso
  public importoConcessoContributo?: number;
  public importoConcessoFinanziamento?: number;
  public importoConcessoGaranzia?: number;
  //Importo già revocato (somma degli importi già revocati per il progetto)
  public importoRevocatoContributo?: number;
  public importoRevocatoFinanziamento?: number;
  public importoRevocatoGaranzia?: number;
  //Importo erogato TODO amm.cont
  public importoErogatoContributo?: number;
  public importoErogatoFinanziamento?: number;
  public importoErogatoGaranzia?: number;
  //Importo recuperato TODO amm.cont
  public importoRecuperatoContributo?: number;
  public importoRecuperatoFinanziamento?: number;
  public importoRecuperatoGaranzia?: number;
  //Importo rimborsato TODO amm.cont
  public importoRimborsatoFinanziamento?: number;

  //- Importo rimborsato FINANZIAMENTO
  public impConcAlNettoRevocatoContributo?: number;
  public impConcAlNettoRevocatoFinanziamento?: number;
  public impConcAlNettoRevocatoGaranzia?: number;
  public impErogAlNettoRecuERimbContributo?: number;
  public impErogAlNettoRecuERimbFinanziamento?: number;
  public impErogAlNettoRecuERimbGaranzia?: number;

  //Recupero importi e interessi revoca per contributo, finanziamento e garanzia (FORM)
  public impContribRevocaNoRecu?: number;
  public impContribRevocaRecu?: number;
  public impContribInteressi?: number;

  public impFinanzRevocaNoRecu?: number;
  public impFinanzRevocaRecu?: number;
  public impFinanzInteressi?: number;
  public impFinanzPreRecu?: number;

  public impGaranziaRevocaNoRecu?: number;
  public impGaranziaRevocaRecu?: number;
  public impGaranziaInteressi?: number;
  public impGaranzPreRecu?: number;


  //Note
  public note?: string;
  //Istruttore che ha avviato l’istruttoria
  public idSoggettoIstruttore?: number;
  public denominazioneIstruttore?: string;
  public numeroCovar: number; 
}
