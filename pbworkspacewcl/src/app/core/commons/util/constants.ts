/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Constants as GlobalConstants } from '@pbandi/common-lib';

export class Constants extends GlobalConstants {

	// RUOLO UTENTE
	public static CODICE_RUOLO_ISTR_AFFIDAMENTI: string = "ISTR-AFFIDAMENTI";
	public static CODICE_RUOLO_BEN_MASTER: string = "BEN-MASTER";
	public static CODICE_RUOLO_PERSONA_FISICA: string = "PERSONA-FISICA";
	public static CODICE_RUOLO_ADG_CERT: string = "ADG-CERT";
	public static CODICE_RUOLO_ADC_CERT: string = "ADC-CERT";
	public static CODICE_RUOLO_ADG_IST_MASTER: string = "ADG-IST-MASTER";
	public static CODICE_RUOLO_ADA_OPE_MASTER: string = "ADA-OPE-MASTER";
	public static CODICE_RUOLO_OPE_SUP_IST: string = "OPE-SUP-IST";
	public static CODICE_RUOLO_GDF: string = "GDF";
	public static CODICE_RUOLO_ADG_ISTRUTTORE: string = "ADG-ISTRUTTORE";
	public static CODICE_RUOLO_OI_IST_MASTER: string = "OI-IST-MASTER";
	public static CODICE_RUOLO_OI_ISTRUTTORE: string = "OI-ISTRUTTORE";
	public static CODICE_RUOLO_OPE_ISTR_PROG: string = "OPE-ISTR-PROG";
	public static CODICE_RUOLO_ISTR_CREDITI: string = "ISTR-CREDITI";
	public static CODICE_RUOLO_CSI_ASSISTENZA: string = "ISTR-CREDITI";
	public static CODICE_RUOLO_CREATOR: string = "CREATOR";

	// DESC BREVE CAUSALE RICHIESTA EROGAZIONE
	public static DESC_BREVE_CAUSALE_PRIMO_ACCONTO: string = "PA";
	public static DESC_BREVE_CAUSALE_PRIMO_ACCONTO_NON_STANDARD: string = "PA-INS";
	public static DESC_BREVE_CAUSALE_SALDO: string = "SAL";
	public static DESC_BREVE_CAUSALE_SALDO_NON_STANDARD: string = "SAL-INS";
	public static DESC_BREVE_CAUSALE_SECONDO_ACCONTO: string = "SA";
	public static DESC_BREVE_CAUSALE_SUCCESSIVO_ACCONTO_NON_STANDARD: string = "SA-INS";
	public static DESC_BREVE_CAUSALE_ULTERIORE_ACCONTO: string = "UA";

	// DESC BREVE PAGINA HELP
	public static DESC_BREVE_PAGINA_HELP_ATTIVITA_DA_SVOLGERE: string = "ATTIVITA_DA_SVOLGERE";
}
