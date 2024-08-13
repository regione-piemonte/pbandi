/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

package it.csi.pbandi.pbwebbo.business.intf;

public interface ErrorConstants {

	/**
	 * Generici
	 */
	String ERRORE_GENERICO = "E.errore_generico";
	String ERRORE_CAMPO_NON_VALORIZZATO = "E000";
	String ERRORE_CAMPO_OBBLIGATORIO = "E.N005";
	String ERRORE_SERVER = "E.E002"; // Non e' stato possibile portare a termine
										// l'operazione.
	String ERRORE_CODICE_FISCALE_FORMALMENTE_SCORRETTO = "E.W027";// W027
																	// Attenzione!
																	// Il codice
																	// fiscale
																	// non ï¿½
																	// formalmente
																	// corretto.
	String ERRORE_CODICE_FISCALE_PIVA_FORMALMENTE_SCORRETTO = "E.W071";
	String ERRORE_CODICE_FISCALE_FORNITORE_GIA_PRESENTE = "E.W018";
	String ERRORE_CODICE_FISCALE_TROPPO_LUNGO = "E.W032";
	String ERRORE_OCCORRENZA_GIA_PRESENTE = "E.E050";
	String ERRORE_VALORE_NON_AMMESSO = "E.N074";
	String ERRORE_SELEZIONARE_ALMENO_UN_ELEMENTO = "E.E152";
	String VALORE_TRA_0_E_100_NON_CORRETTO = "E.error.percentuale.valore";
	String VALORE_NON_NUMERICO = "E.error.importo.format";
	String VALORE_FUORI_RANGE = "E.error.numeric.range";
	String FORMATO_NUMERO_LUNGHEZZA_ERRATO = "E.error.importo.length";
	String ERROR_DATA_SUCCESSIVA_ODIERNA = "E.error.data.successiva";
	String ERROR_DATA_PRECEDENTE_ODIERNA = "E.error.data.precedente";
	String ERROR_ALTERNATIVE = "E.error.alternative";
	String ERROR_FILE_SIZE = "E.error.file.size";
	String ERROR_FILE_TYPE = "E.error.file.type";

	/**
	 * Errori di accesso
	 */
	String ERRORE_UTENTE_NON_AUTORIZZATO = "E.E007";

	/**
	 * Gestione Fornitori
	 */
	String ERRORE_FORNITORE_GIA_PRESENTE = "W.W014";// Attenzione! Il fornitore
													// ï¿½ giï¿½ stato inserito
													// a sistema. Si desidera
													// riattivarlo?.
	String ERRORE_FORNITORE_GIA_USATO = "E.E026";// Errore! esiste giï¿½ un
													// fornitore con gli stessi
													// dati.
	String ERRORE_DOCUMENTI_DI_SPESA_IN_USO_DAL_FORNITORE = "E.E027";
	String ERRORE_FORNITORI_CARICAMENTO_TIPOLOGIE_FORNITORE = "E.E262";
	String ERRORE_FORNITORI_TROVAI_TROPPI_RISULTATI = "E.E263";

	/**
	 * Gestione Pagamenti
	 */
	String ERRORE_VALIDAZIONE_DATA_EFFETTIVO_PAGAMENTO = "E.E011";
	String ERRORE_VALIDA_SOMME_TOTALI_PAGAMENTI = "E.E019";
	String ERRORE_VALIDA_COERENZA_IMPORTO = "E.E021";
	String ERRORE_VALIDA_SOMME_PARZIALI_VOCIDISPESA = "E.E022";
	String ERRORE_TIPOLOGIA_DOC_DI_SPESA_PER_IL_PAGAMENTO = "E.E024";
	String ERRORE_VALIDAZIONE_DATA_VALUTA_PAGAMENTO = "E.E029";
	String ERRORE_DATA_PAGAMENTO_SUCCESSIVA_ALLA_DATA_ODIERNA = "E.E031";
	String ERRORE_DOCUMENTO_DI_SPESA_SENZA_VOCI_DI_SPESA = "E.E032";
	String ERRORE_STATO_VALIDAZIONE_PAGAMENTO = "E.M8";
	String ERRORE_ASSOCIAZIONE_PAGAMENTO_CON_PIU_DOCDISPESA = "E.M10";
	String ERRORE_CAMPI_OBBLIGATORI = "E.W008";
	String ERRORE_DATA_PAGAMENTO_INFERIORE_ALLA_DATA_DOMANDA = "E.E040";
	String ERRORE_PAGAMENTO_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO = "E.E041";
	String ERRORE_DATA_PAGAMENTO_PREC_DATA_SPESA_AND_SUCC_DATA_DOMANDA = "E.E1000";
	String ERRORE_DATA_PAGAMENTO_PREC_DATA_COSTITUZIONE_AZIENDA = "E.W078";
	String ERRORE_DATA_PAGAMENTO_INFERIORE_A_DATA_DOCUMENTO = "E.E068";

