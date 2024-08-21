/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

--PRG_CLASS_CUP
ALTER TABLE PRG_CLASS_CUP MODIFY DESC_INTERVENTO VARCHAR2(2000);
ALTER TABLE PRG_CLASS_CUP MODIFY DESC_INDIRIZZO_AREA VARCHAR2(2000);
ALTER TABLE PRG_CLASS_CUP MODIFY DENOM_BENEFICIARIO VARCHAR2(2000);

--PRG_PROC_AGGIUDICAZIONE
ALTER TABLE  PRG_PROC_AGGIUDICAZIONE MODIFY DESCR_PROCEDURA_AGG  VARCHAR2(2000);