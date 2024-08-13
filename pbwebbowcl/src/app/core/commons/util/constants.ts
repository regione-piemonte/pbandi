/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Constants as GlobalConstants } from '@pbandi/common-lib';

export class Constants extends GlobalConstants {

	// RUOLO UTENTE
	public static CODICE_RUOLO_ADG_CERT: string = "ADG-CERT";
	public static CODICE_RUOLO_ADC_CERT: string = "ADC-CERT";
	public static CODICE_RUOLO_PERSONA_FISICA: string = "PERSONA-FISICA";
	public static CODICE_RUOLO_ISTR_AFFIDAMENTI: string = "ISTR-AFFIDAMENTI";
	public static CODICE_RUOLO_OI_ISTRUTTORE: string = "OI-ISTRUTTORE";
	public static CODICE_RUOLO_ADG_ISTRUTTORE: string = "ADG-ISTRUTTORE";

	//TIPO DOC INDEX
	public static DESC_BREVE_TIPO_DOC_INDEX_ACTA: string = "ACTA";

	public static DESC_BREVE_NORMATIVA_21_27: string = "POR-FESR-2021-2027";
	public static DESC_BREVE_NORMATIVA_14_20: string = "POR-FESR-2014-2020";

	// RUOLO ENTE COMPETENZA
	public static DESC_BREVE_RUOLO_ENTE_RESP_AFFIDAMENTI: string = "RESP.AFFIDAMENTI";
	public static DESC_BREVE_RUOLO_ENTE_RAGIONERIA_DELEGATA: string = "RAGIONERIA DELEGATA";
}