	/**
	 * Gestione Doc Di Spesa
	 */
	String ERRORE_DOCUMENTO_DI_SPESA_ASSOCIATO_A_PAGAMENTO = "E.E025";// E025
																		// Errore:
																		// il
																		// documento
																		// di
																		// spesa
																		// non
																		// puï¿½
																		// essere
																		// cancellato
																		// perche'
																		// e'
																		// associato
																		// a uno
																		// o
																		// piï¿½
																		// pagamenti.
	String ERRORE_DOCUMENTO_DI_SPESA_RIFERITO_A_NOTA_DI_CREDITO = "E.E028";// E028
																			// Errore!
																			// Il
																			// documento
																			// di
																			// spesa
																			// non
																			// puï¿½
																			// essere
																			// cancellato
																			// perche'
																			// e'
																			// riferito
																			// da
																			// una
																			// nota
																			// di
																			// credito.
	String ERRORE_DOCUMENTO_DI_SPESA_FONITORE_NON_PRESENTE = "W.W019";// W019
																		// Attenzione!
																		// Il
																		// fornitore
																		// indicato
																		// non
																		// e'
																		// presente
																		// nell'anagrafica
	String ERRORE_DOCUMENTO_DI_SPESA_GIA_INSERITO = "E.E014";// Errore: il
																// documento di
																// spesa e' gia'
																// stato
																// inserito
																// nella banca
																// dati.
	String ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_DOMANDA = "E.W026";// Attenzione!
																		// La
																		// data
																		// del
																		// documento
																		// di
																		// spesa
																		// deve
																		// essere
																		// successivo
																		// alla
																		// data
																		// di
																		// presentazione
																		// della
																		// domanda.
	String ERRORE_DOCUMENTO_DI_SPESA_IVA_A_COSTO_SUPERIORE = "E.W024";// Attenzione!
																		// L'importo
																		// dell'iva
																		// a
																		// costo
																		// non
																		// deve
																		// essere
																		// superiore
																		// all'imposta.
	String ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_SUPERIORE = "E.W023";// Attenzione!
																					// L'importo
																					// rendicontabile
																					// non
																					// deve
																					// essere
																					// superiore
																					// all'imponibile
																					// piï¿½
																					// l'iva
																					// a
																					// costo.
	String ERRORE_DOCUMENTO_DI_SPESA_CEDOLINO_O_AUTODICHIARAZIONE_SOCI_IMPORTO_RENDICONTABILE_SUPERIORE = "E.W035";// Attenzione!
																													// L'importo
																													// rendicontabile
																													// non
																													// deve
																													// essere
																													// superiore
																													// all'imponibile.
	String ERRORE_DOCUMENTO_DI_SPESA_DATI_NON_CONGRUI = "E.E023";// Errore:
																	// integrare
																	// i dati
																	// del
																	// fornitore
																	// con
																	// l'indicazione
																	// del monte
																	// ore e del
																	// costo
																	// della
																	// risorsa.
	String ERRORE_DOCUMENTO_DI_SPESA_NON_MODIFICABILE = "E.E036";// E036 Errore:
																	// non ï¿½
																	// possibile
																	// modificare
																	// un
																	// documento
																	// di spesa
																	// in stato
																	// <stato_documento_di_spesa>.
	String ERRORE_DOCUMENTO_DI_SPESA_NON_ELIMINABILE = "E.E037";// Errore: non
																// ï¿½ possibile
																// eliminare un
																// documento di
																// spesa in
																// stato
																// inserito
	String ERRORE_DOCUMENTO_DI_SPESA_PAGAMENTI_NON_ELIMINABILI = "E.E268";// E028
																			// ERRORE:
																			// il
																			// documento
																			// di
																			// spesa
																			// non
																			// puï¿½
																			// essere
																			// cancellato
																			// perchï¿½
																			// ï¿½
																			// associato
																			// a
																			// uno
																			// o
																			// piï¿½
																			// pagamenti
																			// in
																			// stato
																			// diverso
																			// da
																			// INSERITO
																			// o
																			// RESPINTO.
	String ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_SUPERIORE_TOTALE = "E.E272";
	String ERRORE_DOCUMENTO_DI_SPESA_NON_ELIMINABILE_NON_ASSOCIATO_AL_PROGETTO = "E.E278";

	String ERRORE_DOCUMENTO_DI_SPESA_DATA_SUPERIORE_OGGI = "E.W030";// Attenzione!
																	// La data
																	// del
																	// documento
																	// di spesa
																	// non puo'
																	// essere
																	// maggiore
																	// alla data
																	// odierna.
	String WARNING_DOCUMENTO_DI_SPESA_GIA_INSERITO = "E.W021";// Attenzione!
																// Esiste gia'
																// un documento
																// di spesa con
																// gli stessi
																// dati.
	String WARNING_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_MAGGIORE_TOTALE = "E.W020";// Attenzione!
																						// Il
																						// totale
																						// del
																						// rendicontabile
																						// associato
																						// ai
																						// progetti
																						// e'
																						// maggiore
																						// all'importo
																						// massimo
																						// rendicontabile
																						// del
																						// documento
																						// di
																						// spesa.
	String WARNING_DOCUMENTO_DI_SPESA_TOTALE_QUOTE_PARTE_MAGGIORE_RENDICONTABILE = "E.W022";// Attenzione!
																							// La
																							// somma
																							// del
																							// rendicontato
																							// e'
																							// maggiore
																							// del
																							// rendicontabile.
	String WARNING_DOCUMENTO_DI_SPESA_TOTALE_PAGAMENTI_MAGGIORE_TOTALE_DOCUMENTO = "E.W025";// Attenzione!
																							// La
																							// somma
																							// del
																							// quietanzato
																							// e'
																							// maggiore
																							// del
																							// totale
																							// del
																							// documento
																							// di
																							// spesa
																							// al
																							// netto
																							// delle
																							// note
																							// di
																							// credito.
	String WARNING_DOCUMENTO_DI_SPESA_CEDOLINO_IMPORTO_RENDICONTABILE_SUPERIORE = "W.C009";// Attenzione!
																							// Hai
																							// indicato
																							// un
																							// rendicontabile
																							// del
																							// progetto
																							// superiore
																							// al
																							// totale
																							// del
																							// cedolino.
																							// Sei
																							// sicuro
																							// di
																							// voler
																							// proseguire?

