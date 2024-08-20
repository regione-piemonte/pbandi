/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class PropostaRevocaVO {

  //proposta revoca
  public idPropostaRevoca: string;
  public numeroPropostaRevoca: string;

  //beneficiario
  public idSoggetto: string;
  public codiceFiscaleSoggetto: string;
  public idBeneficiario: string;
  public denominazioneBeneficiario: string;

  //bando (?)
  public idDomanda: string;
  public progrBandoLineaIntervento: string;
  public nomeBandoLinea: string;

  //progetto
  public titoloProgetto: string;
  public codiceVisualizzatoProgetto: string;

  //causale blocco
  public idCausaleBlocco: string;
  public descCausaleBlocco: string;

  //stato revoca
  public idStatoRevoca: string;
  public descStatoRevoca: string;

  //date
  public dataPropostaRevoca: Date;
  public dataStatoRevoca: Date;

}
