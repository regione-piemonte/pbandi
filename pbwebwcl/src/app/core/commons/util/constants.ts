/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Constants as GlobalConstants } from '@pbandi/common-lib';

export class Constants extends GlobalConstants {

	// DESC BREVE TIPO DOC SPESA
	public static DESC_BREVE_TIPO_DOC_SPESA_ATTO_DI_LIQUIDAZIONE: string = "ADL";
	public static DESC_BREVE_TIPO_DOC_SPESA_AUTODICHIARAZIONE_COMPENSO_SOCI: string = "CS";
	public static DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO: string = "CD";
	public static DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_A_PROGETTO: string = "CC";
	public static DESC_BREVE_TIPO_DOC_SPESA_CEDOLINO_COSTI_STANDARD: string = "CDCS";
	public static DESC_BREVE_TIPO_DOC_SPESA_FATTURA: string = "FT";
	public static DESC_BREVE_TIPO_DOC_SPESA_FATTURA_FIDEIUSSORIA: string = "FF";
	public static DESC_BREVE_TIPO_DOC_SPESA_FATTURA_LEASING: string = "FL";
	public static DESC_BREVE_TIPO_DOC_SPESA_NOTA_DEBITO: string = "ND";
	public static DESC_BREVE_TIPO_DOC_SPESA_NOTA_CREDITO: string = "NC";
	public static DESC_BREVE_TIPO_DOC_SPESA_NOTA_PRESTAZIONE_OCCASIONALE: string = "NPO";
	public static DESC_BREVE_TIPO_DOC_SPESA_NOTA_SPESE: string = "NS";
	public static DESC_BREVE_TIPO_DOC_SPESA_QUOTA_AMMORTAMENTO: string = "QA";
	public static DESC_BREVE_TIPO_DOC_SPESA_RICEVUTA_LOCAZIONE: string = "RL";
	public static DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_DIRETTE: string = "SG";
	public static DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE: string = "SF";
	public static DESC_BREVE_TIPO_DOC_SPESA_SPESE_GENERALI_FORFETTARIE_COSTI_SEMPLIFICATI: string = "SGFCS";
	public static DESC_BREVE_TIPO_DOC_SPESA_DOCUMENTO_GENERICO: string = "DG";
	public static DESC_BREVE_TIPO_DOC_SPESA_ATTO_ACQUISTO: string = "ADA";
	public static DESC_BREVE_TIPO_DOC_SPESA_AUTOCERTIFICAZIONE_SPESE: string = "AS";
	public static DESC_BREVE_TIPO_DOC_SPESA_SAL_SENZA_QUIETANZA: string = "SALSQ";
	public static DESC_BREVE_TIPO_DOC_SPESA_SAL_CON_QUIETANZA: string = "SALCQ";
	public static DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_MENSILE_TIROCINANTE: string = "CMT";
	public static DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_IMPRESA_ARTIGIANA: string = "CIA";
	public static DESC_BREVE_TIPO_DOC_SPESA_COMPENSO_SOGGETTO_GESTORE: string = "CSG";

	//TIPO FORNITORE
	public static ID_TIPO_FORNITORE_PERSONA_FISICA: number = 1;
	public static ID_TIPO_FORNITORE_PERSONA_GIURIDICA: number = 2;

	//DESC_BREVE_STATO_DOC_SPESA
	public static DESC_BREVE_STATO_DOC_SPESA_DICHIARABILE: string = "I";
	public static DESC_BREVE_STATO_DOC_SPESA_IN_VALIDAZIONE: string = "D";
	public static DESC_BREVE_STATO_DOC_SPESA_SOSPESO: string = "S";
	public static DESC_BREVE_STATO_DOC_SPESA_PARZIALMENTE_VALIDATO: string = "P";
	public static DESC_BREVE_STATO_DOC_SPESA_VALIDATO: string = "V";
	public static DESC_BREVE_STATO_DOC_SPESA_NON_VALIDATO: string = "N";
	public static DESC_BREVE_STATO_DOC_SPESA_RESPINTO: string = "R";
	public static DESC_BREVE_STATO_DOC_SPESA_DA_COMPLETARE: string = "DC";

	public static DESC_STATO_DOC_SPESA_SOSPESO: string = "SOSPESO";

	//TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA
	public static TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CLONA: string = "clona";
	public static TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_INSERISCI: string = "inserisci";
	public static TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_MODIFICA: string = "modifica";
	public static TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DETTAGLIO: string = "dettaglio";
	public static TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_CANCELLA: string = "cancella";
	public static TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_ASSOCIA: string = "associa";
	public static TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_DICHIARAZIONE_DI_SPESA: string = "dichiarazione";
	public static TIPO_OPERAZIONE_DOCUMENTO_DI_SPESA_VALIDAZIONE: string = "validazione";

	//VALUE RADIO RICERCA RENDICONTAZIONE
	public static VALUE_RADIO_RENDICONTAZIONE_CAPOFILA: string = "rbTipoCapofila";
	public static VALUE_RADIO_RENDICONTAZIONE_PARTNERS: string = "rbTipoPartners";
	public static VALUE_RADIO_DOCUMENTI_DI_SPESA_GESTITI: string = "rbGestiti";
	public static VALUE_RADIO_DOCUMENTI_DI_SPESA_TUTTI: string = "rbTutti";