	String ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_TOTALE_QUOTE_PARTE_MAGGIORE_RENDICONTABILE = "W.VAL002";// Rendicontabile
																											// del
																											// documento
																											// non
																											// comletamente
																											// associato
																											// alle
																											// voci
																											// di
																											// spesa.
	String ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_TOTALE_ORE_MAGGIORE_MONTE_ORE = "W.VAL006";// Il
																							// numero
																							// complessivo
																							// delle
																							// ore
																							// lavorate
																							// supera
																							// il
																							// tempo
																							// produttivo
																							// del
																							// fornitore.
	String ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_IMPORTO_QUIETANZATO_MAGGIORE_TOTALE_DOCUMENTO = "W.VAL004"; // Importo
																												// quietanzato
																												// maggiore
																												// dell'importo
																												// del
																												// documento
																												// di
																												// spesa
																												// al
																												// netto
																												// di
																												// eventuali
																												// note
																												// di
																												// credito.
	String ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_DOCUMENTO_NON_TOTALMENTE_QUIETANZATO = "W.VAL003"; // Spesa
																									// per
																									// il
																									// documento
																									// non
																									// totalmente
																									// quietanzata.
	String ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_PAGAMENT_ASSOCIATI_NON_RIPARTITI = "W.VAL005"; // I
																								// pagamenti
																								// associati
																								// al
																								// documento
																								// per
																								// il
																								// progetto
																								// corrente
																								// non
																								// sono
																								// sufficientemente
																								// ripartiti
																								// sulle
																								// voci
																								// di
																								// spesa
																								// associate.
	String ERRORE_VALIDAZIONE_DOCUMENTO_DI_SPESA_TIMEOUT = "E.VAL015"; // Attenzione!
																		// La
																		// validazione
																		// non
																		// \u00E8
																		// stata
																		// chiusa
																		// correttamente,
																		// si
																		// prega
																		// di
																		// contattare
																		// il
																		// supporto
																		// applicativo.
	String ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_NOTE_DI_CREDITO_SUPERIORE = "E.W033";// Attenzione!
																					// La/e
																					// nota/e
																					// di
																					// credito
																					// supera/no
																					// il
																					// totale
																					// del
																					// documento
																					// di
																					// spesa
																					// di
																					// riferimento.

	String WARNING_DOCUMENTO_DI_SPESA_DATA_DOCUMENTO_INFERIORE_DATA_COSTITUZIONE = "E.W070";

	String ERRORE_DOCUMENTO_DI_SPESA_IMPORTO_RENDICONTABILE_NOTA_CREDITO = "E.E144";

	String ERRORE_DOCUMENTO_DI_SPESA_SELEZIONARE_QUALIFICA_FORNITORE = "E.E267";
	String ERRORE_DOCUMENTO_DI_SPESA_NON_MODIFICABILE_SEMPLIFICAZIONE = "E.E270";
	String ERRORE_DOCUMENTO_DI_SPESA_NOTA_DI_CREDITO_NON_ASSOCIABILE_PER_PAGAMENTI_PRESENTI = "E.E273";
	String ERRORE_DOCUMENTI_DI_SPESA_TOTALE_DOCUMENTO_MINORE_TOTALE_RENDICONTABILI = "E.E274";
	String ERRORE_DOCUMENTI_DI_SPESA_TOTALE_FATTURA_MINORE_TOTALE_RENDICONTABILI = "E.E275";
	String ERRORE_DOCUMENTO_DI_SPESA_DA_COMPLETARE_NON_MODIFICABILE = "E.E279";
	String WARNING_DOCUMENTO_DI_SPESA_SENZA_ALLEGATO = "W.W090";
	String WARNING_DOCUMENTO_DI_SPESA_CON_PAGAMENTI_SENZA_ALLEGATI = "W.W091";
	String WARNING_DOCUMENTO_DI_SPESA_SENZA_NUMERO_PROTOCOLLO = "W.W092";

	/**
	 * Gestione Voci di spesa
	 */
	String ERRORE_VOCE_DI_SPESA_GIA_ASSOCIATA = "W.V005";
	String ERRORE_VOCE_DI_SPESA_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO = "W.V003";
	String ERRORE_VOCE_DI_SPESA_IMPORTO_MAGGIORE_RESIDUO_VOCE = "W.V004";
	String ERRORE_VOCE_DI_SPESA_ASSOCIATA_PAGAMENTI = "W.V008";
	String ERRORE_VOCE_DI_SPESA_IMPORTO_MINORE_PAGAMENTI = "W.V006";
	String ERRORE_SEMPL_VOCE_GIA_ASSOCIATA = "E.E264";
	String ERRORE_SEMPL_VOCE_IMPORTO_MAGGIORE_RESIDUO_DOCUMENTO = "E.E265";
	String ERRORE_SEMPL_VOCE_IMPORTO_MINORE_ASSOCIATO_PAGAMENTI = "E.E266";
	String ERRORE_SEMPL_IMPOSSIBILE_OPERARE_VOCI_ASSOCIATE = "E.E271";

