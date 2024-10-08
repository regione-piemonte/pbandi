/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbservizit.pbandisrv.business.neoflux;

public interface Notification {
	
  //PBANDI_C_RUOLO_DI_PROCESSO.CODICE
  String BENEFICIARIO="Beneficiario";
  String ISTRUTTORE="Istruttore";
  
  //PBANDI_D_METADATA_NOTIFICA.NOME_PARAMETRO
  String DATA_CHIUSURA_PROGETTO = "DATA_CHIUSURA_PROGETTO";
  String DATA_CHIUSURA_RIMODULAZIONE = "DATA_CHIUSURA_RIMODULAZIONE";
  String DATA_CHIUSURA_VALIDAZIONE = "DATA_CHIUSURA_VALIDAZIONE";
  String DATA_DICHIARAZIONE_DI_SPESA = "DATA_DICHIARAZIONE_DI_SPESA";
  String DATA_INVIO_PROPOSTA = "DATA_INVIO_PROPOSTA";
  String DATA_INVIO_RINUNCIA = "DATA_INVIO_RINUNCIA";
  String DATA_RETTIFICA_SPESA_VALIDATA = "DATA_RETTIFICA_SPESA_VALIDATA";
  String DATA_REVOCA = "DATA_REVOCA";
  String DESC_DICHIARAZIONE_DI_SPESA = "DESC_DICHIARAZIONE_DI_SPESA";
  String NUM_DICHIARAZIONE_DI_SPESA ="NUM_DICHIARAZIONE_DI_SPESA";
  // Jira PBANDI-2773
  String OGGETTO_AFFIDAMENTO = "OGGETTO_AFFIDAMENTO";
  String IMP_AGGIUDICATO_AFFIDAMENTO = "IMP_AGGIUDICATO_AFFIDAMENTO";
  String DATA_RESPINGI_AFFIDAMENTO = "DATA_RESPINGI_AFFIDAMENTO";
  String NOTE_RESPINGI_AFFIDAMENTO = "NOTE_RESPINGI_AFFIDAMENTO";
  String DATA_RIC_INTEGRAZ_AFFIDAMENTO = "DATA_RIC_INTEGRAZ_AFFIDAMENTO";
  String NOTE_RIC_INTEGRAZ_AFFIDAMENTO = "NOTE_RIC_INTEGRAZ_AFFIDAMENTO";
  // Notifica richiesta integrazione DS da istruttore a beneficiario.
  String DATA_RICHIESTA_INTEGRAZ_DIC = "DATA_RICHIESTA_INTEGRAZ_DIC";
  String NOTE_RICHIESTA_INTEGRAZ_DIC = "NOTE_RICHIESTA_INTEGRAZ_DIC";
  // Notifica invio integrazione DS da beneficiario a istruttore.
  String DATA_INTEGRAZ_DIC = "DATA_INTEGRAZ_DIC";
  
  // PBANDI_D_TEMPLATE_NOTIFICA.DESCR_BREVE_TEMPLATE_NOTIFICA
  String NOTIFICA_CHIUSURA_DEL_PROGETTO ="NotificaChiusuraDelProgetto";
  String NOTIFICA_CHIUSURA_DEL_PROGETTO_PER_RINUNCIA ="NotificaChiusuraDelProgettoPerRinuncia";
  String NOTIFICA_CHIUSURA_DUFFICIO_DEL_PROGETTO ="NotificaChiusuraDUfficioDelProgetto";
  String NOTIFICA_COMUNICAZIONE_RINUNCIA ="NotificaComunicazioneRinuncia";
  String NOTIFICA_DI_REVOCA ="NotificaDiRevoca";
  String NOTIFICA_EROGAZIONE  ="NotificaErogazione";
  String NOTIFICA_GESTIONE_SPESA_VALIDATA ="NotificaGestioneSpesaValidata";
  String NOTIFICA_PROPOSTA_RIMODULAZIONE ="NotificaPropostaRimodulazione";
  String NOTIFICA_RICHIESTA_EROGAZIONE_ACCONTO  ="NotificaRichiestaErogazioneAcconto";
  String NOTIFICA_RICHIESTA_EROGAZIONE_PRIMO_ANTICIPO ="NotificaRichiestaErogazionePrimoAnticipo";
  String NOTIFICA_RICHIESTA_EROGAZIONE_SALDO ="NotificaRichiestaErogazioneSaldo";
  String NOTIFICA_RICHIESTA_EROGAZIONE_ULTERIORE_ACCONTO ="NotificaRichiestaErogazioneUlterioreAcconto";
  String NOTIFICA_RIMODULAZIONE ="NotificaRimodulazione";
  String NOTIFICA_VALIDAZIONE ="NotificaValidazione";
  String NOTIFICA_VALIDAZIONE_DI_SPESA_FINALE ="NotificaValidazioneDiSpesaFinale";
  // Jira PBANDI-2773
  String NOTIFICA_RESPINGI_AFFIDAMENTO ="NotificaRespingiAffidamento";
  String NOTIFICA_RICHIESTA_INTEGRAZIONE_AFFIDAMENTO ="NotificaRichiestaIntegrazioneAffidamento";
  // Notifica richiesta integrazione DS da istruttore a beneficiario.
  String NOTIFICA_RICHIESTA_INTEGRAZIONE_DICHIARAZIONE ="NotificaRichiestaIntegrazioneDichiarazione";
  // Notifica invio integrazione DS da beneficiario a istruttore.
  String NOTIFICA_INTEGRAZIONE_DICHIARAZIONE ="NotificaIntegrazioneDichiarazione";

  // Notifica variazione/blocco Anagrafica
  String NOTIFICA_VARIAZIONE_ANAGRAFICA = "NotificaVariazioneAnagrafica";
  String DATA_INSERIMENTO_BLOCCO = "DATA_INSERIMENTO_BLOCCO";
  String DATA_INSERIMENTO_SBLOCCO = "DATA_INSERIMENTO_SBLOCCO";
  String DES_CAUSALE_BLOCCO = "DES_CAUSALE_BLOCCO";

}
