/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT  
  PBANDI_T_PROCEDURA_AGGIUDICAZ.COD_PROC_AGG,
  PBANDI_T_APPALTO.OGGETTO_APPALTO,
  PBANDI_T_APPALTO.ID_APPALTO,
  PBANDI_T_PROGETTO.CODICE_VISUALIZZATO  AS CODICE_PROGETTO ,
  PBANDI_T_PROGETTO.TITOLO_PROGETTO,
  RBLI.NOME_BANDO_LINEA AS DESC_BANDO_LINEA,
  RBLI.PROGR_BANDO_LINEA_INTERVENTO AS PROGR_BANDO_LINEA_INTERVENTO,
DOCINDEX.ID_DOCUMENTO_INDEX ,
PBANDI_T_FOLDER.ID_SOGGETTO_BEN ,
ID_FOLDER ,
PBANDI_T_FILE.SIZE_FILE,
PBANDI_T_FILE.NOME_FILE,
PBANDI_T_FILE.DT_INSERIMENTO,
PBANDI_T_FILE.DT_AGGIORNAMENTO ,
FILE_ENTITA.ID_ENTITA ,
FILE_ENTITA.ID_TARGET  
FROM PBANDI_T_DOCUMENTO_INDEX DOCINDEX
JOIN PBANDI_T_FILE  ON (DOCINDEX.ID_DOCUMENTO_INDEX=PBANDI_T_FILE.ID_DOCUMENTO_INDEX)
JOIN PBANDI_T_FILE_ENTITA FILE_ENTITA ON FILE_ENTITA.ID_FILE=PBANDI_T_FILE.ID_FILE AND 
 FILE_ENTITA.ID_ENTITA=(SELECT ID_ENTITA FROM PBANDI_C_ENTITA WHERE UPPER(NOME_ENTITA)=UPPER('PBANDI_T_APPALTO'))
JOIN PBANDI_T_APPALTO ON (PBANDI_T_APPALTO.ID_APPALTO = FILE_ENTITA.ID_TARGET) AND FILE_ENTITA.ID_PROGETTO IS NOT NULL
JOIN PBANDI_T_PROCEDURA_AGGIUDICAZ ON (PBANDI_T_PROCEDURA_AGGIUDICAZ.ID_PROCEDURA_AGGIUDICAZ = PBANDI_T_APPALTO.ID_PROCEDURA_AGGIUDICAZ)
JOIN PBANDI_T_FOLDER USING(ID_FOLDER)
JOIN PBANDI_T_PROGETTO ON FILE_ENTITA.ID_PROGETTO=PBANDI_T_PROGETTO.ID_PROGETTO
JOIN PBANDI_T_DOMANDA  ON PBANDI_T_PROGETTO.ID_DOMANDA= PBANDI_T_DOMANDA.ID_DOMANDA
JOIN PBANDI_R_BANDO_LINEA_INTERVENT RBLI ON PBANDI_T_DOMANDA.PROGR_BANDO_LINEA_INTERVENTO = RBLI.PROGR_BANDO_LINEA_INTERVENTO