	String ERRORE_INCONGRUITA_DEI_DATI = "ERRORE_INCONGRUITA_DEI_DATI";

	String ERRORE_SERVIZIO_IRIDE = "E.IRIDE001";
	String ERRORE_GENERICO_IRIDE = "E.IRIDE002";

	String ERRORE_ATTIVITA_FLUX_NON_DISPONIBILE = "E.FLUX001";

	/**
	 * Dichiarazione di spesa
	 */
	String ERRORE_NESSUN_DOCUMENTO_PER_DICHIARAZIONE_TROVATO = "E.E034";
	String ERRORE_NESSUN_PAGAMENTO_PER_DICHIARAZIONE_TROVATO = "E.E035";
	String ERRORE_DICHIARAZIONE_NON_PRESENTE = "E.E042";

	String ERRORE_CHECK_RESPINTO_E_SOSPESO = "E.E038";

	String ERRORE_ESISTONO_PAGAMENTI_SOSPESI = "W.M3";
	String ERRORE_ESISTONO_PAGAMENTI_DICHIARATI = "W.M5";
	String ERRORE_ESISTONO_PAGAMENTI_SOSPESI_E_DICHIARATI = "W.M6";

	/**
	 * Errori aggiornamento pagamenti e voci di spesa
	 */

	String ERRORE_PAGAMENTO_SOSPESO_E_RESPINTO = "W.VAL010";
	String ERRORE_PAGAMENTO_VALORE_MANCANTE = "E.VAL007";
	String ERRORE_PAGAMENTO_VALORE_ERRATO = "E.VAL008";
	String ERRORE_CHECKLIST_TRASCORSO_TIMEOUT = "W.VAL011";
	String ERRORE_CHECKLIST_NON_MODIFICABILE_PREGRESSO = "W.M2c4";
	String ERRORE_CHECKLIST_NON_MODIFICABILE_INVIATA = "W.M2c5";

	/*
	 * Errori Richiesta Erogazione
	 */

	String ERRORE_EROGAZIONE_SPESA_SOSTENUTA_ZERO = "W.E068";
	String ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO = "W.E069";
	String ERRORE_EROGAZIONE_IMPORTO_FIDEIUSSIONE_MINORE_ANTICIPO = "W.E070";
	String ERRORE_EROGAZIONE_SPESA_SOSTENTUTA_MINORE_SPESA_SOGLIA_BANDO_SENZA_FIDEIUSSIONE = "W.E071";
	String ERRORE_BANDO_NON_GESTISCE_RICHIESTA_EROGAZIONE = "W.E072";
	String ERRORE_EROGAZIONE_IMPORTO_AGEVOLATO_NULLO = "E.E078";
	String ERRORE_IMPOSSIBILE_CREARE_PDF = "E.E030";
	String ERRORE_EROGAZIONE_MODALITA_DI_AGEVOLAZIONI_NON_PRESENTI = "E.E149";

	/*
	 * Errori Erogazione
	 */
	String ERRORE_SOMMA_EROGAZIONI_PER_MOD_AGEVOLAZ_SUPERIORE_IMPORTO_AGEVOLATO_CONTO_ECONOMICO = "E.E054";
	String ERRORE_SOMMA_QUOTE_EROGATE_SUPERIORE_A_IMPORTO_AGEVOLATO = "E.E058";
	String EROGAZIONE_NON_ELIMINABILE = "E.E080";

	String ERRORE_NON_ESISTONO_DOCUMENTI_IN_VALIDAZIONE = "W.M13";
	String ERRORE_IMPORTO_AMMISSIBILE_SUPERATO = "W.MX2";
	String ERRORE_VALIDAZIONE_PRESENTI_DOCUMENTI_IN_VALIDAZIONE = "E.E276";

	/*
	 * Errori Certificazione
	 */
	String ERRORE_CERTIFICAZIONE_PROPOSTA_GIA_PRESENTE = "E.E045";// ATTENZIONE!
																	// E' gia'
																	// presente
																	// una
																	// richiesta
																	// di
																	// elaborazione...

