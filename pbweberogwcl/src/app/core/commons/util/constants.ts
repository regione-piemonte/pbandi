/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Constants as GlobalConstants } from '@pbandi/common-lib';

export class Constants extends GlobalConstants {

	// RUOLO UTENTE
	public static CODICE_RUOLO_ADG_CERT: string = "ADG-CERT";
	public static CODICE_RUOLO_ADC_CERT: string = "ADC-CERT";
	public static CODICE_RUOLO_ADG_IST_MASTER: string = "ADG-IST-MASTER";
	public static CODICE_RUOLO_OI_IST_MASTER: string = "OI-IST-MASTER";
	public static CODICE_RUOLO_ADG_ISTRUTTORE: string = "ADG-ISTRUTTORE";
	public static CODICE_RUOLO_OI_ISTRUTTORE: string = "OI-ISTRUTTORE";
	public static CODICE_RUOLO_OPE_SUP_IST: string = "OPE-SUP-IST";
	public static CODICE_RUOLO_BEN_MASTER: string = "BEN-MASTER";
	public static CODICE_RUOLO_PERSONA_FISICA: string = "PERSONA-FISICA";

	// DESC BREVE CAUSALE RICHIESTA EROGAZIONE
	public static DESC_BREVE_CAUSALE_PRIMO_ACCONTO: string = "PA";
	public static DESC_BREVE_CAUSALE_PRIMO_ACCONTO_NON_STANDARD: string = "PA-INS";
	public static DESC_BREVE_CAUSALE_SALDO: string = "SAL";
	public static DESC_BREVE_CAUSALE_SALDO_NON_STANDARD: string = "SAL-INS";
	public static DESC_BREVE_CAUSALE_SECONDO_ACCONTO: string = "SA";
	public static DESC_BREVE_CAUSALE_SUCCESSIVO_ACCONTO_NON_STANDARD: string = "SA-INS";
	public static DESC_BREVE_CAUSALE_ULTERIORE_ACCONTO: string = "UA";


	//DESC BREVE LINEA INTERVENTO
	public static DESC_BREVE_LINE_INTERVENTO_BANDIREGP: string = "BANDIREGP";
	public static DESC_BREVE_LINEA_INTERVENTO_POR_FESR_2021_2027: string = "POR-FESR-2021-2027";
	public static DESC_BREVE_LINEA_INTERVENTO_PNRR: string = "PNRR";


	// DESC BREVE PAGINA HELP
	public static DESC_BREVE_PAGINA_HELP_GEST_DATI_PROG: string = "GEST_DATI_PROG";
	public static DESC_BREVE_PAGINA_HELP_GEST_FIDEIUSSIONI: string = "GEST_FIDEIUSSIONI";
	public static DESC_BREVE_PAGINA_HELP_FIDEIUSSIONE: string = "FIDEIUSSIONE";
	public static DESC_BREVE_PAGINA_HELP_COMUN_RINUNCIA: string = "COMUN_RINUNCIA";
	public static DESC_BREVE_PAGINA_HELP_INVIO_COMUN_RINUNCIA: string = "INVIO_COMUN_RINUNCIA";
}