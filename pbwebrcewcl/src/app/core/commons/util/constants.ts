/*
 * Copyright Regione Piemonte - 2024 
 * SPDX-License-Identifier: EUPL-1.2 
*/

import { Constants as GlobalConstants } from '@pbandi/common-lib';

export class Constants extends GlobalConstants {

	// RUOLO USER
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

	// ID STATO AFFIDAMENTO
	public static ID_STATO_AFFIDAMENTO_DA_INVIARE: number = 1;
	public static ID_STATO_AFFIDAMENTO_IN_VERIFICA: number = 2;
	public static ID_STATO_AFFIDAMENTO_VERIFICA_PARZIALE: number = 3;
	public static ID_STATO_AFFIDAMENTO_VERIFICA_DEFINITIVA: number = 4;
	public static ID_STATO_AFFIDAMENTO_RICHIESTA_INTEGRAZIONE: number = 5;

	// DESC STATO AFFIDAMENTO
	public static DESC_STATO_AFFIDAMENTO_DA_INVIARE: string = "DA INVIARE";

	// RUOLO FORNITORE
	public static ID_TIPO_PERCETTORE_PERSONA_FISICA: number = 5;

	// TIPO SOGGETTO
	public static ID_TIPO_SOGGETTO_PERSONA_FISICA: number = 1;

	// COD IGRUE FASE MONIT
	public static COD_IGRUE_FASE_MONIT_STUDIO_FATTIBILITA: string = "A00";
	public static COD_IGRUE_FASE_MONIT_PROGET_PRELIMINARE: string = "A01";

	//DESC BREVE LINEA INTERVENTO
	public static DESC_BREVE_LINEA_INTERVENTO_POR_FESR_2021_2027: string = "POR-FESR-2021-2027";

	//DESC BREVE FASI QTE
	public static DESC_BREVE_COLONNA_QTE_PROG_1: string = "QTE_PROG_1";
	public static DESC_BREVE_COLONNA_QTE_AGGI_2: string = "QTE_AGGI_2";
	public static DESC_BREVE_COLONNA_QTE_REND_3: string = "QTE_REND_3";
	public static DESC_BREVE_COLONNA_QTE_VAR1: string = "QTE_VAR1";
	public static DESC_BREVE_COLONNA_QTE_VAR2: string = "QTE_VAR2";

	// DESC BREVE PAGINA HELP
	public static DESC_BREVE_PAGINA_HELP_INDICATORI_AVVIO: string = "INDICATORI_AVVIO";
	public static DESC_BREVE_PAGINA_HELP_INDICATORI: string = "INDICATORI";
	public static DESC_BREVE_PAGINA_HELP_CRONOPROGRAMMA_AVVIO: string = "CRONPROGRAMMA_AVVIO";
	public static DESC_BREVE_PAGINA_HELP_CRONOPROGRAMMA: string = "CRONPROGRAMMA";
	public static DESC_BREVE_PAGINA_HELP_GEST_AFFIDAMENTI: string = "GEST_AFFIDAMENTI";
	public static DESC_BREVE_PAGINA_HELP_AFFIDAMENTO: string = "AFFIDAMENTO";

	// DESC BREVE SOGGETTI FINANZIATORI
	public static DESC_BREVE_SOGG_FINANZIATORE_FONDO_COMPL: string = "Fondo_Complementare";
	public static DESC_BREVE_SOGG_FINANZIATORE_FONDI_PROPRI_ENTE: string = "Fondi_propri_ Ente";
	public static DESC_BREVE_SOGG_FINANZIATORE_COFIN_560: string = "560/1993-3/10-80/14";
	public static DESC_BREVE_SOGG_FINANZIATORE_COFIN_513: string = "513/1997-21/2001";
	public static DESC_BREVE_SOGG_FINANZIATORE_AVVIO_OPERE: string = "Avvio_Opere_Indiff";
	public static DESC_BREVE_SOGG_FINANZIATORE_ALTRE_FONTI_PUBBL: string = "Altre_fonti_pubblic";
	public static DESC_BREVE_SOGG_FINANZIATORE_IMP_FIN_PRIVATO: string = "Imp_fin_privato";

	// DESC BREVE TIPO INTERV QTES
	public static DESC_BREVE_TIPO_INTERV_QTES_INT_A: string = "INT_A";
	public static DESC_BREVE_TIPO_INTERV_QTES_INT_B: string = "INT_B";
	public static DESC_BREVE_TIPO_INTERV_QTES_INT_C: string = "INT_C";
	public static DESC_BREVE_TIPO_INTERV_QTES_INT_D: string = "INT_D";
	public static DESC_BREVE_TIPO_INTERV_QTES_INT_E: string = "INT_E";
	public static DESC_BREVE_TIPO_INTERV_QTES_INT_F: string = "INT_F";

	// DESC BREVE ALTRI VALORI QTES
	public static DESC_BREVE_ALTRI_VALORI_QTES_NA: string = "NA";
	public static DESC_BREVE_ALTRI_VALORI_QTES_SCM: string = "SCM";
	public static DESC_BREVE_ALTRI_VALORI_QTES_SCS: string = "SCS";
	public static DESC_BREVE_ALTRI_VALORI_QTES_CR: string = "CR";
	public static DESC_BREVE_ALTRI_VALORI_QTES_ST1: string = "ST1";
	public static DESC_BREVE_ALTRI_VALORI_QTES_ST2: string = "ST2";
	public static DESC_BREVE_ALTRI_VALORI_QTES_AOC: string = "AOC";
	public static DESC_BREVE_ALTRI_VALORI_QTES_IMPR: string = "IMPR";
	public static DESC_BREVE_ALTRI_VALORI_QTES_RA: string = "RA";
	public static DESC_BREVE_ALTRI_VALORI_QTES_CI: string = "CI";
	public static DESC_BREVE_ALTRI_VALORI_QTES_IVA: string = "IVA";
	public static DESC_BREVE_ALTRI_VALORI_QTES_IRAP: string = "IRAP";
	public static DESC_BREVE_ALTRI_VALORI_QTES_CT: string = "CT";
	public static DESC_BREVE_ALTRI_VALORI_QTES_IVAD: string = "IVAD";
	public static DESC_BREVE_ALTRI_VALORI_QTES_RI: string = "RI";
	public static DESC_BREVE_ALTRI_VALORI_QTES_CE: string = "CE";
}