	/*
	 * Errori BackOffice
	 */
	String ERRORE_VOCE_DI_SPESA_GIA_PRESENTE_SU_RIGO_CONTO_ECONOMICO = "E.E060";// ATTENZIONE!
																				// La
																				// voce
																				// di
																				// spesa
																				// appartiene
																				// al
																				// conto
																				// economico
																				// di
																				// un
																				// progetto
																				// associato
																				// al
																				// bando,
																				// impossibile
																				// disassociarla
																				// dal
																				// bando
	String ERRORE_MACRO_VOCE_GIA_SU_CONTO_ECONOMICO = "E.E075";// ATTENZIONE! La
																// voce di spesa
																// appartiene al
																// conto
																// economico di
																// un progetto
																// associato al
																// bando e non
																// ha sottovoci,
																// impossibile
																// creare
																// l'associazione
																// con la
																// sottovoce.
	String ERRORE_MACRO_VOCE_CON_MICRO_VOCI = "E.E076";// ATTENZIONE! La voce di
														// spesa ï¿½ associata
														// al bando con una o
														// piï¿½ sottovoci, per
														// eliminare
														// l'associazione ï¿½
														// necessario eliminare
														// prima l'associazione
														// con le sottovoci.
	String ERRORE_REGOLA_ANCORA_ASSOCIATA_A_TIPO_ANAGRAFICA = "E.E077";// ATTENZIONE!
																		// La
																		// regola
																		// ï¿½
																		// associata
																		// a uno
																		// o
																		// piï¿½
																		// tipi
																		// di
																		// anagrafica,
																		// per
																		// eliminare
																		// l'associazione
																		// con
																		// il
																		// bando-linea
																		// ï¿½
																		// necessario
																		// eliminare
																		// prima
																		// l'associazione
																		// tra
																		// la
																		// regola
																		// ed i
																		// tipi
																		// di
																		// anagrafica.
	String ERRORE_UTENTE_GIA_ASSOCIATO_BENEFICIARIO_PROGETTO = "E.E083";// ATTENZIONE!
																		// L'utente
																		// risulta
																		// giï¿½
																		// associato
																		// al
																		// beneficiario
																		// e al
																		// progetto,
																		// impossibile
																		// salvare.
	String ERRORE_UTENTE_GIA_ASSOCIATO_RUOLO = "E.E084";// L'utente risulta
														// giï¿½ associato a
														// questo ruolo.
	String ERRORE_BENEFICIARIO_PROGETTO_INCOERENTI = "E.E086";// ATTENZIONE! I
																// dati relativi
																// a
																// beneficiario
																// e progetto
																// sono
																// incoerenti,
																// impossibile
																// salvare.
	String ERRORE_PROGETTO_SU_ENTE_NON_COLLEGATO_SOGG_OPERANTE = "E.E0861";// Attenzione!
																			// Il
																			// progetto
																			// indicato
																			// \u00E8
																			// relativo
																			// ad
																			// un
																			// ente
																			// che
																			// non
																			// \u00E8
																			// tra
																			// quelli
																			// di
																			// competenza
																			// dell'operatore.

	/*
	 * Fideiussione
	 */
	String FIDEIUSSIONE_NON_ELIMINABILE = "E.E061";
	String CREDENZIALI_EMESSE_DA_UN_DELEGANTE = "E.E062";

	/*
	 * Gestione spesa validata
	 */
	String ERRORE_RETTIFICA_IMPORTO_TOTALE_RENDICONTABILE = "W.E087";
	String ERRORE_RETTIFICA_IMPORTO_VALIDATO_NEGATIVO = "W.E088";
	String ERRORE_RETTIFICA_IMPORTO_VALIDATO_MAGGIORE_IMPORTO_ASSOCIATO = "W.E088";
	String ERRORE_RETTIFICA_IMPORTO_QUOTA_PARTE_SUPERATO = "W.E089";
	String WARNING_RETTIFICA_IMPORTO_EROGAZIONI_SUPERATO = "W.W054";
	String ERRORE_RETTIFICA_NESSUN_DATO_MODIFICATO = "W.N059";

	String ERRORE_RETTIFICA_LOCK_CHECKLIST = "E089C";

	String ERRORE_RETTIFICA_NESSUNA_CKECKLIST_TROVATA = "W.W089";

	/*
	 * Gestione Revoche
	 */

	String ERROR_REVOCA_IMPORTO_REVOCA_NEGATIVO = "E.E090";
	String ERROR_REVOCA_TOTALE_REVOCATO_SUPERIORE_EROGAZIONI = "E.E091";
	String ERROR_REVOCA_RIDURRE_IMPORTO_REVOCA = "E.E092";
	String ERROR_REVOCA_RECUPERATO_MAGGIORE_REVOCATO = "E.E093";
	String ERROR_REVOCA_IMPORTO_GIA_EROGATO_NON_PRESENTE = "E.E094";
	String ERROR_REVOCA_NESSUNA_EROGAZIONE_PRESENTE = "";
	String ERROR_REVOCA_PRESENTE_CERTIFICAZIONE_REVOCA = "E.E123";
	String ERROR_REVOCA_CANCELLAZIONE_TOTALE_REVOCATO_INFERIORE_TOTALE_RECUPERATO = "E.E124";

	String ERROR_DISIMPEGNO_IMPORTO_REVOCA_NEGATIVO = "E.D.E090";
	String ERROR_DISIMPEGNO_TOTALE_REVOCATO_SUPERIORE_EROGAZIONI = "E.D.E091";
	String ERROR_DISIMPEGNO_RIDURRE_IMPORTO_REVOCA = "E.D.E092";
	String ERROR_DISIMPEGNO_RECUPERATO_MAGGIORE_REVOCATO = "E.D.E093";
	String ERROR_DISIMPEGNO_CANCELLAZIONE_TOTALE_REVOCATO_INFERIORE_TOTALE_RECUPERATO = "E.D.E124";

	String WARN_REVOCA_CONFERMA_SALVATAGGIO = "W.W055";
	String WARN_REOVCA_PRESENTE_PROPOSTA_CERTIFIAZIONE_PROGETTO = "W.W061";
	String WARN_REVOCA_REVOCA_CERTIFICATA = "W.W062";
	String WARN_REVOCA_CONFERMA_CANCELLAZIONE = "W.W067";

	/*
	 * Gestione Recuperi
	 */
	String ERRORE_RECUPERO_NESSUNA_REVOCA = "E.E095";
	String ERRORE_RECUPERO_TOTALE_MAGGIORE_REVOCHE = "E.E096";
	String ERRORE_RECUPERO_TOTALE_PROGETTO_MAGGIORE_REVOCHE = "E.E097";
	String ERRORE_RECUPERO_IMPORTO_RECUPERO_NEGATIVO = "E.E098";
	String ERRORE_RECUPERO_PRESENTE_CERTIFICAZIONE_RECUPERO = "E.E126";
	String ERRORE_RECUPERO_TIPOLOGIA_NON_PERTINENTE = "E.E143";