	//TIPO DOC INDEX
	public static DESC_BREVE_TIPO_DOC_INDEX_ACTA: string = "ACTA";
	public static DESC_BREVE_TIPO_DOC_INDEX_ALLEGATI_DOMANDA: string = "ALLDOM";
	public static DESC_BREVE_TIPO_DOC_INDEX_ALLEGATI_ISTRUTTORIA: string = "CLISTR";
	public static DESC_BREVE_TIPO_DOC_INDEX_ACTIVE_INFO: string = "AFAI";
	public static DESC_BREVE_TIPO_DOC_INDEX_LETTERA_ACC_RICH_INTEG_RENDIC: string = "LARIR";

	public static ID_TIPO_DOC_INDEX_ALLEGATI_DOMANDA: number = 82;
	public static ID_TIPO_DOC_INDEX_ALLEGATI_ISTRUTTORIA: number = 25;
	public static ID_TIPO_DOC_INDEX_ACTIVE_INFO: number = 61;

	// RUOLO UTENTE
	public static CODICE_RUOLO_ADG_CERT: string = "ADG-CERT";
	public static CODICE_RUOLO_ADC_CERT: string = "ADC-CERT";
	public static CODICE_RUOLO_OI_IST_MASTER: string = "OI-IST-MASTER";
	public static CODICE_RUOLO_OI_ISTRUTTORE: string = "OI-ISTRUTTORE";
	public static CODICE_RUOLO_ADG_IST_MASTER: string = "ADG-IST-MASTER";
	public static CODICE_RUOLO_ADG_ISTRUTTORE: string = "ADG-ISTRUTTORE";
	public static CODICE_RUOLO_ISTR_AFFIDAMENTI: string = "ISTR-AFFIDAMENTI";
	public static CODICE_RUOLO_BEN_MASTER: string = "BEN-MASTER";
	public static CODICE_RUOLO_PERSONA_FISICA: string = "PERSONA-FISICA";
	public static CODICE_RUOLO_OPE_ISTR_PROG: string = "OPE-ISTR-PROG";
	public static CODICE_RUOLO_BENEFICIARIO: string = "BENEFICIARIO";
	public static CODICE_RUOLO_GDF: string = "GDF";
	public static CODICE_RUOLO_ADA_OPE_MASTER: string = "ADA-OPE-MASTER";
	public static CODICE_RUOLO_OPE_SUP_IST: string = "OPE-SUP-IST";
	public static CODICE_RUOLO_ISTR_CREDITI: string = "ISTR-CREDITI";
	public static CODICE_RUOLO_RAG_DEL: string = "RAG-DEL";

	// COD IGRUE FASE MONIT
	public static COD_IGRUE_FASE_MONIT_STUDIO_FATTIBILITA: string = "A00";
	public static COD_IGRUE_FASE_MONIT_PROGET_PRELIMINARE: string = "A01";

	//DESC BREVE STATO CHECKLIST
	public static DESC_BREVE_STATO_CHECKLIST_INVIATA: string = "I";
	public static DESC_BREVE_STATO_CHECKLIST_DA_AGGIORNARE: string = "DA";

	//ID UTENTE
	public static ID_UTENTE_MIGRAZIONE_FINPIS: number = -14;

	// DESC BREVE PAGINA HELP
	public static DESC_BREVE_PAGINA_HELP_DOC_PROGETTO: string = "DOC_PROGETTO";
	public static DESC_BREVE_PAGINA_HELP_RENDICONTAZIONE: string = "RENDICONTAZIONE";
	public static DESC_BREVE_PAGINA_HELP_DOC_SPESA: string = "DOC_SPESA";
	public static DESC_BREVE_PAGINA_HELP_DICH_SPESA: string = "DICH_SPESA";
	public static DESC_BREVE_PAGINA_HELP_INVIO_DICH_SPESA: string = "INVIO_DICH_SPESA";
	public static DESC_BREVE_PAGINA_HELP_GEST_FORNITORI: string = "GEST_FORNITORI";
	public static DESC_BREVE_PAGINA_HELP_GEST_INTEGRAZIONI: string = "GEST_INTEGRAZIONI"; //usata sia dalla nuova attivita sia dalla vecchia, che non e' piu' visibile
	public static DESC_BREVE_PAGINA_HELP_GEST_INT_DICH_SPESA: string = "GEST_INT_DICH_SPESA";

	// DESC BREVE MODALITA AGEVOLAZIONE
	public static DESC_BREVE_MODALITA_AGEVOLAZ_CONTRIBUTO: string = "Contributo";
	public static DESC_BREVE_MODALITA_AGEVOLAZ_SSF_PRESTITO: string = "SSF: prestito";

	// DESC BREVE TIPO DS
	public static DESC_BREVE_TIPO_DS_INTERMEDIA: string = "I";
	public static DESC_BREVE_TIPO_DS_FINALE: string = "F";
	public static DESC_BREVE_TIPO_DS_FINALE_COMUNICAZIONE: string = "FC";
	public static DESC_BREVE_TIPO_DS_INTEGRATIVA: string = "IN";


	// ID STATO RICHIESTA
	public static ID_STATO_RICHIESTA_COMPLETATA: number = 2;
	public static ID_STATO_RICHIESTA_CHIUSA_UFFICIO: number = 3;
	
	//DESC BREVE STATO RICHIESTA INTEG
	public static DESC_BREVE_STATO_RICHIESTA_INTEG_CHIUSA_UFFICIO: string = "CHIUSA_UFFICIO";

}