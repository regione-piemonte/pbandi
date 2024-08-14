/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Constants as GlobalConstants } from '@pbandi/common-lib';

export class Constants extends GlobalConstants {

	// STATO PROPOSTA CERTIFICAZIONE
	public static DESC_BREVE_STATO_PROPOSTA_CODA: string = "00coda";
	public static DESC_BREVE_STATO_PROPOSTA_ELABORAZIONE: string = "03elab";
	public static DESC_BREVE_STATO_PROPOSTA_ERRORE_ELABORAZIONE: string = "04err";
	public static DESC_BREVE_STATO_PROPOSTA_APERTA: string = "05open";
	public static DESC_BREVE_STATO_PROPOSTA_ANNULLATA: string = "06annu";
	public static DESC_BREVE_STATO_PROPOSTA_APPROVATA: string = "07appr";
	public static DESC_BREVE_STATO_PROPOSTA_RESPINTA: string = "09resp";
	public static DESC_BREVE_STATO_PROPOSTA_BOZZA: string = "bozza";
	public static DESC_BREVE_STATO_PROPOSTA_CODA_BOZZA: string = "00coda-bozza";
	public static DESC_BREVE_STATO_PROPOSTA_ELABORAZIONE_BOZZA: string = "03elab-bozza";
	public static DESC_BREVE_STATO_PROPOSTA_ERRORE_ELABORAZIONE_BOZZA: string = "04err-bozza";
	public static DESC_BREVE_STATO_PROPOSTA_PREVIEW: string = "prev";
	public static DESC_BREVE_STATO_PROPOSTA_PREVIEW_BOZZA: string = "prev-bozza";
	public static DESC_BREVE_STATO_PROPOSTA_ERRORE_PREVIEW: string = "err-prev";
	public static DESC_BREVE_STATO_PROPOSTA_ERRORE_PREVIEW_BOZZA: string = "err-prev-bozza";
	public static DESC_BREVE_STATO_PROPOSTA_INTERMEDIA_FINALE: string = "intermedia-finale";
	public static DESC_BREVE_STATO_PROPOSTA_CHIUSURA_CONTI: string = "chiusura-conti";
	public static DESC_BREVE_STATO_PROPOSTA_AGGIORNAMENTO_INTERMEDIA_FINALE: string = "aggiorna-int-finale";

	// DESC BREVE TIPO DOC INDEX
	public static DESC_BREVE_TIPO_DOC_DICHIARAZIONE_FINALE_CERTIF: string = "DFC";
	public static DESC_BREVE_TIPO_DOC_CHECKLIST_CERTIF_PROPOSTA: string = "CLC";
	public static DESC_BREVE_TIPO_DOC_CHECKLIST_CERTIF_PROGETTO: string = "CLCP";

	// RUOLO UTENTE
	public static CODICE_RUOLO_OI_IST_MASTER: string = "OI-IST-MASTER";
	public static CODICE_RUOLO_OI_ISTRUTTORE: string = "OI-ISTRUTTORE";
	public static CODICE_RUOLO_ADG_CERT: string = "ADG-CERT";
	public static CODICE_RUOLO_ADC_CERT: string = "ADC-CERT";

}