	String WARN_RECUPERO_CONFERMA_SALVATAGGIO = "W.W055";
	String WARN_RECUPERO_DATA_SUPERIORE_DATA_ODIERNA = "E.W063";
	String WARN_RECUPERO_TOTALE_RECUPERATO_SUPERIORE_TOTALE_REVOCATO = "E.W064";
	String WARN_RECUPERO_PRESENTE_PROPOSTA_CERTIFICAZIONE_PROGETTO = "W.W065";
	String WARN_RECUPERO_RECUPERO_CERTIFICATO = "W.W066";
	String WARN_RECUPERO_CONFERMA_CANCELLAZIONE = "W.W068";
	String WARN_RECUPERO_FUNZIONALITA_NON_ABILITATA = "W.W069";

	String ERRORE_TIMEOUT_DICHIARAZIONE_DI_SPESA = "E.E900";

	String ERRORE_TOTALE_VALIDATO_PER_RETTIFICA_INFERIORE_ULTIMO_CERTIFICATO_NETTO = "E.E901";

	String KEY_NUMERO_DOMANDA_PRESENTE = "E.E100";
	String KEY_CUP_PRESENTE = "E.E101";
	String FORMATO_INDIRIZZO_EMAIL = "E.E102";
	String DIMENSIONE_CAMPO_STRINGA = "E.E103";

	String CAMPO_OBBLIGATORIO_PER_AVVIO = "E.E099";
	String DATA_NEL_FUTURO = "E.E104";
	String DIMENSIONE_CAMPO_NUMERICO = "E.E106";
	String FORMATO_DATA = "E.E105";
	String DATE_NON_CONSECUTIVE = "E.E107";
	String NESSUNA_SEDE_INTERVENTO_PRESENTE = "E.E108";
	String FORMATO_NUMERO_TELEFONICO = "E.E109";
	String RUOLO_ATTUATORE_NON_PRESENTE = "E.E110";
	String NESSUNA_FASE_PER_IL_PROGETTO = "E.E111";
	String FORMATO_CAP_ERRATO = "E.E113";
	String DATA_INIZIO_VALIDITA_FASI_MANCANTE = "E.E114";
	String FORMATO_NUMERO_FAX_ERRATO = "E.E115";

	/*
	 * Irregolarita'
	 */
	String ERRORE_IRREGOLARITA_BLOCCATA_INS = "E.E116";
	String ERRORE_IRREGOLARITA_ESISTENTE = "E.E117";
	String ERRORE_IRREGOLARITA_BLOCCATA_MOD = "E.E118";
	String ERRORE_IRREGOLARITA_INVIATA_MOD = "E.E119";
	String ERRORE_IRREGOLARITA_BLOCCATA_CANC = "E.E120";
	String ERRORE_IRREGOLARITA_INVIATA_CANC = "E.E121";
	String ERRORE_IRREGOLARITA_TIPO_FILE = "E.E122";
	String ERRORE_IRREGOLARITA_VERSIONE_MOD = "E.E125";
	String ERRORE_IRREGOLARITA_PROVV_LEGATA_A_DEF = "E.E154";

	/*
	 * Rimodulazione conto economico
	 */
	String ERRORE_RCE_PROPOSTA_PERCENTUALE_RIBASSO_ASTA_MAGGIORE_ZERO = "E.E201";
	String ERRORE_RCE_RIMODULAZIONE_PERCENTUALE_RIBASSO_ASTA_MAGGIORE_ZERO = "E.E203";
	String ERRORE_RCE_SELEZIONARE_PROCEDURA_AGGIUDICAZIONE = "E.E129";
	String ERRORE_RCE_PERCENTUALE_OBBLIGATORIA = "E.E141";
	String ERRORE_RCE_PERCENTUALE_NEGATIVA = "E.E142";
	String ERRORE_RCE_RIMODULAZIONE_PERCENTUALE_NON_CORRISPONDE_IMPORTO = "E.E148";
	String DIMENSIONE_CAMPO_CUP_ERRATA = "E.E130";
	String ERRORE_DATA_ANTECEDENTE_DATA_COMITATO_E_CONCESSIONE = "W.E902";

	/*
	 * Procedure di aggiudicazione
	 */
	String ERRORE_PROCEDURA_AGGIUDICAZIONE_CIG_GIA_ASSEGNATO = "E.E210";
	String ERRORE_PROCEDURA_AGGIUDICAZIONE_CPA_GIA_ASSEGNATO = "E.E211";

	/*
	 * Gestione Indicatori
	 */
	String KEY_ERR_VALORE_INIZIALE_MON = "E.E131";
	String KEY_ERR_VALORE_FINALE_MON = "E.E132";
	String KEY_ERR_VALORE_INIZIALE_MANCANTE_MON = "E.E132_bis";
	String KEY_ERR_VALORE_FINALE_MANCANTE_MON = "E.E132_ter";

