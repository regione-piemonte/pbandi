/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

CREATE OR REPLACE PACKAGE PCK_PBANDI_UTILITY_ONLINE AS

/*******************************************************************************
   NAME:       PCK_PBANDI_UTILITY_ONLINE
   PURPOSE:    Package di funzioni di utility batch

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0.0      16/02/2009  Rocco Cambareri  Created this package.
   2.4.1      22/03/21    mc

  LAST MODIFY : 16/02/2009
  AUTHOR      : Rocco Cambareri
  DESCRIPTION : Creazione Package

  LAST MODIFY :
  AUTHOR      :
  DESCRIPTION :
*******************************************************************************/
  --************************************************************************************
  -- Sezione dedicata alle viste utilizzate dall'online
  --************************************************************************************
  -- Variabili Globali
  g_id_progetto             PBANDI_T_PROGETTO.ID_PROGETTO%TYPE;

  --Metodi
  FUNCTION  GetProgetto RETURN INTEGER;
  PROCEDURE SetParameterProgetto (p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE);
  --*************************************************************************************

  TYPE r_contr_cert IS RECORD
	(CODICE_VISUALIZZATO VARCHAR2(100),
	 ID_PROGETTO  NUMBER(8),
	 ID_PROPOSTA_CERTIFICAZ  NUMBER(8),
	 ID_UTENTE_INS  NUMBER(8),
	 NOME_BANDO_LINEA  VARCHAR2(255),
	 IMPORTO_EROGAZIONI  NUMBER(13,2),
	 ID_TIPO_OPERAZIONE NUMBER(3),
	 ID_DOMANDA  NUMBER(8),
	 PROGR_BANDO_LINEA_INTERVENTO  NUMBER(8),
	 IMPORTO_PAGAMENTI_VALIDATI NUMBER(17,2),
	 ID_LINEA_DI_INTERVENTO NUMBER(3)
	);

     type t_contr_cert_type is table of r_contr_cert
      index by BINARY_INTEGER;

  /************************************************************************
  RipartizionePagamenti : ripartizione dei pagamenti. Richiamata dell'on-line

  Parametri input:  pIdProgetto - Id del progetto
  *************************************************************************/

  PROCEDURE RipartizionePagamenti(pIdProgetto   PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                  pElencoDoc    VARCHAR2,
								  pIdDichiarazioneSpesa PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA%TYPE,
                                  pIdUtenteIns  PBANDI_T_UTENTE.ID_UTENTE%TYPE);

 /************************************************************************
  Revert RipartizionePagamenti : ripartizione dei pagamenti. Richiamata dell'on-line

  Parametri input:  pIdProgetto  - Id del progetto
                    pElencoDoc   - Elenco dei documenti di spesa
					pIdUtenteIns - Utente on line  che esegue l'operazione
*************************************************************************/

  PROCEDURE RevRipartizionePagamenti(pIdProgetto   PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                                  pElencoDoc       T_NUMBER_ARRAY,
								  pIdDichiarazioneSpesa PBANDI_T_DICHIARAZIONE_SPESA.ID_DICHIARAZIONE_SPESA%TYPE,
                                  pIdUtenteIns     PBANDI_T_UTENTE.ID_UTENTE%TYPE);
								  
/************************************************************************
 Inizializzazione archivio file           
 *************************************************************************/
 PROCEDURE InitArchivioFile (p_id_progetto IN NUMBER,
                            p_codice_visualizzato IN VARCHAR2,
							p_id_soggetto_ben IN NUMBER,
							p_id_utente IN NUMBER);
  /************************************************************************
  CaricamentoSchedaProgetto : ripartizione dei pagamenti. Richiamata dell'on-line

  Parametri input:  pIdCspProgetto - Id del progetto csp
  *************************************************************************/
  FUNCTION CaricamentoSchedaProgetto(pIdCspProgetto  PBANDI_T_CSP_PROGETTO.ID_CSP_PROGETTO%TYPE) RETURN NUMBER;

  /************************************************************************
  ReturnIndirSedeIntervento : ritorna l'indirizzo della sede intervento

  Parametri input:  pIdDettPropostaCertificaz - Id del dettaglio della proposta di certificazione
  *************************************************************************/
  FUNCTION ReturnIndirSedeIntervento(pIdDettPropostaCertificaz  PBANDI_T_DETT_PROPOSTA_CERTIF.ID_DETT_PROPOSTA_CERTIF%TYPE) RETURN VARCHAR2;


  /************************************************************************
  AttivitaPregresse : funzione per la gestione delle attivitÃ  pregresse

  Parametri input: pIdProgetto - Id del progetto
  *************************************************************************/
  FUNCTION AttivitaPregresse(pIdProgetto  PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) RETURN NUMBER;

/************************************************************************
  CaricaCheckList : Produce una tabella di oggetti con i dati degli appalti da inserine nella checklist

  Parametri input:  p_codice_modello - Codice del modello della checklist
                    p_id_progetto    - ID del progetto
					p_id_checklist   - ID della checklist (facoltativo)
*************************************************************************/
 FUNCTION CaricaCheckList (p_codice_modello IN PBANDI_C_MODELLO.CODICE_MODELLO%TYPE,
                            p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
                            p_DESC_BREVE_TIPO_DOC_INDEX  IN PBANDI_C_TIPO_DOCUMENTO_INDEX.DESC_BREVE_TIPO_DOC_INDEX%TYPE,
                            p_id_checklist IN PBANDI_T_CHECKLIST.ID_CHECKLIST%TYPE DEFAULT NULL) return LISTCHECKLIST;

/************************************************************************
   IsFESR : controlla se il progetto e' o non e' FESR

   Parametri input: pIdProgetto  - id del progetto
*************************************************************************/
FUNCTION IsFESR (pIdProgetto PBANDI_T_PROGETTO.ID_PROGETTO%TYPE) RETURN VARCHAR2;

/************************************************************************/
FUNCTION FNC_DSFINALE_CFP
(p_id_progetto IN PBANDI_T_PROGETTO.ID_PROGETTO%TYPE,
p_id_utente IN PBANDI_T_UTENTE.ID_UTENTE%TYPE
)
RETURN INTEGER;
/************************************************************************/

END PCK_PBANDI_UTILITY_ONLINE; 
/