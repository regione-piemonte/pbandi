/*******************************************************************************
* Copyright Regione Piemonte - 2024
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/

select rfa.ID_FORNITORE, rfa.ID_APPALTO, rfa.ID_TIPO_PERCETTORE, 
       rfa.DT_INVIO_VERIFICA_AFFIDAMENTO, rfa.FLG_INVIO_VERIFICA_AFFIDAMENTO,
       dtp.DESC_TIPO_PERCETTORE,
       tf.ID_TIPO_SOGGETTO, 
       tf.NOME_FORNITORE, tf.COGNOME_FORNITORE, tf.CODICE_FISCALE_FORNITORE,
       tf.DENOMINAZIONE_FORNITORE,
       tf.PARTITA_IVA_FORNITORE
from PBANDI_R_FORNITORE_AFFIDAMENTO rfa, PBANDI_D_TIPO_PERCETTORE dtp, PBANDI_T_FORNITORE tf
where dtp.ID_TIPO_PERCETTORE = rfa.ID_TIPO_PERCETTORE
  and tf.ID_FORNITORE = rfa.ID_FORNITORE