	/*
	 * Gestione Cronoprogramma
	 */
	String KEY_ERR_DATE_INCOERENTI_CRONO = "E.E133";
	String KEY_ERR_DATA_FUTURA_CRONO = "E.E134";
	String KEY_ERR_DATA_PRECEDENTE_PRESENTAZIONE_CRONO = "E.E135";
	String KEY_MSG_DATE_EFFETTIVE_OBBLIGATORIE_CRONO = "E.MX46";
	String KEY_MSG_DATE_NON_OBBLIGATORIE_CRONO = "W.MX47";
	String KEY_MSG_MANCA_INDICATORE_CORE_CRONO = "E.MX48";
	String KEY_MSG_DATA_PREVISTA_FINE_MINORE_INIZIO_CRONO = "E.MX49";
	String KEY_MSG_DATA_EFFETTIVA_FINE_MINORE_INIZIO_CRONO = "E.MX50";
	String KEY_MSG_DATE_EFFETIVE_FUTURE_CRONO = "E.MX51";
	String KEY_MSG_DATE_PREVISTA_ED_EFFETTIVE_PRIMA_FASE_CRONO = "E.MX52";
	String KEY_MSG_MANCA_MOTIVO_SCOSTAMENTO_CRONO = "E.MX53";
	String KEY_MSG_MANCA_DATA_INIZIO_PREVISTA_CRONO = "E.MX54";
	String KEY_MSG_MANCA_DATA_FINE_PREVISTA_CRONO = "E.MX55";

	/*
	 * Scheda progetto
	 */
	String KEY_ERR_SIMON_CUP_ERRATO_O_NON_PRESENTE = "E.E136";
	String KEY_ERR_SIMON_ALLINEAMENTO_CUP_NON_RIUSCITO = "E.E137";
	String KEY_ERR_SIMON_MODIFICA_ANNO_CONCESSIONE_NON_CONCESSO = "E.E138";
	String KEY_ERR_CUP_NON_ALLINEATO = "E.E139";
	String KEY_ERR_SIMON_DATI_GENERALI_PROVVISORI = "E.E150";
	String ALLINEAMENTO_CUP_NECESSARIO_PER_AVVIO_PROGETTO = "E.E140";

	String ERRORE_FILE_NON_TROVATO = "E.E204";

	String KEY_MSG_DATE_PREVISTA_ED_EFFETTIVE_POST_2007 = "E.MX58";

	/*
	 * Gestione Progetto
	 */
	String ERRORE_GESTIONE_PROGETTO_DATI_NON_TROVATI = "E.E145";
	String ERRORE_GESTIONE_PROGETTO_CUP_NON_UNIVOCO = "E.E146";
	String ERRORE_GESTIONE_PROGETTO_CANCELLAZIONE_SEDE_NON_CONSENTITA = "E.E147";

	/*
	 * Soppressioni
	 */
	String ERRORE_SOPPRESSIONI_MAGGIORE_DI_CERTIFICABILE_LORDO = "E.E153";
	String WARNING_SOPPRESSIONE_ACQUISITA_IN_PROPOSTA_APPROVATA = "W.W079";
	String ERRORE_SOPPRESSIONE_PRESENTE_CERTIFICAZIONE_RECUPERO = "E.E205";

	/*
	 * Bilancio
	 */
	String ERRORE_BILANCIO_SELEZIONARE_UN_IMPEGNO = "E.W081";
	String ERRORE_BILANCIO_SELEZIONARE_ALMENO_UN_BANDOLINEA = "E.W082";
	String ERRORE_BILANCIO_SELEZIONARE_ALMENO_UN_PROGETTO = "E.W083";
	String ERRORE_BILANCIO_BANDOLINEA_NON_DISASSOCIATI = "E.E206";
	String ERRORE_BILANCIO_PROGETTI_NON_DISASSOCIATI = "E.E207";
	String ERRORE_IMPEGNI_IMPOSSIBILE_REPERIRE_DATI_IMPEGNO = "E.E213";
	String ERRORE_IMPEGNI_SELEZIONARE_BANDOLINEA_DA_ASSOCIARE = "E.E217";
	String ERRORE_IMPEGNI_SELEZIONARE_PROGETTO_DA_ASSOCIARE = "E.E218";
	String WARNING_IMPEGNI_NESSUN_IMPEGNO_DA_AGGIORNARE = "W.W085";

	String ERRORE_SELEZIONARE_ALMENO_UNA_FONTE = "E.W084";

	String ERRORE_BILANCIO_LIQUIDAZIONE_STATO_BOZZA = "W.WB01";
	String ERRORE_BILANCIO_IMPORTO_DA_LIQUIDARE_MAGG_ZERO = "E.MB02";
	String ERRORE_BILANCIO_IMPORTO_DA_LIQUIDARE_SUPERIORE_RESIDUO_SPETTANTE = "E.MB03";
	String ERRORE_BILANCIO_NON_TROVA_ASSOCIAZIONE_PROGETTO_IMPEGNO = "W.MB04";
	String ERRORE_BILANCIO_SOMMA_IMPORTI_SU_IMPEGNI_NEGATIVA = "E.MB05";
	String ERRORE_BILANCIO_SOMMA_IMPORTI_DIVERSO_DA_TOTALE = "E.MB06";
	String ERRORE_BILANCIO_SISTEMA_ESTERNO_BIL_NON_DISP = "W.MB07";
	String ERRORE_BILANCIO_PER_ALCUNE_FONTI_NON_IMPEGNI_ASSOC = "W.MB08";
	String ERRORE_BILANCIO_SOMMA_LIQUIDAZ_AL_NETTO_REC_MAGG_IMPORTO_AGEVOLATO = "E.MB09";
	String ERRORE_BILANCIO_SUPERATO_NUMERO_MAX_IMPEGNI = "E.MB10";
	String ERRORE_BILANCIO_DATA_INIZIO_PERIODO_SUPERIORE_DATA_FINE_PERIODO = "E.MB11";
	String WARNING_BILANCIO_LIQUIDAZIONE_RESIDUO_NON_DISPONIBILE = "W.W086";
	String ERRORE_BILANCIO_LIQUIDAZIONE_QUOTE_NON_TROVATE = "E.E216";
	String ERRORE_BILANCIO_LIQUIDAZIONE_IBAN_NON_VALIDO = "E.E219";
	String ERRORE_BILANCIO_LIQUIDAZIONE_CONTRIBUTO_NON_ASSOCIATO_AL_CONTOECONOMICO = "E.E220";

