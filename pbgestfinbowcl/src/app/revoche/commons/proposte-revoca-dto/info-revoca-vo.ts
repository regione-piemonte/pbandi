/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class InfoRevocaVO {

  public idGestioneRevoca: number;     //new
  public idProgetto: number;     //new
  public idSoggetto: number;     //new
  public idDomanda: number;     //new

  //public beneficiario: string;
  public denominazioneBeneficiario: string;     //new
  public idBeneficiario: number;          //new
  public codiceFiscaleSoggetto: string;
  public nomeBandoLinea: string;
  public titoloProgetto: string;
  public numeroRevoca: number;
  public causaRevoca: string;
  public descCausaleBlocco: string;         //new
  public descCategAnagrafica: string;
  public descStatoRevoca: string;
  public dtStatoRevoca: Date;
  public dtGestione: Date;
  public codiceVisualizzato: string;      //new
  public descBreveStatoRevoca: string;      //new

  public nomeIstruttore: string;
  public cognomeIstruttore: string;

  //public importoAmmessoFinanziamento: number;
  //public impQuotaSoggFinanziatore: number;
  //public importoErogazione: number;
  //public importo: number;
  //public impDaRevocare: number;
  //public flagRevocaTotale: string;
  //public note: string;
  //public nome: string;
  //public cognome: string;
  //public idUtenteInserimento: string;

  }
