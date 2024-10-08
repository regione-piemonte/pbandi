/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

SELECT cms.CODICE_ERRORE , NOME_FILE ,
 CMS.ID_UTENTE ,  
 CMS.ID_SOGGETTO_BENEFICIARIO ,
 CMS.ID_PROGETTO , CMS.FLAG_CARICATO ,
 CMS.DT_VALIDAZIONE , DESCRIZIONE ,
 CMS.DT_INSERIMENTO , CMS.DT_ELABORAZIONE ,
 CMS.FLAG_VALIDATO , 
 CMS.ID_CARICA_MASS_DOC_SPESA ,
 RIGA_ERRORE,
 pr.CODICE_VISUALIZZATO,
(select count(ID_SCARTI_CARICA_MASS_DS) 
 from PBANDI_T_SCARTI_CARICA_MASS_DS doc_scartati
 where doc_scartati.id_carica_mass_doc_spesa= cms.id_carica_mass_doc_spesa) doc_scartati,
 (select count(ID_SCARTI_CARICA_MAS_PAG) 
 from PBANDI_T_SCARTI_CARICA_MAS_PAG pag_scartati
 where pag_scartati.id_carica_mass_doc_spesa= cms.id_carica_mass_doc_spesa) pag_scartati 
FROM PBANDI_T_CARICA_MASS_DOC_SPESA CMS 
     LEFT OUTER JOIN PBANDI_D_ERRORE_BATCH ERR 
     ON CMS.CODICE_ERRORE=ERR.CODICE_ERRORE
     LEFT OUTER JOIN PBANDI_T_PROGETTO pr
     ON CMS.ID_PROGETTO=PR.ID_PROGETTO
    