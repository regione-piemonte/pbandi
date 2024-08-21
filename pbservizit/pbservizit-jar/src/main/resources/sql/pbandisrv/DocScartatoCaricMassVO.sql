/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

 SELECT CMS.ID_CARICA_MASS_DOC_SPESA,
        ID_SCARTI_CARICA_MASS_DS,
        nvl2(DESC_TIPO_DOC_SPESA ,' '||DESC_TIPO_DOC_SPESA,'') ||
        nvl2(NUMERO_DOCUMENTO,' '||NUMERO_DOCUMENTO,'') ||
        nvl2(DT_DOCUMENTO,' '||to_char(DT_DOCUMENTO,'dd/MM/YYYY'),'') ||
        nvl2( CODICE_FISCALE_FORNITORE,' ' ||CODICE_FISCALE_FORNITORE,'' )
		  DOCUMENTO_DI_SPESA,
        SCARTI.RIGA_ERRORE,
        SCARTI.CODICE_ERRORE,
        ERR.DESCRIZIONE errore_warning,
        DT_VALIDAZIONE
 FROM PBANDI_T_CARICA_MASS_DOC_SPESA CMS 
 JOIN
 PBANDI_T_SCARTI_CARICA_MASS_DS SCARTI
 ON CMS.ID_CARICA_MASS_DOC_SPESA=SCARTI.ID_CARICA_MASS_DOC_SPESA
 LEFT OUTER JOIN PBANDI_D_ERRORE_BATCH ERR 
 ON SCARTI.CODICE_ERRORE=ERR.CODICE_ERRORE