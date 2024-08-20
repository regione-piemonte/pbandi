/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

export class DettaglioFinanziamentoErogato {

  /* TENETE AGGIORNATO L'OGGETTO 'DettaglioBeneficiarioCreditiVO' SE FATE UNA MODIFICA FE, MANNAGGIA A CRI*** */

  public idBando: string;
  public idProgetto: string;
  public idSoggetto: string;
  public progrSoggettoProgetto: string;

  public idModalitaAgevolazioneOrig: number;
  public descBreveModalitaAgevolazioneOrig: string;
  public descModalitaAgevolazioneOrig: string;
  public idModalitaAgevolazioneRif: number;
  public descBreveModalitaAgevolazioneRif: string;
  public descModalitaAgevolazioneRif: string;
  public dispDescAgevolazione: string; // Per visualizzare solo Contributo, Finanziamento o Garanzia
  
  public codProgetto: string;
  public titoloBando: string;
  public numErogazioni: number; // Usato nella logica per Stato agevolazione (dispStatoAgevolazione) ed Estratto conto
  public dispStatoAgevolazione: string;
  public idUltimaCausale: number; // Serve?
  public importoAmmesso: number;
  public totaleErogato: number;
  public totaleErogatoFin: number;
  public totaleErogatoBanca: number;
  public isCreditoResiduoLoading: boolean; // Usato per lo spinner
  public creditoResiduo: number; // Da amm.vo cont.
  public isAgevolazioneLoading: boolean // Usato per lo spinner
  public agevolazione: number; // Da amm.vo cont.
  public idStatoCessione: number;
  public statoCessione: string;
  public revocaAmministrativa: string;
  public dataRevocaBancaria: Date;
  public libMandatoBanca: string;
  public descStatoCredito: string;
  public listaRevoche: string;
  
}