	String ERRORE_BILANCIO_IMPEGNO_NON_GESTITO = "E.E214";

	/*
	 * Consultazione atti
	 */
	String ERRORE_CONSULTAZIONE_ATTI_PIU_ATTI_IN_STATO_BOZZA = "E.E215";

	/*
	 * Gestione template
	 */
	String ERRORE_GESTIONE_TEMPLATE_ANTEPRIMA_IMPOSSIBILE = "E.E208";
	String ERRORE_GESTIONE_TEMPLATE_VISUALIZZA_MODELLO_IMPOSSIBILE = "E.E209";
	String ERRORE_GESTIONE_TEMPLATE_SELEZIONARE_UN_BANDOLINEA_O_UN_TIPO_DOUCMENTO = "E.E212";

	/*
	 * ERRORI DATI INTEGRATIVI X LIQUIDAZIONE BILANCIO
	 */
	String ERRORE_LIQ_DATI_INTEG_LUNGH_NOTE = "E.ELDI1";

	String ERRORE_LIQ_DATI_INTEG_LUNGH_CAUS_PAG = "E.ELDI2";

	String ERRORE_LIQ_DATI_INTEG_LUNGH_FUNZ = "E.ELDI3";

	String ERRORE_LIQ_DATI_INTEG_LUNGH_TEL = "E.ELDI4";

	String ERRORE_LIQ_DATI_INTEG_LUNGH_DIR = "E.ELDI5";

	String ERRORE_LIQ_DATI_INTEG_LUNGH_ALL_ALTRO = "E.ELDI6";

	String ERRORE_LIQ_DATI_INTEG_DATA_SCADENZA_PAG = "E.ELDI7";

	String ERRORE_LIQ_BEN_IBAN = "E.ELB1";

	String ERRORE_LIQ_BEN_MOD_PAG = "E.ELB2";

	String ERRORE_LIQ_IBAN_BEN_MOD_PAG = "E.ELB3";

	String ERRORE_LIQ_NCR_BEN_MOD_PAG = "E.E260";

	String ERRORE_LIQ_MODALITA_EROGAZIONE_NON_PREVISTA = "E.E261";

	String SERVIZIO_BILANCIO_NON_DISPONIBILE = "E.ELB4";

	String ERRORE_STRUTTURA_XML = "E.ECM137";

	String ERRORE_DOWNLOAD_DOCUMENTO = "E.E221";

	String ERROR_ACCESSO_NON_AUTORIZZATO = "E.E007";
	String ERROR_PASSWORD_SCADUTA = "E.E063";
	String ERROR_ACCOUNT_SCADUTO = "E.E064";
	String ERROR_ACCESSO_GESTIONE_INCARICHI = "E.E065";

	String ERRORE_NESSUN_BENEFICIARIO_ASSOCIATO = "E.ECM138";

	String ERRORE_LUNGHEZZA_CAMPO = "E.E259";

	String ERRORE_RETTIFICA_PROPOSTA_IN_CORSA = "E.RET01";
	String ERRORE_DATA_ANTERIORE = "E.E280";

	String ERRORE_SERVIZI_DI_TOPONOMASTICA = "E.E281";

	String ERROR_MAX_FILE_SIZE = "E.E282";
	String ERROR_USER_SPACE_FULL = "E.E283";
	String ERROR_DUPLICATED_FILE_NAME_PER_FOLDER = "E.E284";
	String ERROR_ZERO_FILE_SIZE = "E.E285";
	String ERROR_INVALID_FILE_EXTENSION = "E.E286";

	public String ERROR_FOTOGRAFIA_ESISTE = "E.E290";
	public String ERROR_FOTOGRAFIA_NESSUN_APPROVATA_TROVATA = "E.E291";

	/*
	 * Gestione Economie
	 */

	String ERRORE_ECONOMIA_DATA_SUCCESSIVA = "E.E292";
	String ERRORE_ECONOMIA_IMPORTO_CEDUTO_SUPERIORE = "E.E293";
	String ERRORE_ECONOMIA_PROGETTI_UGUALI = "E.E294";
	String ERRORE_IMPOSSIBILE_ELIMINARE_ECOMONIA = "E.E295";

	/*
	 * Gestione Sedi
	 */

	String ERRORE_DATI_MANCANTI = "E.E300";
	
	
	String KEY_MSG_CERTIFICAZIONE_ERRORE_CHIUSURA_CONTI = "E.CIF01";
	String KEY_MSG_CERTIFICAZIONE_ERRORE_AGGIORNA_DATI = "E.CIF02";
	
	public String KEY_ERROR_PROCEDURA_CAMPIONAMENTO_PRESENTE = "M.PC001";
}
